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
@Table(name = "reason")
public class Reason {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Weight")
    private double weight;
    @ManyToMany
    @JoinTable(name = "reason_disease",
            joinColumns = @JoinColumn(name = "ReasonID"),
            inverseJoinColumns = @JoinColumn(name = "DiseaseID"))
    private List<Disease> diseaseList;
}
