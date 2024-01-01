package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Account;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public List<Account> findAll(Sort sort);
    public List<Account> findAll();
    public Optional<Account> findAccountById(long id);

    public Optional<Account> findAccountByUserMailAndPassword(String userMail,  String password);

    public Optional<Account> findByUserMailAndPassword(String userMail, String password);

    public Optional<Account> findAccountByUserMail(String userMail);
    public void deleteAccountByUserMail(String userMail);

}
