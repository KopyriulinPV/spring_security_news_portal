package com.example.news_portal.mapper;
import com.example.news_portal.DTO.NewsResponseForList;
import com.example.news_portal.DTO.NewsResponseForSingleCall;
import com.example.news_portal.DTO.UpsertNewsRequest;
import com.example.news_portal.model.News;
import com.example.news_portal.repository.CommentRepository;
import com.example.news_portal.service.impl.DataBaseCategoryService;
import com.example.news_portal.service.impl.DataBaseUserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {
    @Autowired
    private DataBaseUserService dataBaseUserService;

    @Autowired
    private DataBaseCategoryService dataBaseCategoryService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;



    @Override
    public News requestToNews(UpsertNewsRequest request, String username) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setAuthor(dataBaseUserService.findByUsername(username));
        news.setCategory(dataBaseCategoryService.findById(request.getCategory_id()));
        return news;
    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest request, String username) {
        News news = requestToNews(request, username);
        news.setId(newsId);
        return news;
    }

    @Override
    public NewsResponseForList newsToNewsResponseForList(News news) {
        NewsResponseForList newsResponseForList = new NewsResponseForList();
        newsResponseForList.setId(news.getId());
        newsResponseForList.setTitle(news.getTitle());
        newsResponseForList.setContent(news.getContent());
        newsResponseForList.setAuthor_id(news.getAuthor().getId());
        newsResponseForList.setCategory_id(news.getCategory().getId());
        newsResponseForList.setCountComments(commentRepository.findAllByNews(news).stream().count());
        return newsResponseForList;
    }

    @Override
    public NewsResponseForSingleCall newsToNewsResponseForSingleCall(News news) {
        NewsResponseForSingleCall newsResponseForSingleCall = new NewsResponseForSingleCall();
        newsResponseForSingleCall.setId(news.getId());
        newsResponseForSingleCall.setTitle(news.getTitle());
        newsResponseForSingleCall.setContent(news.getContent());
        newsResponseForSingleCall.setAuthor_id(news.getAuthor().getId());
        newsResponseForSingleCall.setCategory_id(news.getCategory().getId());
        newsResponseForSingleCall.setComments(commentRepository.findAllByNews(news)
                .stream()
                .map(q -> commentMapper.commentToCommentResponse(q)).toList());
        return newsResponseForSingleCall;
    }
}
