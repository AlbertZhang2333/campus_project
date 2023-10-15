package com.example.campus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class BusRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotNull
    Integer startStop; //起始站的id
    @NotNull
    Integer endStop;
    @NotNull
    Integer time; //分钟数

}
