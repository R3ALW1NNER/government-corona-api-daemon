package com.realwinner.governmentcoronaapidaemon.domain.controller;

import com.realwinner.governmentcoronaapidaemon.config.GovernmentCoronaApiHttpClient;
import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCoronaModel;
import com.realwinner.governmentcoronaapidaemon.domain.repository.GovernmentCoronaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("not_used")
@RestController
public class GovernMentCoronaController {
    @Autowired
    private GovernmentCoronaRepository governmentCoronaRepository;

    @Autowired
    private GovernmentCoronaApiHttpClient coronaApiHttpCall;

    @GetMapping("/apiSave")
    public String apiToDbSave() {

        GovernmentCoronaModel governmentCoronaModel = coronaApiHttpCall.getCoronaData();

        if (governmentCoronaModel == null) {
            return "fail";
        }

        governmentCoronaRepository.save(governmentCoronaModel);

        return "success";
    }
}
