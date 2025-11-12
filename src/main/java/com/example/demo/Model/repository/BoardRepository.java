package com.example.demo.Model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.Model.domain.Board;

@Repository
 public interface BoardRepository extends JpaRepository<Board, Long>{
    @SuppressWarnings("null")
    List<Board> findAll();

    Board save(Board entity);
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
 }
