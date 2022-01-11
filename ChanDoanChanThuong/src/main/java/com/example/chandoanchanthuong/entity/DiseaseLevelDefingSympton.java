package com.example.chandoanchanthuong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diseaseleveldefingsympton")
public class DiseaseLevelDefingSympton extends Sympton{
    @Column(name = "IsQuantitative")
    private boolean isQuantitative;
    @Column(name = "FromAmount")
    private int fromAmount;
    @Column(name = "ToAmount")
    private int toAmount;
    @ManyToOne(targetEntity = DiseaseLevel.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "DiseaseLevelID")
    private DiseaseLevel diseaseLevel;
}
