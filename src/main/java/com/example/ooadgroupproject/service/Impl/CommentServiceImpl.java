package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.CommentRepository;
import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.entity.CommentType;
import com.example.ooadgroupproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment belongDepartment, int state, CommentType type, Pageable pageable) {
        return commentRepository.findAllByBelongDepartmentAndStateAndType(belongDepartment, state, type, pageable);
    }

    @Override
    public Page<Comment> findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment belongDepartment, int state, CommentType type, Long replyId, Pageable pageable) {
        return commentRepository.findAllByBelongDepartmentAndStateAndTypeAndReplyId(belongDepartment, state, type, replyId, pageable);
    }

    @Override
    public Page<Comment> findAllByBelongDepartmentAndType(CommentManagementDepartment belongDepartment, CommentType type, Pageable pageable) {
        return commentRepository.findAllByBelongDepartmentAndType(belongDepartment, type, pageable);
    }

    @Override
    public Page<Comment> findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment belongDepartment, CommentType type, Long replyId, Pageable pageable) {
        return commentRepository.findAllByBelongDepartmentAndTypeAndReplyId(belongDepartment, type, replyId, pageable);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> findByConditions(String userMail, Date date, CommentManagementDepartment belongDepartment, CommentType type, Pageable pageable) {
        return commentRepository.findByConditions(userMail, date, belongDepartment, type, pageable);
    }
}
