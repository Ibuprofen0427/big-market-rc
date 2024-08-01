package com.rc.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/1
 * @Description
 */
@Data
public class RuleTreeNode {

    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String treeId;
    /** 规则Key */
    private String ruleKey;
    /** 规则描述 */
    private String ruleDesc;
    /** 规则比值 */
    private String ruleValue;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
