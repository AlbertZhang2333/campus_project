package com.example.campus.controller;

import com.example.campus.entity.Comment;
import com.example.campus.entity.CommentManagementDepartment;
import com.example.campus.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/exer")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/commentRecord")
    public Comment addOne(Comment comment) {
        return commentService.save(comment);
    }

    @PutMapping("/commentRecord")
    public Comment update(@RequestParam long id,
                          @RequestParam String userName,
                          @RequestParam String userMail,
                          @RequestParam String commentInfo,
                          @RequestParam boolean vis,
                          @RequestParam Time time,
                          @RequestParam Date date,
                          @RequestParam CommentManagementDepartment department) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setUserName(userName);
        comment.setUserMail(userMail);
        comment.setComment(commentInfo);
        comment.setVis(vis);
        comment.setTime(time);
        comment.setDate(date);
        comment.setBelongDepartment(department);

        return commentService.save(comment);
    }

    @GetMapping("/commentByUserMail")
    public List<Comment> getCommentByUserMail(@RequestParam String userMail) {
        return commentService.findCommentByUserMail(userMail);
    }

    @GetMapping("/commentByUserName")
    public List<Comment> getCommentByUserName(@RequestParam String userName) {
        return commentService.findCommentByUserName(userName);
    }

    @GetMapping("/commentByDate")
    public List<Comment> getCommentByUserMail(@RequestParam Date date) {
        return commentService.fineCommentByDate(date);
    }

    @GetMapping("/commentByDepartment")
    public List<Comment> getCommentByUserMail(@RequestParam CommentManagementDepartment department) {
        return commentService.findCommentByDepartment(department);
    }
}
