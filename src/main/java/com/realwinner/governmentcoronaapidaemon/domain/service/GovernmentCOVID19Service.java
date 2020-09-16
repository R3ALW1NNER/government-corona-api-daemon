package com.realwinner.governmentcoronaapidaemon.domain.service;

import com.realwinner.governmentcoronaapidaemon.config.GovernmentCOVID19ApiHttpClient;
import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCOVID19Model;
import com.realwinner.governmentcoronaapidaemon.domain.repository.GovernmentCOVID19Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class GovernmentCOVID19Service {
    @Autowired
    private GovernmentCOVID19Repository governmentCOVID19Repository;

    @Autowired
    private GovernmentCOVID19ApiHttpClient coronaApiHttpCall;

    public void coronaApiToDbSave() {
        ArrayList<GovernmentCOVID19Model> list = coronaApiHttpCall.getCOVID19Data();

        if (list.size() == 0) {
            log.info("Data is Null");
        } else {
            for (GovernmentCOVID19Model model : list) {
                log.info(model.toString());
                governmentCOVID19Repository.save(model);
            }
            log.info("DB save success");
        }
    }
}
