package com.example.hrm.config;

import com.example.hrm.dto.RoomDTO;
import com.example.hrm.service.RoomCacheService;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class LocalCacheConfig {
    @Bean
    public LoadingCache<Long, RoomDTO> cache(RoomCacheService roomCacheService, MeterRegistry meterRegistry) {
        LoadingCache<Long, RoomDTO> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(1000)
                .recordStats()
                .build(roomCacheService);
        CaffeineCacheMetrics.monitor(meterRegistry, cache, "testCache");
        return cache;
    }
}
