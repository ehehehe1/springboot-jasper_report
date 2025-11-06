package com.example.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class JrsApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${jasper.server.url:http://localhost:8080/jasperserver-pro}")
    private String jasperUrl;

    @Value("${jasper.username:jasperadmin}")
    private String username;

    @Value("${jasper.password:jasperadmin}")
    private String password;

    public void scheduleReport() {
        // JSON đã sửa chữa dựa trên lỗi serialization và tài liệu REST API v2 của JasperReports Server
        String jobJson = """
                {
                  "version": -1,
                  "label": "Test Job",
                  "description": "Run report demo/test",
                  "baseOutputFilename": "Test_Report",
                  "outputFormats": {
                    "outputFormat": ["PDF"]
                  },
                  "source": {
                    "reportUnitURI": "/reports/demo/test",
                    "parameters": {
                      "parameterValues": {}
                    }
                  },
                  "trigger": {
                    "simpleTrigger": {
                      "startType": 1,
                      "timezone": "Asia/Ho_Chi_Minh",
                      "occurrenceCount": -1,
                      "recurrenceInterval": 1,
                      "recurrenceIntervalUnit": "MINUTE",
                      "misfireInstruction": 0
                    }
                  },
                  "repositoryDestination": {
                    "folderURI": "/reports/demo/output",
                    "saveToRepository": true,
                    "overwriteFiles": true,
                    "sequentialFilenames": false,
                    "outputDescription": "Generated report output"
                  },
                  "mailNotification": {
                    "toAddresses": {
                      "address": []
                    },
                    "subject": "Scheduled Report",
                    "messageText": "This is an automated report"
                  }
                }
                """;

        String url = jasperUrl + "/rest_v2/jobs";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password); // Sử dụng Basic Auth cho xác thực
        headers.setContentType(MediaType.valueOf("application/job+json"));
        headers.setAccept(Collections.singletonList(MediaType.valueOf("application/job+json")));

        HttpEntity<String> entity = new HttpEntity<>(jobJson, headers);

        // Gửi yêu cầu PUT để lập lịch job
        restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }
}