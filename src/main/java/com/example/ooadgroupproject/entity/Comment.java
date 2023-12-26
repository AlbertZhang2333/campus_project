package com.example.ooadgroupproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;
import java.sql.Time;

@Getter
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
    private String comment;

    @NotNull
    private int state;

    @NotNull
    private Time time;

    @NotNull
    private Date date;

    @NotNull
    private CommentManagementDepartment belongDepartment;

    private Long replyId;

    @NotNull
    private CommentType type;

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBelongDepartment(CommentManagementDepartment belongDepartment) {
        this.belongDepartment = belongDepartment;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", comment='" + comment + '\'' +
                ", state=" + state +
                ", time=" + time +
                ", date=" + date +
                ", belongDepartment=" + belongDepartment +
                ", replyId=" + replyId +
                ", type=" + type +
                '}';
    }
}
