package com.example.chandoanchanthuong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicalhistory")
public class MedicalHistory {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Weight")
    private double weight;
    @ManyToMany
    @JoinTable(name = "medicalhistory_disease",
            joinColumns = @JoinColumn(name = "MedicalHistoryID"),
            inverseJoinColumns = @JoinColumn(name = "DiseaseID"))
    private List<Disease> diseaseList;
}
