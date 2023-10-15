package com.example.campus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull
    private String name;
    @NotNull
    private Integer line;

}
