package com.cab21.delivery.dto.response;

import com.cab21.delivery.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;     // JWT
    private String type;      // "Bearer"
    private UserDto user;
}
