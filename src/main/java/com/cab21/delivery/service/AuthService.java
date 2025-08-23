package com.cab21.delivery.service;

import com.cab21.delivery.dto.request.User.LoginRequest;
import com.cab21.delivery.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest req);
}
