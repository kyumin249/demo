package com.example.demo.Model.Controller;


import com.example.demo.Model.Controller.MemberController;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


import com.example.demo.Model.domain.Board;
import com.example.demo.Model.service.BlogService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class BlogController {
    
    @Autowired
    BlogService blogService;

//     @GetMapping("/article_list") // 게시판 링크 지정
//     public String article_list(Model model) {
//         List<Article> list = blogService.findAll(); // 게시판 리스트
//         model.addAttribute("articles", list); // 모델에 추가
//         return "article_list"; // .HTML 연결
//     }
    @GetMapping("/board_edit/{id}") // 게시판링크지정
    public String board_edit(Model model, @PathVariable Long id) {
    Optional<Board> list = blogService.findById(id); // 선택한게시판글
    if (list.isPresent()) {
        model.addAttribute("board", list.get()); // 존재하면Article 객체를모델에추가
    } else {
 // 처리할로직추가(예: 오류페이지로리다이렉트, 예외처리등)
         return "error_page/board_error"; // 오류처리페이지로연결
    }
    return "board_edit"; // .HTML 연결
    }

    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute Board request) {
        blogService.update(id, request);
        return "redirect:/board_list"; // 글 수정 이후 .html 연결
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }
    // S
    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("board", list.get()); // 존재할 경우 실제 Board 객체를 모델에 추가
        } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/board_error"; // 오류 처리 페이지로 연결
        }
        return "board_view"; // .HTML 연결
    }
    
    // 글쓰기 게시판
    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

@GetMapping("/board_list")
public String board_list(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String keyword, HttpSession session) {

    String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
    String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인
    if (userId == null) {
        return "redirect:/login"; // 로그인 페이지로 리다이렉션
    }
    System.out.println("세션 userId: " + userId); // 서버 IDE 터미널에 세션 값 출력

    int pageSize = 3;
    PageRequest pageable = PageRequest.of(page, pageSize);

    Page<Board> list;
    if (keyword.isEmpty()) {
        list = blogService.findAll(pageable);
    } else {
        list = blogService.searchByKeyword(keyword, pageable);
    }

    int startNum = page * pageSize + 1;

    model.addAttribute("boards", list); // 게시글
    model.addAttribute("totalPages", list.getTotalPages()); // 총 페이지
    model.addAttribute("currentPage", page); // 현재 페이지
    model.addAttribute("keyword", keyword); // 검색 키워드
    model.addAttribute("startNum", startNum); // 시작 번호
    model.addAttribute("email", email); // 로그인 사용자(이메일)

    return "board_list";
}


@PostMapping("/board_list")
public String board_list_post(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String keyword,
        Model model) {
    return board_list(model, page, keyword, null);
}


    
}
