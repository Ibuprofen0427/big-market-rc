package com.rc.domain.award.repository;

import com.rc.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.rc.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author renchuang
 * @date 2024/8/19
 * @Description 奖品仓储服务
 */
public interface IAwardRepository {
    void saveAwardRecord(UserAwardRecordAggregate recordAggregate);
}
