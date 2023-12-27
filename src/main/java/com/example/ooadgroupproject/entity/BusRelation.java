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
    private String startStop; //起始站的id
    @NotNull
    private String endStop;
    @NotNull
    private Integer time; //分钟数
    @NotNull
    private Integer lineId;
    private String direction; //方向
    private Integer stopNum; //站点数
}
