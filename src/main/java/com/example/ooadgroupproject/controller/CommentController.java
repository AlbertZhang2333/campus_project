package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.entity.CommentType;
import com.example.ooadgroupproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/Comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public Comment addOne(@RequestBody Comment comment) {
        System.out.println(comment.toString());
        return commentService.save(comment);
    }

    @PutMapping("/updateComment")
    public Comment update(@RequestBody Comment comment) {
        Comment newComment = new Comment();

        System.out.println(comment.toString());

        return commentService.save(comment);
    }

    // 用于用户获取不是回复和评价的评论
    @PostMapping("/allCommentsUser")
    public Result getCommentsUser(@RequestParam Integer belongDepartment,
                              @RequestParam Integer type,
                              @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment.getByCode(belongDepartment), 1, CommentType.getByCode(type));
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @PostMapping("/allCommentsReplyUser")
    public Result getCommentReplyUser(@RequestParam Integer belongDepartment,
                                  @RequestParam Integer type,
                                  @RequestParam Long replyId,
                                  @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment.getByCode(belongDepartment), 1, CommentType.getByCode(type), replyId);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @PostMapping("/allCommentsAdmin")
    public Result getCommentsAdmin(@RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findAll();
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @PostMapping("/allReplyCommentsAdmin")
    public Result getReplyCommentsAdmin(@RequestParam Integer type,
                                        @RequestParam Long replyId,
                                        @RequestParam Integer belongDepartment,
                                        @RequestParam int pageSize, @RequestParam int currentPage) {
        List<Comment> list = commentService.findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment.getByCode(belongDepartment), CommentType.getByCode(type), replyId);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }

    @GetMapping("/commentSearchAdmin")
    public Result getSearchAdmin(@RequestParam(required = false) String userMail,
                                 @RequestParam(required = false) Date date,
                                 @RequestParam(required = false) Integer belongDepartment,
                                 @RequestParam(required = false) Integer type,
                                 @RequestParam(required = false) int pageSize,
                                 @RequestParam(required = false) int currentPage) {

        List<Comment> list = commentService.findByConditions(userMail, date, CommentManagementDepartment.getByCode(belongDepartment), CommentType.getByCode(type));
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }



}
