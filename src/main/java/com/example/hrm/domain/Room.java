package com.example.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;


@Getter
@Setter
@SQLDelete(sql = "UPDATE room SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
@Table
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber; // 2C

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType; // 1K

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String buildingName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Column(nullable = false)
    private BigDecimal shortTermPrice;  // 4,500円/月

    @Column(nullable = false)
    private BigDecimal middleTermPrice; // 75,000円/月

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String imageUrl;

    private boolean deleted = Boolean.FALSE;
}
