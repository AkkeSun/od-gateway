package com.example.odgateway.domain.model;
import com.example.odgateway.application.port.in.command.RegisterAccountCommand;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Account {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String userTel;
    private List<Role> roles;
    private boolean status;
    private LocalDateTime regDateTime;

    @Builder
    public Account(Long id, String username, String password, String name, String userTel,
        List<Role> roles, boolean status, LocalDateTime regDateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.userTel = userTel;
        this.roles = roles;
        this.status = status;
        this.regDateTime = regDateTime;
    }

    public static Account of(RegisterAccountCommand command, Map<String, Role> validRoles){
        return Account.builder()
            .username(command.username())
            .password(command.password())
            .name(command.name())
            .userTel(command.userTel())
            .roles(command.roles().stream()
                .map(validRoles::get)
                .toList())
            .status(true)
            .regDateTime(LocalDateTime.now())
            .build();
    }

    public static Account of(Claims claims) {
        return Account.builder()
            .username(claims.getSubject())
            .roles(Arrays.stream(claims.get("roles").toString().split(","))
                .map(role -> Role.builder().name(role).build())
                .toList())
            .build();
    }

    public List<String> getRoleNames() {
        return roles.stream()
            .map(Role::name)
            .toList();
    }
}
