package com.example.demo.Model.repository;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
 import com.example.demo.Model.domain.Article;
 @Repository
 public interface BlogRepository extends JpaRepository<Article, Long>{
 }
 
