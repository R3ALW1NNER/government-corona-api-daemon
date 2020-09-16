package com.realwinner.governmentcoronaapidaemon.scheduler;

import com.realwinner.governmentcoronaapidaemon.domain.service.GovernmentCOVID19Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GovernmentCOVID19Scheduler {

    @Autowired
    private GovernmentCOVID19Service service;

    // 보통 데이터는 9~10시 사이에 생성된다.
    @Scheduled(cron = "*/30 * 9,10 * * ?")
    public void apiDailySchedule() {
        log.info("Daily Api Start");
        service.coronaApiToDbSave();
        log.info("Daily Api End");
    }
}
