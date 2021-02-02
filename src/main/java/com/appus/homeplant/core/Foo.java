package com.appus.homeplant.core;

import lombok.Setter;

import javax.persistence.*;

@Setter
@Entity
public class Foo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
}
