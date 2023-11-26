package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findById(long id);

    public Optional<Account> findAccountByUserMailAndUsernameAndPassword(String userMail, String username, String password);

    public Optional<Account> findByUserMailAndPassword(String userMail, String password);


}
