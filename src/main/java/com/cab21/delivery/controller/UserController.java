package com.cab21.delivery.controller;

import com.cab21.delivery.dto.request.User.CreateUserRequest;
import com.cab21.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cab21.delivery.model.User;

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
}
