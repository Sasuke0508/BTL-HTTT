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
@Table(name = "diseaselevel")
public class DiseaseLevel {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Name")
    private String name;
    @ManyToMany
    @JoinTable(name = "diseaselevel_solution",
            joinColumns = @JoinColumn(name = "DiseaseLevelID"),
            inverseJoinColumns = @JoinColumn(name = "SolutionID") )
    private List<Solution> solutionList;
    @ManyToOne(targetEntity = Disease.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "DiseaseID")
    private Disease disease;

}
