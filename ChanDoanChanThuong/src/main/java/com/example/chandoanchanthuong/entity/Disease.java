package com.example.chandoanchanthuong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "disease")
public class Disease {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Type")
    private String type;
    @OneToMany
    private List<DiseaseLevel> diseaseLevels;
    @OneToMany(targetEntity = Solution.class, mappedBy = "disease", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Solution> solutionList;
}
