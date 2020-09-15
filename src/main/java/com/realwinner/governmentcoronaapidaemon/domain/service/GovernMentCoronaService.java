package com.realwinner.governmentcoronaapidaemon.domain.service;

import com.realwinner.governmentcoronaapidaemon.config.GovernmentCoronaApiHttpClient;
import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCoronaModel;
import com.realwinner.governmentcoronaapidaemon.domain.repository.GovernmentCoronaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GovernMentCoronaService {
    @Autowired
    private GovernmentCoronaRepository governmentCoronaRepository;

    @Autowired
    private GovernmentCoronaApiHttpClient coronaApiHttpCall;

    public void coronaApiToDbSave() {
        GovernmentCoronaModel governmentCoronaModel = coronaApiHttpCall.getCoronaData();

        if (governmentCoronaModel == null) {
            log.info("Data is Null");
        } else {
            governmentCoronaRepository.save(governmentCoronaModel);
            log.info("DB save success");
        }
    }
}
