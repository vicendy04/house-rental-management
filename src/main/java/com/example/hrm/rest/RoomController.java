package com.example.hrm.rest;

import com.example.hrm.dto.EditRoomRequest;
import com.example.hrm.dto.NewRoomRequest;
import com.example.hrm.dto.RoomDTO;
import com.example.hrm.dto.RoomSearchCriteria;
import com.example.hrm.rest.utils.PageBuilder;
import com.example.hrm.service.RoomService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/rooms")
@RestController
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @PostMapping
    public ResponseEntity<RoomDTO> create(@Valid @RequestBody NewRoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @Valid @RequestBody EditRoomRequest request) {
        return ResponseEntity.ok(roomService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<RoomDTO>> list(@PageableDefault() Pageable pageable) {
        return ResponseEntity.ok(roomService.listRooms(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RoomDTO>> search(RoomSearchCriteria criteria) {
        PageRequest pageRequest = PageBuilder.of(criteria);
        return ResponseEntity.ok(roomService.search(criteria, pageRequest));
    }
}
