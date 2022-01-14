package com.example.chandoanchanthuong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cases")
public class Case implements Comparable<Case> {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "CreateDate")
    private Date createDate;
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

    @Override
    public int compareTo(Case o) {
        if(this.getId() == o.getId()){
            return 0;
        }
        return 1;
    }
}
