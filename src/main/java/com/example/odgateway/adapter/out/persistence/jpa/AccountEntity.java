package com.example.odgateway.adapter.out.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TBL_ACCOUNT")
class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_TEL")
    private String userTel;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TBL_ACCOUNT_ROLE",
        joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Builder
    AccountEntity(Long id, String username, String password, String name, String userTel,
        boolean status, LocalDateTime regDateTime, Set<RoleEntity> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.userTel = userTel;
        this.status = status;
        this.regDateTime = regDateTime;
        this.roles = roles;
    }
}
