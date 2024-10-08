package com.rc.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author renchuang
 * @date 2024/7/17
 * @Description 抽奖策略规则-规则值对象，没有唯一ID，仅限于从数据库中查询。例如：1,1000，意思是1～1000积分内对应的可抽奖品
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {

     private String ruleModels;
//
//     public String[] raffleCenterRuleModelList(){
//         List<String> ruleModelList=new ArrayList<>();
//         String[] ruleModelValues = ruleModels.split(Constants.SPLIT);
//         for(String ruleModelValue : ruleModelValues){
//             if(DefaultLogicFactory.LogicModel.isCenter(ruleModelValue)){
//                 ruleModelList.add(ruleModelValue);
//             }
//         }
//
//         return ruleModelList.toArray(new String[0]);
//     }
//     public String[] raffleAfterRuleModelList(){
//         List<String> ruleModelList=new ArrayList<>();
//         String[] ruleModelValues = ruleModels.split(Constants.SPLIT);
//         for(String ruleModelValue : ruleModelValues){
//             if(DefaultLogicFactory.LogicModel.isAfter(ruleModelValue)){
//                 ruleModelList.add(ruleModelValue);
//             }
//         }
//
//         return ruleModelList.toArray(new String[0]);
//     }
//


}
