package com.rc.domain.strategy.service.armory;

import com.rc.domain.strategy.model.entity.StrategyAwardEntity;
import com.rc.domain.strategy.model.entity.StrategyEntity;
import com.rc.domain.strategy.model.entity.StrategyRuleEntity;
import com.rc.domain.strategy.repository.IStrategyRepository;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 策略装配库，负责初始化策略计算
 */
@Service
@Slf4j
@Resource
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    @Resource
    private IStrategyRepository strategyRepository;

    // 查询策略配置
    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1.查询策略配置对应的奖品，按照策略查找对应的该策略下的奖品
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);
        // 2.装配数据
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);
        // 3.权重策略配置 - 使用于 rule_weight 权重规则配置
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        // 4.获取rule_weight
        String ruleWeight = strategyEntity.getRuleWeight();
        if (null == ruleWeight) return true;
        // 5.查询StrategyRuleEntity
        StrategyRuleEntity strategyRuleEntity = strategyRepository.queryStrategyRule(strategyId, ruleWeight);
        // 6.校验rule_weight是否为空
        if (null == strategyRuleEntity) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(),ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        // 7.解析rule_value，比如4000:102，103，104，105 Map<Long,List<Long>> 积分制对应一个奖品ID列表
        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys=ruleWeightValueMap.keySet();
        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            // 将不在该区间范围内的awardId剔除
            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));
            // 剔除后进行装配
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }


        return true;
    }

    // 抽离出装配动作方法：
    public void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
//        if(strategyAwardEntities == null || strategyAwardEntities.isEmpty()) {
//            return false;
//        }
        // 根据strategy_rule中的内容，过滤掉其他的奖品ID
        // 比如4000分以内的只允许抽102，103，104，105
        // 1.获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 2.获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream().
                map(StrategyAwardEntity::getAwardRate).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3.用1 % 0.0001 获取概率范围，百分位，千分位，万分位
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 4.生成策略

        // 根据每个奖项的中奖率填充奖项查找表
        // 例如下面的注释，按规则比例分配奖项查找表
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();
            // 计算每个概率值需要存放到查找表的数量，循环填充
            /*
             * 比如三个奖项，中奖率分别为0.5，0.1，0.01，则填充查找表时：
             * 奖项1：rateRange * 0.5 = 61 * 0.5 = 30.5，取整后为31次。
             * 奖项2：rateRange * 0.1 = 61 * 0.1 = 6.1，取整后为7次。
             * 奖项3：rateRange * 0.01 = 61 * 0.01 = 0.61，取整后为1次。
             * 所以strategyAwardSearchRateTables的size为31+7+1=39个格子，分别是31个奖品1，7个奖品2.1个奖品1
             * 填充完毕。
             * 后续抽奖，就查这个表，按照生成的随机值来作为key，去查这个奖项map，拿到的value就是奖品ID
             * */
            for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }
        // 5.乱序策略
        // todo: strategyAwardSearchRateTables.size()过大，shuffle操作为洗牌操作，时间复杂度为O(n)
        Collections.shuffle(strategyAwardSearchRateTables);
        // 6.存到map中
        HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables = new HashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            //这里 java.lang.OutOfMemoryError: Java heap space
            shuffleStrategyAwardSearchRateTables.put(i, strategyAwardSearchRateTables.get(i));
        }
        // 7.存储到redis中
        strategyRepository.storeStrategyAwardSearchRateTables(key, shuffleStrategyAwardSearchRateTables.size(), shuffleStrategyAwardSearchRateTables);

    }


    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 分布式部署下，不一定为当前应用做的策略配置，也就是值不一定会保存到本应用，而是分布式应用，所以从Redis中获取
        int rateRange = strategyRepository.getRateRange(strategyId);
        // 通过生成的碎挤汁，获取概率值奖品查找表的结果
        return strategyRepository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key=String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        // 分布式部署下，不一定为当前应用做的策略配置，也就是值不一定会保存到本应用，而是分布式应用，所以从Redis中获取
        int rateRange = strategyRepository.getRateRange(key);
        // 通过生成的碎挤汁，获取概率值奖品查找表的结果
        return strategyRepository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }
}

