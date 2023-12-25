package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.ReplyComment;

import java.util.List;

public interface ReplyCommentService {
    public ReplyComment save(ReplyComment replyComment);

    public List<ReplyComment> findByReplyId(long replyId);

}
