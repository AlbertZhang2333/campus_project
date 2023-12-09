package com.example.ooadgroupproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class BusLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Boolean inService;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;
    @NotNull
    private Integer startStopId;
    @NotNull
    private Integer endStopId;

}
