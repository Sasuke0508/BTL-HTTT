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
@Table(name = "diseasedefingsympton")
public class DiseaseDefingSympton extends Sympton{
    @Column(name = "Weight")
    private double weight;
    @ManyToMany
    @JoinTable(name = "diseasedefingsympton_disease",
            joinColumns = @JoinColumn(name = "DiseaseDefingSymptonID"),
            inverseJoinColumns = @JoinColumn(name = "DiseaseID"))
    private List<Disease> diseaseList;
}
