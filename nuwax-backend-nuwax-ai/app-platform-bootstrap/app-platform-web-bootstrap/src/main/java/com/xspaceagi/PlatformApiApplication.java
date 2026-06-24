package com.xspaceagi;

import com.xspaceagi.system.domain.log.EnableLogPrint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@EnableLogPrint
@SpringBootApplication(scanBasePackages = {"com.xspaceagi", "io.springfox"})
public class PlatformApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApiApplication.class, args);
    }

}
