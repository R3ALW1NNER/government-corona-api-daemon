package com.realwinner.governmentcoronaapidaemon.config;

import com.realwinner.governmentcoronaapidaemon.domain.model.GovernmentCoronaModel;
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
import java.time.LocalDateTime;

@Slf4j
@Component
public class CoronaApiHttpCall {

    @Value("${government.api-url}")
    private String governmentApiUrl;

    @Value("${government.api-key}")
    private String governmentApiKey;

    @Autowired
    private CustomHttpClient customHttpClient;

    @Autowired
    private DateUtil dateUtil;

    public GovernmentCoronaModel getCoronaData() {

        GovernmentCoronaModel governmentCoronaModel = new GovernmentCoronaModel();

        HttpClient httpClient = customHttpClient.getHttpClient();
        HttpResponse response;

        try {
            URI uri = new URIBuilder(new URI(governmentApiUrl))
                    .addParameter("ServiceKey", URLDecoder.decode(governmentApiKey, StandardCharsets.UTF_8.name()))
                    .addParameter("pageNo", "1")
                    .addParameter("numOfRows", "1000")
                    .addParameter("startCreateDt", dateUtil.getTodayDateString())
                    .addParameter("endCreateDt", dateUtil.getTodayDateString()).build();

            response = httpClient.execute(new HttpGet(uri));
            HttpEntity httpEntity = response.getEntity();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;
            InputSource inputSource;

            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            inputSource = new InputSource(new StringReader(EntityUtils.toString(httpEntity)));
            Document doc = documentBuilder.parse(inputSource);

            NodeList list = doc.getElementsByTagName("item");

            Element element;

            try {
                element = (Element) list.item(0).getChildNodes();
            } catch (NullPointerException e) {
                log.error("현재 날짜 연동 데이터 없음!");
                return null;
            }

            governmentCoronaModel.setSeq(Integer.parseInt(element.getElementsByTagName("seq").item(0).getTextContent()));
            governmentCoronaModel.setStateDt(element.getElementsByTagName("stateDt").item(0).getTextContent());
            governmentCoronaModel.setStateTime(element.getElementsByTagName("stateTime").item(0).getTextContent());
            governmentCoronaModel.setDecideCnt(Integer.parseInt(element.getElementsByTagName("decideCnt").item(0).getTextContent()));
            governmentCoronaModel.setClearCnt(Integer.parseInt(element.getElementsByTagName("clearCnt").item(0).getTextContent()));
            governmentCoronaModel.setExamCnt(Integer.parseInt(element.getElementsByTagName("examCnt").item(0).getTextContent()));
            governmentCoronaModel.setDeathCnt(Integer.parseInt(element.getElementsByTagName("deathCnt").item(0).getTextContent()));
            governmentCoronaModel.setCareCnt(Integer.parseInt(element.getElementsByTagName("careCnt").item(0).getTextContent()));
            governmentCoronaModel.setResutlNegCnt(Integer.parseInt(element.getElementsByTagName("resutlNegCnt").item(0).getTextContent()));
            governmentCoronaModel.setAccExamCnt(Integer.parseInt(element.getElementsByTagName("accExamCnt").item(0).getTextContent()));
            governmentCoronaModel.setAccExamCompCnt(Integer.parseInt(element.getElementsByTagName("accExamCompCnt").item(0).getTextContent()));
            governmentCoronaModel.setAccDefRate(Double.parseDouble(element.getElementsByTagName("accDefRate").item(0).getTextContent()));

            if (element.getElementsByTagName("createDt").item(0).getTextContent().equals("null")) {
                governmentCoronaModel.setCreateDt(null);
            } else {
                governmentCoronaModel.setCreateDt(LocalDateTime.parse(element.getElementsByTagName("createDt").item(0).getTextContent(),
                        dateUtil.formatterYYYYMMDDHHMMSSSSS));
            }

            if (element.getElementsByTagName("updateDt").item(0).getTextContent().equals("null")) {
                governmentCoronaModel.setUpdateDt(null);
            } else {
                governmentCoronaModel.setUpdateDt(LocalDateTime.parse(element.getElementsByTagName("updateDt").item(0).getTextContent(),
                        dateUtil.formatterYYYYMMDDHHMMSSSSS));
            }

        } catch (Exception e) {
            log.error("getCoronaData Fail !! :: ", e);
        }

        return governmentCoronaModel;
    }
}
