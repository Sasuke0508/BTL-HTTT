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
@Table(name = "groupsympton")
public class GroupSympton {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Name")
    private String name;
    @OneToMany(targetEntity = Sympton.class, mappedBy = "groupSympton", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Sympton> symptonList;
}
