package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
    public List<ReplyComment> findByReplyIdAndState(long replyId, int state);
}
