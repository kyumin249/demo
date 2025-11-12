package com.example.demo.Model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.Model.domain.Board;
import com.example.demo.Model.repository.BoardRepository;
 import lombok.RequiredArgsConstructor;
 @Service
 @RequiredArgsConstructor // 생성자자동생성(부분)
public class BlogService {
    private final BoardRepository boardRepository; // 리포지토리선언

    public List<Board> findAll() { // 게시판 전체 목록 조회
        return boardRepository.findAll();
    }

    public Board save(AddBoardRequest request){
        // DTO가 없는 경우 이곳에 직접 구현 가능
        return boardRepository.save(request.toEntity());
    }

    public Board addBoard(String title, String content, String user, String newdate, String count, String likec) {
        Board board = Board.builder()
            .title(title)
            .content(content)
            .user(user)
            .newdate(newdate)
            .count(count)
            .likec(likec)
            .build();
        return boardRepository.save(board);
    }

    // Removed duplicate findById stub (use the implemented findById below)
    public void update(Long id, Board request) {
        Optional<Board> optionalBoard = boardRepository.findById(id); // 단일 글 조회
        optionalBoard.ifPresent(board -> { // 값이 있으면
            board.update(request.getTitle(), request.getContent(), board.getUser(), board.getNewdate(), board.getCount(), board.getLikec()); // 값을 수정
            boardRepository.save(board); // Article 객체에 저장
            
        });
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }


    public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
    return boardRepository.findById(id);
    }
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    } // LIKE 검색 제공(대소문자 무시)
}