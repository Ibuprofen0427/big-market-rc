package com.rc.infrastructure.persistent.dao;

import com.rc.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 奖品表dao
 */
@Mapper
public interface IAwardDao {

    List<Award> queryAwardList();

}
