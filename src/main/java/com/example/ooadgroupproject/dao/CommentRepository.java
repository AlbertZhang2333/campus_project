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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 用于查询正常的评论
    public List<Comment> findAllByBelongDepartmentAndStateAndType(CommentManagementDepartment belongDepartment, int state, CommentType type);

    // 用于查询回复和对某些物品的评价
    public List<Comment> findAllByBelongDepartmentAndStateAndTypeAndReplyId(CommentManagementDepartment belongDepartment, int state, CommentType type, Long replyId);

    // 用于管理员查询
    public List<Comment> findAllByBelongDepartmentAndType(CommentManagementDepartment belongDepartment, CommentType type);

    public List<Comment> findAllByBelongDepartmentAndTypeAndReplyId(CommentManagementDepartment belongDepartment, CommentType type, Long replyId);


//    @Query("select c from Comment c " +
//            "where (?1 is null or c.userMail = ?2) " +
//            "and (?3 is null or c.date = ?4) " +
//            "and (?5 is null or c.belongDepartment = ?6) " +
//            "and (?7 is null or c.type = ?8)")
    @Query("SELECT c1_0 " +
            "FROM Comment c1_0 " +
            "WHERE (c1_0.userMail = COALESCE(:userMail, c1_0.userMail)) " +
            "  AND (c1_0.date = COALESCE(:date, c1_0.date)) " +
            "  AND (c1_0.belongDepartment = COALESCE(:belongDepartment, c1_0.belongDepartment)) " +
            "  AND (c1_0.type = COALESCE(:type, c1_0.type))")
    List<Comment> findByConditions(
            @Param("userMail") String userMail,
            @Param("date") Date date,
            @Param("belongDepartment") CommentManagementDepartment belongDepartment,
            @Param("type") CommentType type
    );
}
