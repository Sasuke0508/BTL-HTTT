package com.example.chandoanchanthuong.repository;

import com.example.chandoanchanthuong.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepo extends JpaRepository<MedicalHistory, Integer> {
}
