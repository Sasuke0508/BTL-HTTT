package com.example.chandoanchanthuong.repository;

import com.example.chandoanchanthuong.entity.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReasonRepo extends JpaRepository<Reason, Integer> {
}
