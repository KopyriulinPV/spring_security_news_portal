package com.example.news_portal.service;
import com.example.news_portal.DTO.NewsFilter;
import com.example.news_portal.model.Category;
import com.example.news_portal.model.News;

import java.util.List;

public interface NewsService {

    List<News> filterBy(NewsFilter newsFilter);

    List<News> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);

}
