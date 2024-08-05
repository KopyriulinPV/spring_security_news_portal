package com.example.news_portal.service.impl;
import com.example.news_portal.DTO.NewsFilter;
import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.News;
import com.example.news_portal.repository.NewsRepository;
import com.example.news_portal.repository.NewsSpecification;
import com.example.news_portal.service.NewsService;
import com.example.news_portal.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseNewsService implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Override
    public List<News> filterBy(NewsFilter newsFilter) {
        return newsRepository.findAll(NewsSpecification.withFilter(newsFilter),
                PageRequest.of(
                        newsFilter.getPageNumber(), newsFilter.getPageSize()
                )).getContent();
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Новость с ID {0} не найдена", id
                )));
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        News existedNews = newsRepository.findById(news.getId()).get();
        BeanUtils.copyNonNullProperties(news, existedNews);
        return newsRepository.save(existedNews);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
