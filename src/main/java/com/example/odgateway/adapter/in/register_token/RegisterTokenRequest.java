package com.example.odgateway.adapter.in.register_token;

import com.example.odgateway.application.port.in.command.RegisterTokenCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
record RegisterTokenRequest(
    @NotBlank(message = "접속 계정은 필수값 입니다.")
    String username,

    @NotBlank(message = "비밀번호는 필수값 입니다.")
    String password
) {

    RegisterTokenCommand toCommand() {
        return RegisterTokenCommand.builder()
            .username(username)
            .password(password)
            .build();
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
