package com.example.news_portal.mapper;

import com.example.news_portal.DTO.UpsertUserRequest;
import com.example.news_portal.DTO.UserListResponse;
import com.example.news_portal.DTO.UserResponse;
import com.example.news_portal.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToUserResponse(User user);

    default UserListResponse userListToUserResponseList(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(users.stream().map(this::userToUserResponse).collect(Collectors.toList()));
        return response;
    }

}
