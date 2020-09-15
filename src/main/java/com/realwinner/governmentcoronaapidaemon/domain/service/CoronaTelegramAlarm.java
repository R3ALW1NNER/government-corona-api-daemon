package com.realwinner.governmentcoronaapidaemon.domain.service;

import com.realwinner.governmentcoronaapidaemon.config.TelegramMessage;
import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCoronaModel;
import com.realwinner.governmentcoronaapidaemon.domain.repository.GovernmentCoronaRepository;
import com.realwinner.governmentcoronaapidaemon.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoronaTelegramAlarm {
    @Autowired
    private GovernmentCoronaRepository repository;

    @Autowired
    private TelegramMessage telegramMessage;

    @Autowired
    private DateUtil dateUtil;

    public void yesterdayCompareTodayAlarmSend() {
        String msgFormat = "%s년 %s월 %s일 00시 기준 발생 현황\n" +
                "- 확진자 수 : %d명\n" +
                "- 사망자 수 : %d명\n" +
                "- 검사진행 수 : %d명\n" +
                "- 격리해제 수 : %d명";


        String todayYYYY = dateUtil.getTodayDateString(dateUtil.dateTimeFormatYYYY);
        String todayMM = dateUtil.getTodayDateString(dateUtil.dateTimeFormatMM);
        String todayDD = dateUtil.getTodayDateString(dateUtil.dateTimeFormatDD);

        String todayDate = dateUtil.getTodayDateString();
        String yesterdayDate = dateUtil.getYesterdayDateString();

        GovernmentCoronaModel todayModel = repository.findByStateDt(todayDate);
        GovernmentCoronaModel yesterdayModel = repository.findByStateDt(yesterdayDate);

        int compareDecideCnt = todayModel.getDecideCnt() - yesterdayModel.getDecideCnt();
        int compareDeathCnt = todayModel.getDeathCnt() - yesterdayModel.getDeathCnt();
        int compareAccExamCnt = todayModel.getAccExamCnt() - yesterdayModel.getAccExamCnt();
        int compareClearCnt = todayModel.getClearCnt() - yesterdayModel.getClearCnt();


        String msg = String.format(msgFormat, todayYYYY, todayMM, todayDD,
                compareDecideCnt, compareDeathCnt, compareAccExamCnt, compareClearCnt);

        telegramMessage.send(msg);
    }

    public boolean getTodayDataCollectYN() {
        GovernmentCoronaModel todayModel = repository.findByStateDt(dateUtil.getTodayDateString());

        boolean rs = false;

        if (todayModel.getStateDt() != null) {
            rs = true;
        }

        return rs;
    }

}
