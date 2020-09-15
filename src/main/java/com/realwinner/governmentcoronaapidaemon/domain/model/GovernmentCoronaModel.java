package com.realwinner.governmentcoronaapidaemon.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity(name = "kr_daily_corona_cnt")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class GovernmentCoronaModel {
    @Id
    private int seq;

    private String stateDt;

    private String stateTime;

    private int decideCnt;

    private int clearCnt;

    private int examCnt;

    private int deathCnt;

    private int careCnt;

    private int resutlNegCnt;

    private int accExamCnt;

    private int accExamCompCnt;

    private double accDefRate;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;
}
