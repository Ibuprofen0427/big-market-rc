package com.rc.domain.activity.service.partake;

import com.rc.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.rc.domain.activity.model.entity.*;
import com.rc.domain.activity.model.valobj.UserRaffleOrderStateVO;
import com.rc.domain.activity.repository.IActivityRepository;
import com.rc.types.enums.ResponseCode;
import com.rc.types.exception.AppException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author renchuang
 * @date 2024/8/18
 * @Description
 */
@Service
public class RaffleActivityPartakeService extends AbstractRaffleActivityPartake {

    private final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");
    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 使用构造函数的方式进行注入
     *
     * @param activityRepository 活动仓储接口
     */
    public RaffleActivityPartakeService(IActivityRepository activityRepository) {
        super(activityRepository);
    }



    @Override
    protected CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate) {
        // 1.查询总账户额度
        ActivityAccountEntity activityAccountEntity = activityRepository.queryActivityAccountByUserId(userId,activityId);
        // 账户额度为空 或 额度不足
        if (null == activityAccountEntity || activityAccountEntity.getTotalCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACCOUNT_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_QUOTA_ERROR.getInfo());
        }
        // 2.查询月额度
        String month = dateFormatMonth.format(currentDate);
        ActivityAccountMonthEntity activityAccountMonthEntity = activityRepository.queryActivityMonthAccountByUserId(userId,activityId,month);
        if (null != activityAccountMonthEntity && activityAccountMonthEntity.getMonthCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getInfo());
        }

        // 总额度不为0，但月额度为空，则创建月额度
        // 创建月账户额度；true=存在月账户， false=不存在月账户
        boolean isExistAccountMonth = null != activityAccountMonthEntity;
        if (null == activityAccountMonthEntity) {
            activityAccountMonthEntity = new ActivityAccountMonthEntity();
            activityAccountMonthEntity.setUserId(userId);
            activityAccountMonthEntity.setActivityId(activityId);
            activityAccountMonthEntity.setMonth(month);
            activityAccountMonthEntity.setMonthCount(activityAccountEntity.getMonthCount());
            activityAccountMonthEntity.setMonthCountSurplus(activityAccountEntity.getMonthCountSurplus());
        }
        // 3.查询日额度
        String day = dateFormatDay.format(currentDate);
        ActivityAccountDayEntity activityAccountDayEntity = activityRepository.queryActivityDayAccountByUserId(userId,activityId,day);
        if (null != activityAccountDayEntity && activityAccountDayEntity.getDayCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getInfo());
        }
        // 总额度不为0，但日额度为空，则创建日额度
        // 创建日账户额度；true=存在日账户， false=不存在日账户
        boolean isExistAccountDay = null != activityAccountDayEntity;
        if (null == activityAccountDayEntity) {
            activityAccountDayEntity = new ActivityAccountDayEntity();
            activityAccountDayEntity.setUserId(userId);
            activityAccountDayEntity.setActivityId(activityId);
            activityAccountDayEntity.setDay(day);
            activityAccountDayEntity.setDayCount(activityAccountEntity.getDayCount());
            activityAccountDayEntity.setDayCountSurplus(activityAccountEntity.getDayCountSurplus());
        }
        // 4.构建聚合对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate = new CreatePartakeOrderAggregate();
        createPartakeOrderAggregate.setUserId(userId);
        createPartakeOrderAggregate.setActivityId(activityId);
        createPartakeOrderAggregate.setActivityAccount(activityAccountEntity);
        createPartakeOrderAggregate.setActivityAccountMonth(activityAccountMonthEntity);
        createPartakeOrderAggregate.setActivityAccountDay(activityAccountDayEntity);
        createPartakeOrderAggregate.setExistAccountDay(isExistAccountDay);
        createPartakeOrderAggregate.setExistAccountMonth(isExistAccountMonth);
        // 5.返回聚合对象
        return createPartakeOrderAggregate;
    }

    @Override
    protected UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate) {
        ActivityEntity activityEntity=activityRepository.queryRaffleActivityByActivityId(activityId);
        // 构建订单
        UserRaffleOrderEntity userRaffleOrderEntity=new UserRaffleOrderEntity();
        userRaffleOrderEntity.setUserId(userId);
        userRaffleOrderEntity.setActivityId(activityId);
        userRaffleOrderEntity.setActivityName(activityEntity.getActivityName());
        userRaffleOrderEntity.setStrategyId(activityEntity.getStrategyId());
        userRaffleOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        userRaffleOrderEntity.setOrderTime(currentDate);
        userRaffleOrderEntity.setOrderState(UserRaffleOrderStateVO.create);
        // 返回订单对象
        return userRaffleOrderEntity;
    }
}
