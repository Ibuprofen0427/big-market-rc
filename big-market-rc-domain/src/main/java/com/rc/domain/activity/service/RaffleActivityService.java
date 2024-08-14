package com.rc.domain.activity.service;

import com.rc.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author renchuang
 * @date 2024/8/14
 * @Description
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity{


    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }
}
