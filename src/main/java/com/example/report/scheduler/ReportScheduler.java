package com.example.report.scheduler;

import com.example.report.service.JrsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final JrsApiService jrsApiService;
    //@Scheduled(cron = "0/10 * * * * ?") // chạy mỗi 10 giây
    // @Scheduled(cron = "0 0 9 * * ?") // 9h sáng hàng ngày
  //  @Scheduled(cron = "0 0/1 * * * ?") // 1p hàng ngày
    public void scheduleDailyReport() throws Exception {
        jrsApiService.scheduleReport();
    }
}