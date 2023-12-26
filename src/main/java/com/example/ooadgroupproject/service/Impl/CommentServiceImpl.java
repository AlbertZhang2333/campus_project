package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.CommentRepository;
import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.entity.CommentType;
import com.example.ooadgroupproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Comment> findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment belongDepartment, int state, CommentType type) {
        return commentRepository.findAllByBelongDepartmentAndStateAndType(belongDepartment, state, type);
    }

    @Override
    public List<Comment> findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment belongDepartment, int state, CommentType type, Long replyId) {
        return commentRepository.findAllByBelongDepartmentAndStateAndTypeAndReplyId(belongDepartment, state, type, replyId);
    }

    @Override
    public List<Comment> findAllByBelongDepartmentAndType(CommentManagementDepartment belongDepartment, CommentType type) {
        return commentRepository.findAllByBelongDepartmentAndType(belongDepartment, type);
    }

    @Override
    public List<Comment> findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment belongDepartment, CommentType type, Long replyId) {
        return commentRepository.findAllByBelongDepartmentAndTypeAndReplyId(belongDepartment, type, replyId);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findByConditions(String userMail, Date date, CommentManagementDepartment belongDepartment, CommentType type) {
        if (date != null) {
            System.out.println(date);
            System.out.println(date.getClass());
        }
        System.out.println(belongDepartment);
        System.out.println(type);

        return commentRepository.findByConditions(userMail, date, belongDepartment, type);
    }
}
