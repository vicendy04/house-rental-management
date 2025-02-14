package com.example.hrm.service;

import com.example.hrm.dto.RoomDTO;
import com.example.hrm.repository.RoomRepository;
import com.github.benmanes.caffeine.cache.CacheLoader;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.hrm.mapper.RoomMapper.ROOM_MAPPER;

@Slf4j
@Service
public class RoomCacheService implements CacheLoader<Long, RoomDTO> {
    private final RoomRepository roomRepository;

    public RoomCacheService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public RoomDTO load(Long key) {
        log.info("Cache miss! Fetching single ID from DB: " + key);
        var room = roomRepository.findRoomById(key)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return ROOM_MAPPER.toDto(room);
    }


    @Transactional(readOnly = true)
    @Override
    public Map<? extends Long, ? extends @NonNull RoomDTO> loadAll(Set<? extends Long> keys) {
        log.info("Cache miss! Fetching batch  IDs from DB: " + keys);
        return roomRepository.findAllByIdIn(keys)
                .stream()
                .map(ROOM_MAPPER::toDto)
                .collect(Collectors.toMap(RoomDTO::getId, Function.identity()));
    }
}
