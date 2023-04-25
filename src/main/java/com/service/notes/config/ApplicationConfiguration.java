package com.service.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.time.Clock;

@EnableRetry
@Configuration
public class ApplicationConfiguration {

    @Bean
    public Clock utcClock() {
        return Clock.systemUTC();
    }
}
