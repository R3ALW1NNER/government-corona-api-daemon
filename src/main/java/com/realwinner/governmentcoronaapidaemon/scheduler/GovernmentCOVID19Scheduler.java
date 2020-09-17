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
    // API의 데이터 갱신 시간이 너무 느리다.
    // 언론 공개 시간과 달라 사이트 크롤링으로 변경이 필요 할 것 같음
    @Scheduled(cron = "0 0 9 * * ?")
    public void apiDailySchedule() {
        log.info("Daily Api Start");
        service.coronaApiToDbSave();
        log.info("Daily Api End");
    }
}
