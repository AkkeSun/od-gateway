package com.example.odgateway.application.port.in.command;

import java.util.List;
import lombok.Builder;

@Builder
public record RegisterAccountCommand(
    String username,
    String name,
    String userTel,
    List<String> roles,
    String password
) {

}
