package com.realwinner.governmentcoronaapidaemon.domain.scheduler;

import com.realwinner.governmentcoronaapidaemon.domain.service.CoronaTelegramAlarm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoronaTelegramAlarmScheduler {

    @Autowired
    private CoronaTelegramAlarm alarm;

    @Scheduled(cron = "0 0 9 * * ?")
    public void dailyAlarm() {
        boolean loopFlag = true;

        while(loopFlag) {
            if (alarm.getTodayDataCollectYN()) {
                alarm.yesterdayCompareTodayAlarmSend();
                loopFlag = false;
            } else {
                try {
                    log.info("data not collected, 1 min sleep...");
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
