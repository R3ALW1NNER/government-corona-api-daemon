package com.realwinner.governmentcoronaapidaemon.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "kr_covid19_daily_cnt")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class GovernmentCOVID19Model {
    /* '게시글번호(국내 시도별 발생현황 고유값)' */
    @Id
    private int seq;

    /* '등록일시분초' */
    private String createDt;

    /* '사망자 수' */
    private int deathCnt;

    /* '시도명(한글)' */
    private String gubun;

    /* '시도명(중국어)' */
    private String gubunCn;

    /* '시도명(영어)' */
    private String gubunEn;

    /* '전일대비 증감 수' */
    private int incDec;

    /* '격리 해제 수' */
    private int isolClearCnt;

    /* '10만명당 발생률' */
    private double qurRate;

    /* '기준일시' */
    private String stdDay;

    /* '수정일시분초' */
    private String updateDt;

    /* '확진자 수' */
    private int defCnt;

    /* '격리중 환자수' */
    private int isolIngCnt;

    /* '해외유입 수' */
    private int overFlowCnt;

    /* '지역발생 수 */
    private int localOccCnt;
}
