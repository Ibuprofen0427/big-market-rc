package com.rc.domain.strategy.service.rule.chain;

/**
 * @author renchuang
 * @date 2024/7/22
 * @Description
 */
public abstract class AbstractLogicChain implements ILogicChain{

    private ILogicChain next;

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next=next;
        return next;
    }

    @Override
    public ILogicChain next() {

        return next;
    }


    protected abstract String ruleModel();
}
