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
    public List<Comment> findByUserNameAndState(String userName) {
        return commentRepository.findByUserNameAndState(userName, 1);
    }

    @Override
    public List<Comment> findByBelongDepartmentAndState(CommentManagementDepartment department) {
        return commentRepository.findByBelongDepartmentAndState(department, 1);
    }
    @Override
    public List<Comment> findByDateAndState(Date date) {
        return commentRepository.findByDateAndState(date, 1);
    }

    @Override
    public List<Comment> findByUserMailAndState(String userMail) {
        return commentRepository.findByUserMailAndState(userMail, 1);
    }

    @Override
    public List<Comment> findAllCommentByState() {
        return commentRepository.findAllByState(1);
    }

    @Override
    public List<Comment> findByUserName(String userName) {
        return commentRepository.findByUserName(userName);
    }

    @Override
    public List<Comment> findByBelongDepartment(CommentManagementDepartment department) {
        return commentRepository.findByBelongDepartment(department);
    }

    @Override
    public List<Comment> findByDate(Date date) {
        return commentRepository.findByDate(date);
    }

    @Override
    public List<Comment> findByUserMail(String userMail) {
        return commentRepository.findByUserMail(userMail);
    }

    @Override
    public List<Comment> findAllComment() {
        return commentRepository.findAll();
    }

}
