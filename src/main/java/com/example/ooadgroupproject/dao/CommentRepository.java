package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Comment;
import com.example.ooadgroupproject.entity.CommentManagementDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByUserMail(String userMail);

    public List<Comment> findByUserName(String userName);

    public List<Comment> findByDate(Date date);

    public List<Comment> findByBelongDepartment(CommentManagementDepartment department);
}
