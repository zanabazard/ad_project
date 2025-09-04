package com.cab21.delivery.dto.request.User;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String phone;
    private String newPassword;
}
