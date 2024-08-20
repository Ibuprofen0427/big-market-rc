package com.rc.domain.award.service;

import com.rc.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description 奖品服务接口
 */

public interface IAwardService {

    void saveAwardRecord(UserAwardRecordEntity userAwardRecordEntity);
}
