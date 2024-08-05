package com.example.news_portal.web.controller;

import com.example.news_portal.DTO.UpsertUserRequest;
import com.example.news_portal.DTO.UserFilter;
import com.example.news_portal.DTO.UserListResponse;
import com.example.news_portal.DTO.UserResponse;
import com.example.news_portal.mapper.UserMapper;
import com.example.news_portal.model.User;
import com.example.news_portal.service.impl.DataBaseUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

        private final DataBaseUserService dataBaseUserService;

        private final UserMapper userMapper;

        @GetMapping("/userFilter")
        public ResponseEntity<UserListResponse> findAll(UserFilter userFilter) {
            return ResponseEntity.ok(userMapper.userListToUserResponseList(dataBaseUserService.findAll(userFilter)));
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserResponse> findById(@PathVariable long id) {
            return ResponseEntity.ok(userMapper.userToUserResponse(dataBaseUserService.findById(id)));
        }

        @PostMapping
        public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
            User user = dataBaseUserService.save(userMapper.requestToUser(request));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userMapper.userToUserResponse(user));
        }

        @PutMapping("/{id}")
        public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId, @RequestBody @Valid UpsertUserRequest request) {
            User updatedUser = dataBaseUserService.update(userMapper.requestToUser(userId, request));
            return ResponseEntity.ok(userMapper.userToUserResponse(updatedUser));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            dataBaseUserService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
}
