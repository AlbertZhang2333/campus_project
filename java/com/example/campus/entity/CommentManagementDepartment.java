package com.example.campus.entity;

public enum CommentManagementDepartment {
    Store(0);


    private final int department;

    CommentManagementDepartment(int department) {
        this.department = department;
    }

    public int getDepartment() {
        return department;
    }

    public static CommentManagementDepartment getByCode(int departmentCode) {
        for (CommentManagementDepartment c : CommentManagementDepartment.values()) {
            if (c.department == departmentCode) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid DepartmentState code: " + departmentCode);
    }

}
