package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author renchuang
 * @date 2024/8/13
 * @Description
 */
@Mapper
public interface IRaffleActivitySkuDao {

    RaffleActivitySku queryActivitySku(Long sku);
}
