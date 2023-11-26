package com.example.ooadgroupproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String userName;

    @NotNull
    private String userMail;

    @NotNull
    private String Comment;

    @NotNull
    private boolean vis;

    @NotNull
    private Time time;

    @NotNull
    private Date date;

    @NotNull
    private CommentManagementDepartment belongDepartment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public boolean isVis() {
        return vis;
    }

    public void setVis(boolean vis) {
        this.vis = vis;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CommentManagementDepartment getBelongDepartment() {
        return belongDepartment;
    }

    public void setBelongDepartment(CommentManagementDepartment belongDepartment) {
        this.belongDepartment = belongDepartment;
    }
}
