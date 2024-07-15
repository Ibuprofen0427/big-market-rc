package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/7/15
 * @Description 奖品表
 *
 */

@Data
public class Award {

    private String id;
    // 抽奖奖品id
    private String awardId;
    // 奖品对接标识 - 每一个都是一个对应的发奖策略
    private String awardKey;
    // 奖品配置信息
    private String awardConfig;
    // 奖品内容描述
    private String awardDesc;
    private Date createTime;
    private Date updateTime;
}
