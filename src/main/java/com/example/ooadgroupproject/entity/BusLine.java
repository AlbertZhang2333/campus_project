package com.example.ooadgroupproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class BusLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "线路名不能为空")
    private Integer lineId;
    @NotNull(message = "方向不能为空")
    private String direction;
    @NotNull(message = "运营状态不能为空")
    private Boolean inService;
    @NotNull(message = "起始时间不能为空")
    private String startTime;
    @NotNull(message = "结束时间不能为空")
    private String endTime;
    @NotNull(message = "起始站不能为空")
    private Integer startStopId;
    @NotNull(message = "终点站不能为空")
    private Integer endStopId;
}
