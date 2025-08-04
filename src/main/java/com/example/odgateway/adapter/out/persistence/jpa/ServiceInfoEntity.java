package com.example.odgateway.adapter.out.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TBL_SERVICE_INFO")
@NoArgsConstructor
class ServiceInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "URL")
    private String url;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTIme;

    @Builder
    ServiceInfoEntity(Long id, String url, String description, LocalDateTime regDateTIme) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.regDateTIme = regDateTIme;
    }
}
