package com.realwinner.governmentcoronaapidaemon.config;

import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCOVID19Model;
import com.realwinner.governmentcoronaapidaemon.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Slf4j
@Component
public class GovernmentCOVID19ApiHttpClient {

    @Value("${government.api-url}")
    private String governmentApiUrl;

    @Value("${government.api-key}")
    private String governmentApiKey;

    @Autowired
    private CustomHttpClient customHttpClient;

    @Autowired
    private DateUtil dateUtil;

    public ArrayList<GovernmentCOVID19Model> getCOVID19Data() {
        ArrayList<GovernmentCOVID19Model> covid19ModelList = new ArrayList<>();

        HttpClient httpClient = customHttpClient.getHttpClient();

        HttpResponse response;

        try {
            URI uri = new URIBuilder(new URI(governmentApiUrl))
                    .addParameter("ServiceKey", URLDecoder.decode(governmentApiKey, StandardCharsets.UTF_8.name()))
                    .addParameter("pageNo", "1")
                    .addParameter("numOfRows", "1000000")
                    .addParameter("startCreateDt", dateUtil.getTodayDateString())
                    .addParameter("endCreateDt", dateUtil.getTodayDateString()).build();

            log.info(uri.toString());

            response = httpClient.execute(new HttpGet(uri));
            HttpEntity httpEntity = response.getEntity();

            DocumentBuilder documentBuilder;
            InputSource inputSource;

            inputSource = new InputSource(new StringReader(EntityUtils.toString(httpEntity, StandardCharsets.UTF_8)));
            inputSource.setEncoding(StandardCharsets.UTF_8.name());

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputSource);

            NodeList list = doc.getElementsByTagName("item");

            try {
                Element element = (Element) list.item(0).getChildNodes();
            } catch (NullPointerException e) {
                log.error("현재 날짜 연동 데이터 없음!");
                return null;
            }

            for (int i = 0; i < list.getLength(); i++) {
                GovernmentCOVID19Model governMentCOVID19Model = new GovernmentCOVID19Model();
                Element element = (Element) list.item(i);
                try {
                    governMentCOVID19Model.setSeq(Integer.parseInt(element.getElementsByTagName("seq").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setSeq(0);
                }

                try {
                    governMentCOVID19Model.setDeathCnt(Integer.parseInt(element.getElementsByTagName("deathCnt").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setDeathCnt(0);
                }

                try {
                    governMentCOVID19Model.setGubun(element.getElementsByTagName("gubun").item(0).getTextContent());
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setGubun(null);
                }

                try {
                    governMentCOVID19Model.setGubunCn(element.getElementsByTagName("gubunCn").item(0).getTextContent());
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setGubunCn(null);
                }

                try {
                    governMentCOVID19Model.setGubunEn(element.getElementsByTagName("gubunEn").item(0).getTextContent());
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setGubunEn(null);
                }

                try {
                    governMentCOVID19Model.setIncDec(Integer.parseInt(element.getElementsByTagName("incDec").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setIncDec(0);
                }

                try {
                    governMentCOVID19Model.setIsolClearCnt(Integer.parseInt(element.getElementsByTagName("isolClearCnt").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setIsolClearCnt(0);
                }

                try {
                    governMentCOVID19Model.setStdDay(element.getElementsByTagName("stdDay").item(0).getTextContent());
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setStdDay(null);
                }

                try {
                    governMentCOVID19Model.setDefCnt(Integer.parseInt(element.getElementsByTagName("defCnt").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setDefCnt(0);
                }

                try {
                    governMentCOVID19Model.setOverFlowCnt(Integer.parseInt(element.getElementsByTagName("overFlowCnt").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setOverFlowCnt(0);
                }

                try {
                    governMentCOVID19Model.setLocalOccCnt(Integer.parseInt(element.getElementsByTagName("localOccCnt").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setLocalOccCnt(0);
                }

                try {
                    governMentCOVID19Model.setIsolIngCnt(Integer.parseInt(element.getElementsByTagName("isolIngCnt").item(0).getTextContent()));
                } catch (NullPointerException e) {
                    governMentCOVID19Model.setIsolIngCnt(0);
                }

                try {
                    if (element.getElementsByTagName("qurRate")
                            .item(0)
                            .getTextContent()
                            .replaceAll(" ", "")
                            .replaceAll("　", "")
                            .equals("-")) {
                        governMentCOVID19Model.setQurRate(0);
                    } else {
                        governMentCOVID19Model.setQurRate(Double.parseDouble(element.getElementsByTagName("qurRate").item(0).getTextContent()));
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    governMentCOVID19Model.setQurRate(0);
                }

                try {
                    if (element.getElementsByTagName("createDt").item(0).getTextContent().equals("'") |
                            element.getElementsByTagName("createDt").item(0).getTextContent().equals("null")) {
                        governMentCOVID19Model.setCreateDt(null);
                    } else {
                        governMentCOVID19Model.setCreateDt(element.getElementsByTagName("createDt").item(0).getTextContent());
                    }

                } catch (NullPointerException e) {
                    governMentCOVID19Model.setCreateDt(null);
                }

                try {
                    if (element.getElementsByTagName("updateDt").item(0).getTextContent().equals("") |
                            element.getElementsByTagName("updateDt").item(0).getTextContent().equals("null")) {
                        governMentCOVID19Model.setUpdateDt(null);
                    } else {
                        governMentCOVID19Model.setUpdateDt(element.getElementsByTagName("updateDt").item(0).getTextContent());
                    }

                } catch (NullPointerException e) {
                    governMentCOVID19Model.setUpdateDt(null);
                }

                covid19ModelList.add(governMentCOVID19Model);
            }


        } catch (Exception e) {
            log.error("getCoronaData Fail !! :: ", e);
        }

        return covid19ModelList;
    }
}
