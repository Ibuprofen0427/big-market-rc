package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.RaffleActivity;
import com.rc.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description
 */
@Mapper
public interface IRaffleActivityCountDao {
     RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);
}
