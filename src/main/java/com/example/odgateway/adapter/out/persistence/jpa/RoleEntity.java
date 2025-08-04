package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.domain.model.Role;
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
@Table(name = "TBL_ROLE")
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    public RoleEntity(Long id, String name, String description, LocalDateTime regDateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.regDateTime = regDateTime;
    }

    Role toDomain() {
        return Role.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();
    }

    static RoleEntity of(Role role) {
        return RoleEntity.builder()
            .id(role.id())
            .name(role.name())
            .description(role.description())
            .build();
    }
}
