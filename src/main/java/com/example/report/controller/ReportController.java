package com.example.report.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final RestTemplate restTemplate; // inject từ RestTemplateConfig

    @GetMapping("/run-now")
    public ResponseEntity<?> runNow(@RequestParam String name,
                                    @RequestParam(defaultValue = "pdf") String format) {
        try {
            String url = String.format(
                    "http://localhost:8080/jasperserver-pro/rest_v2/reports/reports/demo/%s.%s",
                    name, format
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("jasperadmin", "jasperadmin");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
            return ResponseEntity.ok()
                    .contentType(format.equalsIgnoreCase("pdf") ? MediaType.APPLICATION_PDF : MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name + "." + format)
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy báo cáo: " + e.getMessage());
        }
    }
}
