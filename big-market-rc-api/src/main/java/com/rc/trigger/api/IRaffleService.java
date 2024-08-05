package com.rc.trigger.api;

import com.rc.trigger.api.dto.RaffleAwardListRequestDTO;
import com.rc.trigger.api.dto.RaffleAwardListResponseDTO;
import com.rc.trigger.api.dto.RaffleRequestDTO;
import com.rc.trigger.api.dto.RaffleResponseDTO;
import com.rc.types.model.Response;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/8/4
 * @Description
 */
public interface IRaffleService {

    /**
     * @param strategyId
     * @return
     * 策略装配接口
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * @param raffleAwardListRequestDTO
     * @return 奖品列表
     * 奖品列表查询接口
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO raffleAwardListRequestDTO);

    /**
     * @param raffleRequestDTO
     * @return 奖品dto
     * 随机抽奖接口
     */
    Response<RaffleResponseDTO> raffle(RaffleRequestDTO raffleRequestDTO);
}
