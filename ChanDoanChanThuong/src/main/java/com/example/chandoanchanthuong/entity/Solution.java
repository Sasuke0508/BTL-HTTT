package com.example.chandoanchanthuong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "solution")
public class Solution {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Content")
    private String content;
    @Column(name = "Reference")
    private String reference;
    @ManyToOne
    @JoinColumn(name = "DiseaseID")
    private Disease disease;
}
