package com.example.ooadgroupproject.entity;

import lombok.Getter;

@Getter
public enum CommentType {

    Comment(0),
    Reply(1);

    private final int type;

    CommentType(int type) {
        this.type = type;
    }

    public static CommentType getByCode(Integer typeCode) {
        if (typeCode == null) return null;

        for (CommentType c : CommentType.values()) {
            if (c.type == typeCode) {
                return c;
            }
        }

        throw new IllegalArgumentException("Invalid CommentType code: " + typeCode);
    }
}
