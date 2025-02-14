package com.example.hrm.repository;

import com.example.hrm.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    @EntityGraph(attributePaths = "company")
    Optional<Room> findDetailById(@Param("id") Long id);

    @EntityGraph(attributePaths = "company")
    @Query("SELECT r FROM Room r")
    Page<Room> listRooms(Pageable pageable);

    @EntityGraph(attributePaths = "company")
    @Query("SELECT r FROM Room r WHERE r.buildingName LIKE :keyword%")
    Page<Room> search(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE Room r SET r.deleted = true WHERE r.id = :id")
    int softDelete(@Param("id") Long id);
}
