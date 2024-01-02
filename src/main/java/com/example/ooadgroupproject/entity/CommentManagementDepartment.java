package com.example.ooadgroupproject.entity;

import lombok.Getter;

@Getter
public enum CommentManagementDepartment {
    Store(0),
    Building(1);


    private final int department;

    CommentManagementDepartment(int department) {
        this.department = department;
    }

    public static CommentManagementDepartment getByCode(Integer departmentCode) {
        if (departmentCode == null) return null;

        for (CommentManagementDepartment c : CommentManagementDepartment.values()) {
            if (c.department == departmentCode) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid DepartmentState code: " + departmentCode);
    }

}
