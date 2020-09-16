package com.realwinner.governmentcoronaapidaemon.scheduler;

import com.realwinner.governmentcoronaapidaemon.domain.service.COVID19TelegramAlarm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class COVID19TelegramAlarmScheduler {

    @Autowired
    private COVID19TelegramAlarm alarm;

    @Scheduled(cron = "0 0 9 * * ?")
    public void dailyAlarm() {
        boolean loopFlag = true;

        while(loopFlag) {
            if (alarm.getTodayDataCollectYN()) {
                alarm.yesterdayCompareTodayAlarmSend();
                loopFlag = false;
            } else {
                try {
                    log.info("data not collected, 10 sec sleep...");
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
