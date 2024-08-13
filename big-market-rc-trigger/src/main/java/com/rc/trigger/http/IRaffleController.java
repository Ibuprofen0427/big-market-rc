package com.rc.trigger.http;

import com.alibaba.fastjson.JSON;
import com.rc.domain.strategy.model.entity.RaffleAwardEntity;
import com.rc.domain.strategy.model.entity.RaffleFactorEntity;
import com.rc.domain.strategy.model.entity.StrategyAwardEntity;
import com.rc.domain.strategy.service.IRaffleAward;
import com.rc.domain.strategy.service.IRaffleStrategy;
import com.rc.domain.strategy.service.armory.IStrategyArmory;
import com.rc.trigger.api.IRaffleService;
import com.rc.trigger.api.dto.RaffleAwardListRequestDTO;
import com.rc.trigger.api.dto.RaffleAwardListResponseDTO;
import com.rc.trigger.api.dto.RaffleRequestDTO;
import com.rc.trigger.api.dto.RaffleResponseDTO;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import com.rc.types.model.Response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/4
 * @Description 抽奖服务
 */
@Slf4j
@RestController()
@CrossOrigin(value = "${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/")
public class IRaffleController implements IRaffleService {


    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IRaffleAward raffleAward;

    @Resource
    private IRaffleStrategy raffleStrategy;


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
     * @param raffleAwardListRequestDTO
     * @return 抽奖列表服务
     */
    @RequestMapping(value = "query_raffle_award_list", method = RequestMethod.POST)
    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(@RequestBody RaffleAwardListRequestDTO raffleAwardListRequestDTO) {
        try {
            log.info("查询奖品列表开始 raffleAwardListRequestDTO: {}", raffleAwardListRequestDTO);
            List<StrategyAwardEntity> strategyAwardEntities = raffleAward.queryRaffleAwardList(raffleAwardListRequestDTO.getStrategyId());
            List<RaffleAwardListResponseDTO> raffleAwardListRequestDTOS = new ArrayList<>(strategyAwardEntities.size());
            for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
                raffleAwardListRequestDTOS.add(RaffleAwardListResponseDTO.builder()
                        .awardId(strategyAwardEntity.getAwardId())
                        .awardTitle(strategyAwardEntity.getAwardTitle())
                        .awardSubtitle(strategyAwardEntity.getAwardSubtitle())
                        .sort(strategyAwardEntity.getSort())
                        .build());

            }
            Response<List<RaffleAwardListResponseDTO>> response = Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleAwardListRequestDTOS)
                    .build();
            log.info("查询抽奖奖品列表配置成功！ strategyId: {} response: {}", raffleAwardListRequestDTO.getStrategyId(), JSON.toJSONString(response));
            return response;


        } catch (Exception e) {
            log.info("查询奖品列表配置失败！strategyId: {}", raffleAwardListRequestDTO.getStrategyId(), e);
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
    public Response<RaffleResponseDTO> raffle(@RequestBody  RaffleRequestDTO raffleRequestDTO) {
        try {
            log.info("随机抽奖开始 strategyId: {}", raffleRequestDTO.getStrategyId());
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(RaffleFactorEntity.builder()
                    .userId("system")
                    .strategyId(raffleRequestDTO.getStrategyId())
                    .build());
            Response<RaffleResponseDTO> response = Response.<RaffleResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(RaffleResponseDTO.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();
            log.info("随机抽奖完成 strategyId: {} response: {}", raffleRequestDTO.getStrategyId(), response);
            return response;

        } catch (AppException e) {
            log.error("随机抽奖失败 strategyId: {} {}", raffleRequestDTO.getStrategyId(), e.getMessage());
            return Response.<RaffleResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("随机抽奖失败 strategyId: {}", raffleRequestDTO.getStrategyId(), e);
            return Response.<RaffleResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
