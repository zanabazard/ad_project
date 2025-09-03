package com.cab21.delivery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.dto.request.User.CreateUserRequest;
import com.cab21.delivery.model.User;
import com.cab21.delivery.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /***
     * Хэрэглэгч үүсгэх сервис
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok().body(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    /***
     * Хэрэглэгчийн мэдээлэл засах сервис
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok().body(userService.createUser(request));
    }

    /***
     * Хэрэглэгчийн төлөв идэвхгүй болгох сервис
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    /***
     * Хэрэглэгчийн төлөв идэвхтэй болгох сервис
     */
    @PutMapping("/{id}/reactivate")
    public ResponseEntity<String> reactivate(@PathVariable Long id) {
        return ResponseEntity.ok(userService.reactivateUser(id));
    }
    /***
     * Хэрэглэгчийн grid мэдээлэл авах сервис
     */
    @PostMapping("/grid")
    public ResponseEntity<?> getGrid(@RequestBody nm.common.grid.request.GridRequest request) {
        return ResponseEntity.ok(userService.getGrid(request));
    }
}
