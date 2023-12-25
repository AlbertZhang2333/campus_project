package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;

import java.sql.Date;
import java.util.List;

public interface CommentService {
    public Comment save(Comment comment);

    public List<Comment> findByUserNameAndState(String userName);

    public List<Comment> findByBelongDepartmentAndState(CommentManagementDepartment department);

    public List<Comment> findByDateAndState(Date date);

    public List<Comment> findByUserMailAndState(String userMail);


    public List<Comment> findAllCommentByState();

    public List<Comment> findByUserName(String userName);

    public List<Comment> findByBelongDepartment(CommentManagementDepartment department);

    public List<Comment> findByDate(Date date);

    public List<Comment> findByUserMail(String userMail);

    public List<Comment> findAllComment();
}
