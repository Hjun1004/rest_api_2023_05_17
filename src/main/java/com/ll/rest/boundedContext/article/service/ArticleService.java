package com.ll.rest.boundedContext.article.service;

import com.ll.rest.base.rsData.RsData;
import com.ll.rest.boundedContext.article.entity.Article;
import com.ll.rest.boundedContext.article.repository.ArticleRepository;
import com.ll.rest.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public RsData<Article> write(Member author, String subject, String content){
        Article article = Article.builder()
                .author(author)
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        return RsData.of(
                "S-1",
                "게시물이 생성되었습니다.",
                article
        );
    }

    public List<Article> findAll() {
        return articleRepository.findAllByOrderByIdDesc();
    }
}
