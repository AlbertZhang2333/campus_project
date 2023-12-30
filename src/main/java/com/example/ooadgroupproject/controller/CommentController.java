package com.example.ooadgroupproject.controller;

import org.springframework.data.domain.Page;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.entity.CommentType;
import com.example.ooadgroupproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Result addOne(@RequestBody Comment comment) {
        System.out.println(comment.toString());
        return Result.success(1L, commentService.save(comment));
    }

    @PutMapping("/updateComment")
    public Result update(@RequestBody Comment comment) {
        Comment newComment = new Comment();

        System.out.println(comment.toString());

        return Result.success(1L, commentService.save(comment));
    }

    // 用于用户获取不是回复和评价的评论
    @PostMapping("/allCommentsUser")
    public Result getCommentsUser(@RequestParam Integer belongDepartment,
                                  @RequestParam Integer type,
                                  @RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Comment> list = commentService.findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment.getByCode(belongDepartment), 1, CommentType.getByCode(type), pageable);
        Long tot = list.getTotalElements();

        return Result.success(tot, list.getContent());
    }

    @PostMapping("/allCommentsReplyUser")
    public Result getCommentReplyUser(@RequestParam Integer belongDepartment,
                                      @RequestParam Integer type,
                                      @RequestParam Long replyId,
                                      @RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Comment> list = commentService.findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment.getByCode(belongDepartment), 1, CommentType.getByCode(type), replyId, pageable);
        Long tot = list.getTotalElements();

        return Result.success(tot, list.getContent());
    }

    @PostMapping("/allCommentsAdmin")
    public Result getCommentsAdmin(@RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Comment> list = commentService.findAll(pageable);
        Long tot = list.getTotalElements();

        return Result.success(tot, list.getContent());
    }

    @PostMapping("/allReplyCommentsAdmin")
    public Result getReplyCommentsAdmin(@RequestParam Integer type,
                                        @RequestParam Long replyId,
                                        @RequestParam Integer belongDepartment,
                                        @RequestParam int pageSize, @RequestParam int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Comment> list = commentService.findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment.getByCode(belongDepartment), CommentType.getByCode(type), replyId, pageable);
        Long tot = list.getTotalElements();

        return Result.success(tot, list.getContent());
    }

    @GetMapping("/commentSearchAdmin")
    public Result getSearchAdmin(@RequestParam(required = false) String userMail,
                                 @RequestParam(required = false) Date date,
                                 @RequestParam(required = false) Integer belongDepartment,
                                 @RequestParam(required = false) Integer type,
                                 @RequestParam(required = false) int pageSize,
                                 @RequestParam(required = false) int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Comment> list = commentService.findByConditions(userMail, date, CommentManagementDepartment.getByCode(belongDepartment), CommentType.getByCode(type), pageable);
        Long tot = list.getTotalElements();

        return Result.success(tot, list.getContent());
    }
}
