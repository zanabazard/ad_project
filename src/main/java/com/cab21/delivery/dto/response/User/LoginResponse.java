package com.cab21.delivery.dto.response.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long   userId;
    private String username;
    private String role;
}
