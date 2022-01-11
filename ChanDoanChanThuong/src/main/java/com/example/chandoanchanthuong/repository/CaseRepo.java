package com.example.chandoanchanthuong.repository;

import com.example.chandoanchanthuong.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepo extends JpaRepository<Case, Integer> {
}
