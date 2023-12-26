package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.entity.CommentType;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CommentService {
    public Comment save(Comment comment);

    // 用于查询正常的评论
    public List<Comment> findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment belongDepartment, int state, CommentType type);

    // 用于查询回复和对某些物品的评价
    public List<Comment> findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment belongDepartment, int state, CommentType type, Long replyId);

    // 用于管理员查询
    public List<Comment> findAllByBelongDepartmentAndType(CommentManagementDepartment belongDepartment, CommentType type);

    public List<Comment> findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment belongDepartment, CommentType type, Long replyId);

    public List<Comment> findAll();

    public List<Comment> findByConditions(String userMail, Date date, CommentManagementDepartment belongDepartment, CommentType type);
}
