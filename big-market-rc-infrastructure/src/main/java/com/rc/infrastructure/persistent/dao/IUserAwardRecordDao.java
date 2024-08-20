package com.rc.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.rc.infrastructure.persistent.po.UserAwardRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author renchuang
 * @date 2024/8/17
 * @Description
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserAwardRecordDao {


    void insert(UserAwardRecord userAwardRecord);
}
