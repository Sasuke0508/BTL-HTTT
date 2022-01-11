package com.example.chandoanchanthuong.repository;

import com.example.chandoanchanthuong.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepo extends JpaRepository<Disease, Integer> {
}
