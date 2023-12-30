package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import com.example.ooadgroupproject.entity.CommentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.sql.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {
    // 用于查询正常的评论
    public Page<Comment> findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment belongDepartment, int state, CommentType type, Pageable pageable);

    // 用于查询回复和对某些物品的评价
    public Page<Comment> findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment belongDepartment, int state, CommentType type, Long replyId, Pageable pageable);

    // 用于管理员查询
    public Page<Comment> findAllByBelongDepartmentAndType(CommentManagementDepartment belongDepartment, CommentType type, Pageable pageable);

    public Page<Comment> findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment belongDepartment, CommentType type, Long replyId, Pageable pageable);


    @Query("SELECT c1_0 " +
            "FROM Comment c1_0 " +
            "WHERE (c1_0.userMail = COALESCE(:userMail, c1_0.userMail)) " +
            "  AND (c1_0.date = COALESCE(:date, c1_0.date)) " +
            "  AND (c1_0.belongDepartment = COALESCE(:belongDepartment, c1_0.belongDepartment)) " +
            "  AND (c1_0.type = COALESCE(:type, c1_0.type))")
    public Page<Comment> findByConditions(
            @Param("userMail") String userMail,
            @Param("date") Date date,
            @Param("belongDepartment") CommentManagementDepartment belongDepartment,
            @Param("type") CommentType type,
            Pageable pageable
    );

}
