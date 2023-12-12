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
@RequestMapping("/Comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/commentRecord")
    public Comment addOne(@RequestBody Comment comment) {
        System.out.println(comment.toString());
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
    @PostMapping("/allComments")
    public Result getComments(@RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findAllComment();
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByUserMail")
    public Result getCommentByUserMail(@RequestParam String userMail, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findCommentByUserMail(userMail);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByUserName")
    public Result getCommentByUserName(@RequestParam String userName, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findCommentByUserName(userName);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByDate")
    public Result getCommentByUserMail(@RequestParam Date date, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findCommentByDate(date);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentByDepartment")
    public Result getCommentByUserMail(@RequestParam CommentManagementDepartment department, @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findCommentByDepartment(department);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @DeleteMapping("commentDelete/{id}")
    public void deleteOne(@PathVariable long id) {
        System.out.println(id);
        commentService.deleteById(id);
    }
}
