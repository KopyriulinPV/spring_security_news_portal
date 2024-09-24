package com.example.news_portal.service.impl;
import com.example.news_portal.DTO.UserFilter;
import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.Role;
import com.example.news_portal.model.RoleType;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.UserRepository;
import com.example.news_portal.service.UserService;
import com.example.news_portal.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseUserService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll(UserFilter userFilter) {
        return userRepository.findAll(PageRequest.of(
                userFilter.getPageNumber(), userFilter.getPageSize()
        )).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Автор с ID {0} не найден", id
                )));
    }

    @Override
    public User save(User user, RoleType roleType) {
        Role role = new Role();
        role.setAuthority(roleType);
        role.setUser(user);
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existedNews = userRepository.findById(user.getId()).get();
        BeanUtils.copyNonNullProperties(user, existedNews);
        existedNews.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(existedNews);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Username not found!"));
    }

}
