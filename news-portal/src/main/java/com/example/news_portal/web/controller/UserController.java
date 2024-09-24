package com.example.news_portal.web.controller;

import com.example.news_portal.DTO.UpsertUserRequest;
import com.example.news_portal.DTO.UserFilter;
import com.example.news_portal.DTO.UserListResponse;
import com.example.news_portal.DTO.UserResponse;
import com.example.news_portal.aopSpringSecurity.UserVerificationSpring;
import com.example.news_portal.mapper.UserMapper;
import com.example.news_portal.model.RoleType;
import com.example.news_portal.model.User;
import com.example.news_portal.service.impl.DataBaseUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

        private final DataBaseUserService dataBaseUserService;

        private final UserMapper userMapper;

        @GetMapping("/userFilter")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<UserListResponse> findAll(@AuthenticationPrincipal UserDetails userDetails, UserFilter userFilter) {
            return ResponseEntity.ok(userMapper.userListToUserResponseList(dataBaseUserService.findAll(userFilter)));
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
        @UserVerificationSpring
        public ResponseEntity<UserResponse> findById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id) {
            return ResponseEntity.ok(userMapper.userToUserResponse(dataBaseUserService.findById(id)));
        }

        @PostMapping
        public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request, @RequestParam RoleType roleType) {
            User user = dataBaseUserService.save(userMapper.requestToUser(request), roleType);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userMapper.userToUserResponse(user));
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
        @UserVerificationSpring
        public ResponseEntity<UserResponse> update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long userId,
                                                   @RequestBody @Valid UpsertUserRequest request) {
            User updatedUser = dataBaseUserService.update(userMapper.requestToUser(userId, request));
            return ResponseEntity.ok(userMapper.userToUserResponse(updatedUser));
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
        @UserVerificationSpring
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            dataBaseUserService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
}
