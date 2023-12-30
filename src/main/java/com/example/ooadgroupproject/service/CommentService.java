package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.entity.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CommentService {
    public Comment save(Comment comment);

    // 用于查询正常的评论
    public Page<Comment> findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment belongDepartment, int state, CommentType type, Pageable pageable);

    // 用于查询回复和对某些物品的评价
    public Page<Comment> findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment belongDepartment, int state, CommentType type, Long replyId, Pageable pageable);

    // 用于管理员查询
    public Page<Comment> findAllByBelongDepartmentAndType(CommentManagementDepartment belongDepartment, CommentType type, Pageable pageable);

    public Page<Comment> findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment belongDepartment, CommentType type, Long replyId, Pageable pageable);

    public Page<Comment> findAll(Pageable pageable);

    public Page<Comment> findByConditions(String userMail, Date date, CommentManagementDepartment belongDepartment, CommentType type, Pageable pageable);
}
