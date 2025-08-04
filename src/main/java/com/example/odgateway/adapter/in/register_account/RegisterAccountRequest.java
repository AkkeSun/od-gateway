package com.example.odgateway.adapter.in.register_account;
import com.example.odgateway.application.port.in.command.RegisterAccountCommand;
import com.example.odgateway.infrastructure.validation.ValidPassword;
import com.example.odgateway.infrastructure.validation.groups.ValidationGroups.CustomGroups;
import com.example.odgateway.infrastructure.validation.groups.ValidationGroups.NotBlankGroups;
import com.example.odgateway.infrastructure.validation.groups.ValidationGroups.SizeGroups;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;

@Builder
@ValidPassword(groups = CustomGroups.class)
record RegisterAccountRequest(

    @NotBlank(message = "접속 계정은 필수값 입니다", groups = NotBlankGroups.class)
    @Size(max = 30, message = "접속 계정은 30자 이하로 입력 가능 합니다", groups = SizeGroups.class)
    String username,

    @NotBlank(message = "사용자 이름은 필수값 입니다", groups = NotBlankGroups.class)
    @Size(max = 10, message = "사용자 이름은 10자 이하로 입력 가능 합니다", groups = SizeGroups.class)
    String name,

    @NotBlank(message = "사용자 전화번호는 필수값 입니다", groups = NotBlankGroups.class)
    @Size(max = 20, message = "사용자 전화번호는 20자 이하로 입력 가능 합니다", groups = SizeGroups.class)
    String userTel,

    @NotEmpty(message = "권한은 필수값 입니다", groups = NotBlankGroups.class)
    List<String> roles,

    String password,

    String passwordCheck
) {

    RegisterAccountCommand toCommand() {
        return RegisterAccountCommand.builder()
            .username(username)
            .name(name)
            .userTel(userTel)
            .roles(roles)
            .password(password)
            .build();
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return this.toString();
        }
    }
}
