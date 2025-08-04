package com.example.odgateway.application.port.in.command;


import lombok.Builder;

@Builder
public record RegisterTokenCommand(
    String username,
    String password
) {

}
