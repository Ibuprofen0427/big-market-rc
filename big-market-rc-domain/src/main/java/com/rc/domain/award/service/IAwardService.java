package com.rc.domain.award.service;

import com.rc.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description 奖品服务接口
 */

public interface IAwardService {

    /**
     * @param userAwardRecordEntity 用户中奖记录实体
     * 用户中奖奖品流水落库
     */
    void saveAwardRecord(UserAwardRecordEntity userAwardRecordEntity);
}
