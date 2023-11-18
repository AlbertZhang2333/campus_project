package com.example.campus.service;

import com.example.campus.dao.CommentRepository;
import com.example.campus.entity.Comment;
import com.example.campus.entity.CommentManagementDepartment;
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
