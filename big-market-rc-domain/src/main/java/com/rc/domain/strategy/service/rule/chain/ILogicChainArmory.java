package com.rc.domain.strategy.service.rule.chain;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description 责任链装配接口
 */
public interface ILogicChainArmory {


    ILogicChain appendNext(ILogicChain next);

    ILogicChain next();

}
