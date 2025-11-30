package com.example.demo.Model.Controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.Model.domain.Member;
import com.example.demo.Model.service.AddBoardRequest;
import com.example.demo.Model.service.AddMemberRequest;
import com.example.demo.Model.service.BlogService;
import com.example.demo.Model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class MemberController {
    
    @Autowired
    MemberService memberService;
    
     @GetMapping("/join_new") // 회원 가입 페이지 연결
    public String join_new() {
        return "join_new"; // .HTML 연결
    }
    @PostMapping("/api/members") // 회원 가입 저장
    public String addmembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end"; // .HTML 연결
    }
 

    @GetMapping("/login") // 로그인 페이지 연결
    public String member_login() {
        return "login"; // .HTML 연결
    }
     @PostMapping("/api/login_check") // 아이디, 패스워드 로그인 체크
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model, HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
            if (session != null) {
                session.invalidate(); // 기존 세션 무효화
                Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID 초기화
                cookie.setPath("/"); // 쿠키 경로
                cookie.setMaxAge(0); // 쿠키 삭제(0으로 설정)
                response.addCookie(cookie); // 응답으로 쿠키 전달
            }
            session = request2.getSession(true); // 새로운 세션 생성
            return "redirect:/board_list"; // 로그인 성공 시 게시판 리스트 페이지로 리다이렉트
 
        }  catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
        
    }

     @GetMapping("/api/logout") // 로그아웃버튼동작
    public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false); // 기존세션가져오기(존재하지않으면null 반환)
            session.invalidate(); // 기존세션무효화
            Cookie cookie= new Cookie("JSESSIONID", null); // 기본이름은JSESSIONID
            cookie.setPath("/"); // 쿠키의경로
            cookie.setMaxAge(0); // 쿠키만료0이면삭제
            response.addCookie(cookie); // 응답에쿠키설정
            session = request2.getSession(true); // 새로운세션생성
            System.out.println("세션userId: " + session.getAttribute("userId" )); // 초기화후IDE 터미널에세션값출력
            return "login"; // 로그인페이지로리다이렉트   
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러메시지전달
            return "login"; // 로그인실패시로그인페이지로리다이렉트
        }
    }
}

