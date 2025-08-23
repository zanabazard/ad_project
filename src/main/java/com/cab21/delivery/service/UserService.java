package com.cab21.delivery.service;

import com.cab21.delivery.dto.request.User.CreateUserRequest;
import com.cab21.delivery.dto.request.User.UpdateUserRequest;
import jakarta.transaction.Transactional;

public interface  UserService {

    String createUser(CreateUserRequest req);

    String updateUser(UpdateUserRequest req);

    String deactivateUser(Long id);

    String reactivateUser(Long id);
}
