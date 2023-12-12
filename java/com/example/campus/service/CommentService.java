package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;

import java.sql.Date;
import java.util.List;

public interface CommentService {
    public Comment save(Comment comment);

    public List<Comment> findCommentByUserName(String userName);

    public List<Comment> findCommentByDepartment(CommentManagementDepartment department);

    public List<Comment> findCommentByDate(Date date);

    public List<Comment> findCommentByUserMail(String userMail);

    public List<Comment> findAllComment();

    public void deleteById(long id);
}
