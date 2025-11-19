package com.example.demo.Model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.domain.Member;



@Repository
 public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Member findByEmail(String email);
 }
