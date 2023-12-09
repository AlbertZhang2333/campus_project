package com.example.ooadgroupproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class BusRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private Integer startStop; //起始站的id
    @NotNull
    private Integer endStop;
    @NotNull
    private Integer time; //分钟数
    @NotNull
    private Integer lineId;

}
