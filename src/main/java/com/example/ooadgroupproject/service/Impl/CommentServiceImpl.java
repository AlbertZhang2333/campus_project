package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.CommentRepository;
import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
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
    public List<Comment> findCommentByUserName(String userName) {
        return commentRepository.findByUserName(userName);
    }

    @Override
    public List<Comment> findCommentByDepartment(CommentManagementDepartment department) {
        return commentRepository.findByBelongDepartment(department);
    }
    @Override
    public List<Comment> fineCommentByDate(Date date) {
        return commentRepository.findByDate(date);
    }

    @Override
    public List<Comment> findCommentByUserMail(String userMail) {
        return commentRepository.findByUserMail(userMail);
    }
}
