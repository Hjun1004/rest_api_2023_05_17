package com.ll.rest.boundedContext.article.controller;

import com.ll.rest.base.rsData.RsData;
import com.ll.rest.boundedContext.article.entity.Article;
import com.ll.rest.boundedContext.article.service.ArticleService;
import com.ll.rest.boundedContext.member.controller.ApiV1MemberController;
import com.ll.rest.boundedContext.member.entity.Member;
import com.ll.rest.boundedContext.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/articles", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ArticlesController", description = "게시물 CRUD컨트롤러")
public class ApiV1ArticlesController {
    private final ArticleService articleService;

    @AllArgsConstructor
    @Getter
    public static class ArticlesResponse {
        private final List<Article> articles;
    }


    // consumes = ALL_VALUE => 나는 딱히 JSON을 입력받기를 고집하지 않겠다.
    // 매개변수로 뭐가 오던 상관없다. null도 된다.
    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "게시물들")
    public RsData<ArticlesResponse> articles(){
        List<Article> articles = articleService.findAll();

        return RsData.of(
                "S-1",
                "성공",
                new ArticlesResponse(articles)
        );
    }


}
