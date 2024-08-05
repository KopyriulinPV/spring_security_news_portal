package com.example.news_portal.mapper;
import com.example.news_portal.DTO.*;
import com.example.news_portal.model.News;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    News requestToNews (UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    NewsResponseForSingleCall newsToNewsResponseForSingleCall(News news);

    NewsResponseForList newsToNewsResponseForList(News news);

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse response = new NewsListResponse();
        response.setNews(newsList.stream().map(this::newsToNewsResponseForList).collect(Collectors.toList()));
        return response;
    }

}
