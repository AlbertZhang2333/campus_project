package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.ReplyCommentRepository;
import com.example.ooadgroupproject.entity.ReplyComment;
import com.example.ooadgroupproject.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {

    @Autowired
    private ReplyCommentRepository replyCommentRepository;


    @Override
    public ReplyComment save(ReplyComment replyComment) {
        return replyCommentRepository.save(replyComment);
    }

    @Override
    public List<ReplyComment> findByReplyId(long replyId) {
        return replyCommentRepository.findByReplyIdAndState(replyId, 1);
    }
}
