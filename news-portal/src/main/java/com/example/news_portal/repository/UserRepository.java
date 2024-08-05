package com.example.news_portal.repository;
import com.example.news_portal.model.Comment;
import com.example.news_portal.model.News;
import com.example.news_portal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

}
