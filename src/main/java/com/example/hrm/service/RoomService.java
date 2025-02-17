package com.example.hrm.service;

import com.example.hrm.domain.Company;
import com.example.hrm.dto.EditRoomRequest;
import com.example.hrm.dto.NewRoomRequest;
import com.example.hrm.dto.RoomDTO;
import com.example.hrm.repository.CompanyRepository;
import com.example.hrm.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.hrm.mapper.RoomMapper.ROOM_MAPPER;

@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final CompanyRepository companyRepository;

    public RoomService(RoomRepository roomRepository, CompanyRepository companyRepository) {
        this.roomRepository = roomRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public RoomDTO create(NewRoomRequest request) {
        var room = ROOM_MAPPER.toEntity(request);
        var company = getCompanyOrThrow(request.getCompanyId());
        room.setCompany(company);
        return ROOM_MAPPER.toDto(roomRepository.save(room));
    }

    @Transactional
    public RoomDTO update(Long id, EditRoomRequest request) {
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        var company = getCompanyOrThrow(request.getCompanyId());
        room.setCompany(company);
        ROOM_MAPPER.partialUpdate(room, request);
        return ROOM_MAPPER.toDto(roomRepository.save(room));
    }

    @Transactional
    public void delete(Long id) {
        int deletedRow = roomRepository.softDelete(id);
        if (deletedRow == 0) throw new RuntimeException("Room not found");
    }

    @Transactional(readOnly = true)
    public Page<Long> searchForIds(String buildingName, Pageable pageable) {
        if (buildingName == null || buildingName.trim().isEmpty())
            return roomRepository.findIds(pageable);
        return roomRepository.searchForIds(buildingName, pageable);
    }

    private Company getCompanyOrThrow(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }
}