package com.example.demo.Model.Controller;

import com.example.demo.Model.domain.Board;
import com.example.demo.Model.service.AddBoardRequest;
import com.example.demo.Model.service.BlogService;
 import lombok.RequiredArgsConstructor;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // @Controller+@ResponseBody
public class BlogRestController {
     private final BlogService blogService; // 서비스선언
    // @PostMapping("/api/articles") // post 요청
    // public ResponseEntity<Article> addArticle(@ModelAttribute AddArticleRequest request) { // 아직없음(에러)
    //     Article saveArticle= blogService.save(request); // 게시글저장
    //     return ResponseEntity.status(HttpStatus.CREATED) // 상태코드및게시글정보반환
    //     .body(saveArticle);
    // }
    @PostMapping("/api/boards") // post 요청
    public ResponseEntity<Board> addBoard(@ModelAttribute AddBoardRequest request) { // 아직없음(에러)
        Board saveBoard= blogService.save(request); // 게시글저장
        return ResponseEntity.status(HttpStatus.CREATED) // 상태코드및게시글정보반환
            .body(saveBoard);
    }
    @GetMapping("/favicon.ico")
    public void favicon() {
    // 아무 작업도 하지 않음
    }

}
