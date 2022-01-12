package com.example.chandoanchanthuong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "case")
public class Case {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "CreateDate")
    private Date createDate;
    @Column(name = "Type")
    private String type;
    @ManyToMany
    @JoinTable(name = "case_diseasedefingsympton",
            joinColumns = @JoinColumn(name = "CaseID"),
            inverseJoinColumns = @JoinColumn(name = "DiseaseDefingSymptonID"))
    private List<DiseaseDefingSympton> diseaseDefingSymptonList;
    @ManyToMany
    @JoinTable(name = "case_diseaseleveldefingsympton",
            joinColumns = @JoinColumn(name = "CaseID"),
            inverseJoinColumns = @JoinColumn(name = "DiseaseLevelDefingSymptonID"))
    private List<DiseaseLevelDefingSympton> diseaseLevelDefingSymptonList;
    @ManyToMany
    @JoinTable(name = "case_reason",
            joinColumns = @JoinColumn(name = "CaseID"),
            inverseJoinColumns = @JoinColumn(name = "ReasonID"))
    private List<Reason> reasonList;
    @ManyToMany
    @JoinTable(name = "case_disease",
            joinColumns = @JoinColumn(name = "CaseID"),
            inverseJoinColumns = @JoinColumn(name = "DiseaseID"))
    private List<Disease> diseaseList;
    @ManyToMany
    @JoinTable(name = "case_medicalhistory",
            joinColumns = @JoinColumn(name = "CaseID"),
            inverseJoinColumns = @JoinColumn(name = "MedicalHistoryID"))
    private List<MedicalHistory> medicalHistoryList;
}
