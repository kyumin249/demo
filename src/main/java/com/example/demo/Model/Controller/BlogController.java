package com.example.demo.Model.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.ui.Model;

import com.example.demo.Model.domain.Article;
import com.example.demo.Model.service.AddArticleRequest;
import com.example.demo.Model.service.BlogService;

@Controller
public class BlogController {
    
    @Autowired
    BlogService blogService;

    @GetMapping("/article_list") // 게시판 링크 지정
    public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }
    @GetMapping("/article_edit/{id}") // 게시판링크지정
    public String article_edit(Model model, @PathVariable Long id) {
    Optional<Article> list = blogService.findById(id); // 선택한게시판글
    if (list.isPresent()) {
        model.addAttribute("article", list.get()); // 존재하면Article 객체를모델에추가
    } else {
 // 처리할로직추가(예: 오류페이지로리다이렉트, 예외처리등)
        return "error"; // 오류처리페이지로연결
    }
    return "article_edit"; // .HTML 연결
    }

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list"; // 글 수정 이후 .html 연결
    }
}
