package com.cab21.delivery.dto.request.User;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private Long phone;
    private String newPassword;
}
