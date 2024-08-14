package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description
 */
@Mapper
public interface IRaffleActivityAccountDao {
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);


    void insert(RaffleActivityAccount raffleActivityAccount);
}
