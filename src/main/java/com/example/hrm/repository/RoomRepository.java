package com.example.hrm.repository;

import com.example.hrm.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    @EntityGraph(attributePaths = "company")
    Optional<Room> findRoomById(@Param("id") Long id);

    @EntityGraph(attributePaths = "company")
    @Query(
            value = """
                    SELECT r.id
                    FROM Room r
                    WHERE r.buildingName LIKE :keyword%
                    """,
            countQuery = """
                    SELECT count(r)
                    FROM Room r
                    """)
    Page<Long> searchForIds(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE Room r SET r.deleted = true WHERE r.id = :id")
    int softDelete(@Param("id") Long id);

    @EntityGraph(attributePaths = "company")
    List<Room> findAllByIdIn(Set<? extends Long> ids);

    @Query(
            value = """
                    SELECT r.id
                    FROM Room r
                    """,
            countQuery = """
                    SELECT count(r)
                    FROM Room r
                    """)
    Page<Long> findIds(Pageable pageable);
}
