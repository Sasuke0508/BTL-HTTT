package com.example.chandoanchanthuong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sympton")
public class Sympton {
    @Id
    @Column(name = "ID")
    public int id;
    @Column(name = "Name")
    public String name;
    @ManyToOne(targetEntity = GroupSympton.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "GroupSymptonID")
    private GroupSympton groupSympton;
}
