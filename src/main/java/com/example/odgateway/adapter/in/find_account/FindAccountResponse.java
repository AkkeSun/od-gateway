package com.example.odgateway.adapter.in.find_account;

import com.example.odgateway.application.service.find_account.FindAccountServiceResponse;
import java.util.List;
import lombok.Builder;

@Builder
record FindAccountResponse(
    Long id,
    String username,
    String name,
    String userTel,
    List<String> roles,
    String regDateTime
) {

    static FindAccountResponse of(FindAccountServiceResponse serviceResponse) {
        return FindAccountResponse.builder()
            .id(serviceResponse.id())
            .username(serviceResponse.username())
            .name(serviceResponse.name())
            .userTel(serviceResponse.userTel())
            .roles(serviceResponse.roles())
            .regDateTime(serviceResponse.regDateTime())
            .build();
    }
}
