package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.entity.ReplyComment;
import com.example.ooadgroupproject.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/ReplyComment")
public class ReplyCommentController {
    @Autowired
    private ReplyCommentService replyCommentService;

    @PostMapping("/replyComment")
    public ReplyComment addOne(@RequestBody ReplyComment replyComment) {
        System.out.println(replyComment);
        return replyCommentService.save(replyComment);
    }

    @PutMapping("/replyComment")
    public ReplyComment update(@RequestBody ReplyComment replyComment) {
        System.out.println(replyComment);
        return replyCommentService.save(replyComment);
    }

    @PostMapping("/replyCommentsByReplyId")
    public Result getReplyComments(@RequestParam int pageSize, @RequestParam int currentPage, @RequestParam long replyId) {
        List<ReplyComment> list = replyCommentService.findByReplyId(replyId);
        Long tot = (long) list.size();

        return Result.success(tot, SplitPage.getPage(list, pageSize, currentPage));
    }
}
