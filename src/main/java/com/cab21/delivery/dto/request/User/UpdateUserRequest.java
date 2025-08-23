package com.cab21.delivery.dto.request.User;

import lombok.Data;

import java.util.Date;
@Data
public class UpdateUserRequest {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String registryNumber;
    private String password;
    private String email;
    private String role;
}
