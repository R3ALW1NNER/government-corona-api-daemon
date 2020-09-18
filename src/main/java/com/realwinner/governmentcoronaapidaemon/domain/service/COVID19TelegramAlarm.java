package com.realwinner.governmentcoronaapidaemon.domain.service;

import com.realwinner.governmentcoronaapidaemon.config.TelegramMessage;
import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCOVID19Model;
import com.realwinner.governmentcoronaapidaemon.domain.repository.GovernmentCOVID19Repository;
import com.realwinner.governmentcoronaapidaemon.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class COVID19TelegramAlarm {
    @Autowired
    private GovernmentCOVID19Repository repository;

    @Autowired
    private TelegramMessage telegramMessage;

    @Autowired
    private DateUtil dateUtil;

    public void yesterdayCompareTodayAlarmSend() {
        String msgFormat = "%s 기준 발생 현황\n" +
                "- 확진자 수: %d 명\n" +
                "  (국내: %d, 해외: %d)\n" +
                "- 사망자 수: %d 명\n" +
                "- 누적 확진자 수: %d 명\n" +
                "- 누적 사망자 수: %d 명\n\n" +
                "----- 지역별 발생 현황 -----\n" +
                "지역명: 확진자 수 / 사망자 수\n";

        String subMsgFormat = "%s: %d / %d\n";
        String resultMsg = "";

        String yesterdayDateHangul = dateUtil.getYesterdayDateStringHangul();
        String todayDateHangul = dateUtil.getTodayDateStringHangul();


        ArrayList<GovernmentCOVID19Model> todayList = repository.findByStdDay(todayDateHangul);
        ArrayList<GovernmentCOVID19Model> yesterdayList = repository.findByStdDay(yesterdayDateHangul);

        int todayIncDec = todayList.get(todayList.size() - 1).getIncDec();
        int todayDeathCnt = todayList.get(todayList.size() - 1).getDeathCnt() - yesterdayList.get(todayList.size() - 1).getDeathCnt();

        int todayOutsideInDec = todayList.get(0).getOverFlowCnt();
        int todayInsideIncDec = todayList.get(0).getLocalOccCnt();

        int todayTotalDeathCnt = todayList.get(todayList.size() - 1).getDeathCnt();
        int todayTotalDefCnt = todayList.get(todayList.size() - 1).getDefCnt();

        resultMsg += String.format(msgFormat, todayDateHangul, todayIncDec, todayInsideIncDec,
                todayOutsideInDec, todayDeathCnt, todayTotalDefCnt, todayTotalDeathCnt);

        for (int i = todayList.size() - 2; i > 0; i--) {
            resultMsg += String.format(subMsgFormat, todayList.get(i).getGubun(),
                    todayList.get(i).getIncDec(),
                    todayList.get(i).getDeathCnt() - yesterdayList.get(i).getDeathCnt());
        }


        telegramMessage.send(resultMsg);
    }

    public boolean getTodayDataCollectYN() {
        ArrayList<GovernmentCOVID19Model> todayModelList = repository.findByStdDay(dateUtil.getTodayDateStringHangul());

        boolean rs = false;

        if (todayModelList.size() > 0) {
            rs = true;
        }

        return rs;
    }
}
