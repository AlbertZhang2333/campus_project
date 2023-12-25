package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/Common")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/commentRecord")
    public Comment addOne(@RequestBody Comment comment) {
        System.out.println(comment.toString());
        return commentService.save(comment);
    }

    @PutMapping("/commentRecord")
    public Comment update(@RequestBody Comment comment) {
        Comment newComment = new Comment();

        System.out.println(comment.toString());

        return commentService.save(comment);
    }

    @PostMapping("/allComments")
    public Result getComments(@RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findAllCommentByState();
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByUserMail")
    public Result getCommentByUserMail(@RequestParam String userMail, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByUserMailAndState(userMail);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByUserName")
    public Result getCommentByUserName(@RequestParam String userName, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByUserNameAndState(userName);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByDate")
    public Result getCommentByDate(@RequestParam Date date, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByDateAndState(date);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByDepartment")
    public Result getCommentByDepartment(@RequestParam CommentManagementDepartment department, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByBelongDepartmentAndState(department);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @PostMapping("/allCommentsAdmin")
    public Result getCommentsAdmin(@RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findAllComment();
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByDepartmentAdmin")
    public Result getCommentByDepartmentAdmin(@RequestParam CommentManagementDepartment department, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByBelongDepartment(department);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByUserMailAdmin")
    public Result getCommentByUserMailAdmin(@RequestParam String userMail, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByUserMail(userMail);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByUserNameAdmin")
    public Result getCommentByUserNameAdmin(@RequestParam String userName, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByUserName(userName);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByDateAdmin")
    public Result getCommentByDateAdmin(@RequestParam Date date, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findByDate(date);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }
}
