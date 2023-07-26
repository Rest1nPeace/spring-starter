package com.dmdev.spring.dto;

import com.dmdev.spring.database.entity.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserCreatedEditDto {
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    Integer companyId;
}
