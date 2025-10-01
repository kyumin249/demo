package com.example.demo.Model.service;

import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import com.example.demo.Model.domain.Article;
 import com.example.demo.Model.repository.BlogRepository;
 import lombok.RequiredArgsConstructor;
 @Service
 @RequiredArgsConstructor // 생성자자동생성(부분)

public class BlogService {
    @Autowired // 객체주입자동화, 생성자1개면생략가능
    private final BlogRepository blogRepository; // 리포지토리선언
    public List<Article> findAll() { // 게시판전체목록조회
        return blogRepository.findAll();
    }
    public Article save(AddArticleRequest request){
 // DTO가없는경우이곳에직접구현가능
// public ResponseEntity<Article> addArticle(@RequestParam String title, @RequestParam String content) {
 // Article article = Article.builder()
 // .title(title)
 // .content(content)
 // .build();
        return blogRepository.save(request.toEntity());
    }
}
