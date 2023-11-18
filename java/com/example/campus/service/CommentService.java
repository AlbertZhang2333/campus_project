package com.example.campus.service;

import com.example.campus.entity.Comment;
import com.example.campus.entity.CommentManagementDepartment;

import java.sql.Date;
import java.util.List;

public interface CommentService {
    public Comment save(Comment comment);

    public List<Comment> findCommentByUserName(String userName);

    public List<Comment> findCommentByDepartment(CommentManagementDepartment department);

    public List<Comment> fineCommentByDate(Date date);

    public List<Comment> findCommentByUserMail(String userMail);
}
