package com.example.news_portal.service;

import com.example.news_portal.DTO.UserFilter;
import com.example.news_portal.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> findAll(UserFilter userFilter);

    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

}
