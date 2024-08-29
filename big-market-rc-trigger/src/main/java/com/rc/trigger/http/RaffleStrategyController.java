package com.rc.trigger.http;

import com.alibaba.fastjson.JSON;
import com.rc.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.rc.domain.strategy.model.entity.RaffleAwardEntity;
import com.rc.domain.strategy.model.entity.RaffleFactorEntity;
import com.rc.domain.strategy.model.entity.StrategyAwardEntity;
import com.rc.domain.strategy.service.IRaffleAward;
import com.rc.domain.strategy.service.IRaffleRule;
import com.rc.domain.strategy.service.IRaffleStrategy;
import com.rc.domain.strategy.service.armory.IStrategyArmory;
import com.rc.trigger.api.IRaffleStrategyService;
import com.rc.trigger.api.dto.strategy.RaffleAwardListRequestDTO;
import com.rc.trigger.api.dto.strategy.RaffleAwardListResponseDTO;
import com.rc.trigger.api.dto.strategy.RaffleStrategyRequestDTO;
import com.rc.trigger.api.dto.strategy.RaffleStrategyResponseDTO;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import com.rc.types.model.Response;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author renchuang
 * @date 2024/8/4
 * @Description 抽奖服务
 */
@Slf4j
@RestController()
@CrossOrigin(value = "${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/strategy")
public class RaffleStrategyController implements IRaffleStrategyService {


    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IRaffleAward raffleAward;

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private IRaffleRule raffleRule;

    @Resource
    private IRaffleActivityAccountQuotaService accountQuotaService;

    /**
     * @param strategyId
     * @return 策略装配服务
     */
    @RequestMapping(value = "strategy_armory", method = RequestMethod.GET)
    @Override
    public Response<Boolean> strategyArmory(@RequestParam Long strategyId) {
        try {
            log.info("抽奖策略装配开始 strategyId: {}", strategyId);
            boolean assembledLotteryStrategy = strategyArmory.assembleLotteryStrategy(strategyId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(assembledLotteryStrategy)
                    .build();
            log.info("抽奖策略装配完成 strategyId: {} response: {}", strategyId, response);
            return response;

        } catch (Exception e) {
            log.error("抽奖策略装配失败 strategyId: {}", strategyId);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();

        }

    }

    /**
     * @param request
     * @return 抽奖列表服务
     */
    @RequestMapping(value = "query_raffle_award_list", method = RequestMethod.POST)
    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(@RequestBody RaffleAwardListRequestDTO request) {
        try {
            log.info("查询奖品列表开始 userId: {} activityId: {}", request.getUserId(), request.getActivityId());
            // 参数校验
            if (StringUtils.isBlank(request.getUserId()) || null == request.getActivityId()) {
                return Response.<List<RaffleAwardListResponseDTO>>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }
            // 2.查询奖品配置
            List<StrategyAwardEntity> strategyAwardEntities = raffleAward.queryRaffleAwardListByActivityId(request.getActivityId());
            // 3.获取奖品规则配置
            String[] treeIds = strategyAwardEntities.stream()
                    .map(StrategyAwardEntity::getRuleModels)
                    // 过滤一下不为空的ruleModel
                    .filter(ruleModel -> ruleModel != null && ruleModel.isEmpty())
                    .toArray(String[]::new);
            // 4.查询规则配置 - 获取奖品的解锁限制信息
            Map<String, Integer> ruleLockCountMap = raffleRule.queryAwardRuleLockCount(treeIds);
            // 5.查询抽奖次数 - 查询用户账户已经参与抽奖次数
            Integer dayPartakeCount = accountQuotaService.queryRaffleActivityAccountDayPartakeCount(request.getActivityId(), request.getUserId());
            // 6.遍历填充数据 - awardRuleLockCount、isAwardUnlock、waitUnlockCount三个字段，给到前端用于控制奖品加锁
            List<RaffleAwardListResponseDTO> raffleAwardListResponseDTOS = new ArrayList<>(strategyAwardEntities.size());
            for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
                Integer awardRuleLockCount = ruleLockCountMap.get(strategyAwardEntity.getRuleModels());
                raffleAwardListResponseDTOS.add(RaffleAwardListResponseDTO.builder()
                        .awardId(strategyAwardEntity.getAwardId())
                        .awardTitle(strategyAwardEntity.getAwardTitle())
                        .awardSubtitle(strategyAwardEntity.getAwardSubtitle())
                        .sort(strategyAwardEntity.getSort())
                        .awardRuleLockCount(awardRuleLockCount)
                        .isAwardUnlock(null == awardRuleLockCount || dayPartakeCount > awardRuleLockCount)
                        .waitUnlockCount(null == awardRuleLockCount || awardRuleLockCount <= dayPartakeCount ? 0 : awardRuleLockCount - dayPartakeCount)
                        .build());

            }
            Response<List<RaffleAwardListResponseDTO>> response = Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleAwardListResponseDTOS)
                    .build();
            log.info("查询抽奖奖品列表配置成功！ userId: {} activityId: {} response: {}", request.getUserId(), request.getActivityId(),JSON.toJSONString(response));
            return response;


        } catch (Exception e) {
            log.info("查询抽奖奖品列表配置失败！ userId: {} activityId: {}", request.getUserId(), request.getActivityId(),e);
            return Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }


    /**
     * @param raffleRequestDTO
     * @return
     * @Description 随机抽奖服务
     */
    @RequestMapping(value = "random_raffle", method = RequestMethod.POST)
    @Override
    public Response<RaffleStrategyResponseDTO> raffle(@RequestBody RaffleStrategyRequestDTO raffleRequestDTO) {
        try {
            log.info("随机抽奖开始 strategyId: {}", raffleRequestDTO.getStrategyId());
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(RaffleFactorEntity.builder()
                    .userId("system")
                    .strategyId(raffleRequestDTO.getStrategyId())
                    .build());
            Response<RaffleStrategyResponseDTO> response = Response.<RaffleStrategyResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(RaffleStrategyResponseDTO.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();
            log.info("随机抽奖完成 strategyId: {} response: {}", raffleRequestDTO.getStrategyId(), response);
            return response;

        } catch (AppException e) {
            log.error("随机抽奖失败 strategyId: {} {}", raffleRequestDTO.getStrategyId(), e.getMessage());
            return Response.<RaffleStrategyResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("随机抽奖失败 strategyId: {}", raffleRequestDTO.getStrategyId(), e);
            return Response.<RaffleStrategyResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
