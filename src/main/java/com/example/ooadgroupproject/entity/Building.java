package com.example.ooadgroupproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "纬度不能为空")
    private Double lat;
    @NotNull(message = "经度不能为空")
    private Double lng;
    @NotNull(message = "建筑名称不能为空")
    private String name;
    @NotNull(message = "建筑描述不能为空")
    private String description;
    private String photoPath;
    private String busStop;
    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
