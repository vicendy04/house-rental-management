package com.example.hrm.rest;

import com.example.hrm.dto.EditRoomRequest;
import com.example.hrm.dto.NewRoomRequest;
import com.example.hrm.dto.RoomDTO;
import com.example.hrm.service.RoomService;
import com.github.benmanes.caffeine.cache.LoadingCache;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/rooms")
@RestController
public class RoomController {
    private final RoomService roomService;
    private final LoadingCache<Long, RoomDTO> cache;

    public RoomController(RoomService roomService, LoadingCache<Long, RoomDTO> cache) {
        this.roomService = roomService;
        this.cache = cache;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(cache.get(id));
    }

    @PostMapping
    public ResponseEntity<RoomDTO> create(@Valid @RequestBody NewRoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @Valid @RequestBody EditRoomRequest request) {
        RoomDTO updated = roomService.update(id, request);
        cache.invalidate(id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        cache.invalidate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<RoomDTO>> list(
            @RequestParam(required = false) String buildingName,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        Page<Long> pagedRoomIds = roomService.searchForIds(buildingName, pageable);
        List<RoomDTO> rooms = List.copyOf(cache.getAll(pagedRoomIds).values());
        return ResponseEntity.ok(new PageImpl<>(rooms, pagedRoomIds.getPageable(), pagedRoomIds.getTotalElements()));
    }
}
