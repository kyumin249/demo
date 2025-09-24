package com.example.demo.Model.repository;
import org.springframework.data.jpa.repository.JpaRepository; // JPA 필수 등록
import org.springframework.stereotype.Repository; // 빈 등록

import com.example.demo.Model.domain.TestDB;
@Repository
public interface TestRepository extends JpaRepository<TestDB, Long> {
    // TestDB라는 도메인과 Long 타입의 PK를 관리하는 JPA 레포지토리
    TestDB findByName(String name);
}
