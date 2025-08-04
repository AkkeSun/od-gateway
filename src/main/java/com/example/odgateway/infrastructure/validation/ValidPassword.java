package com.example.odgateway.infrastructure.validation;

import com.example.odgateway.infrastructure.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "유효하지 않은 비밀번호 입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
