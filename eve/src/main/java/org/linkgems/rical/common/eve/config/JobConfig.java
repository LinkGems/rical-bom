package org.linkgems.rical.common.eve.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 定时任务配置
 * @author: meidanlong
 * @date: 2022/11/22 10:33 AM
 */
@Slf4j
@Component
public class JobConfig {

    @Value("${job.admin.addresses}")
    private String adminAddresses;
    @Value("${job.accessToken}")
    private String accessToken;
    @Value("${job.executor.appname}")
    private String appname;
    @Value("${job.executor.port}")
    private String port;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
//        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(Integer.parseInt(port));
        xxlJobSpringExecutor.setAccessToken(accessToken);
//        xxlJobSpringExecutor.setLogPath(logPath);
//        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobSpringExecutor;
    }
}
