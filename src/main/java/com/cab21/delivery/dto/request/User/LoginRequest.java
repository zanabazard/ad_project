package com.cab21.delivery.dto.request.User;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
