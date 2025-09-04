package com.cab21.delivery.service;

import com.cab21.delivery.dto.request.User.ChangePasswordRequest;
import com.cab21.delivery.dto.request.User.CreateUserRequest;
import com.cab21.delivery.dto.request.User.UpdateUserRequest;
import com.cab21.delivery.model.User;

import nm.common.grid.request.GridRequest;
import nm.common.grid.response.GridResponse;

public interface UserService {

    String createUser(CreateUserRequest req);

    String updateUser(UpdateUserRequest req);

    String deactivateUser(Long id);

    String reactivateUser(Long id);

    User getUserByPhone(String phone);

    GridResponse getGrid(GridRequest request);

    String changePassword(ChangePasswordRequest req);
}
