package com.rc.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.rc.infrastructure.persistent.po.RaffleActivityAccountDay;
import com.rc.infrastructure.persistent.po.RaffleActivityAccountMonth;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description
 */
@Mapper
public interface IRaffleActivityAccountMonthDao {
    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);


    void insertActivityAccountMonth(RaffleActivityAccountMonth raffleActivityAccountMonth);

    @DBRouter
    RaffleActivityAccountMonth queryActivityAccountMonthByUserId(RaffleActivityAccountMonth raffleActivityAccountMonthReq);


}
