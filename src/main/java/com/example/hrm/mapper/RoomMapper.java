package com.example.hrm.mapper;


import com.example.hrm.domain.Room;
import com.example.hrm.dto.EditRoomRequest;
import com.example.hrm.dto.NewRoomRequest;
import com.example.hrm.dto.RoomDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class)
public interface RoomMapper {
    RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);

    RoomDTO toDto(Room entity);

    Room toEntity(NewRoomRequest request);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Room entity, EditRoomRequest request);
}

