package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.Encryption;
import com.example.ooadgroupproject.dao.AccountRepository;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

//    @Override
//    public List<Account> findAll() {
//        return accountRepository.findAll();
//    }
    @Override
    public Account getUserById(long id){//.findById会返回一个optional对象
        Optional<Account>optionalAccount=accountRepository.findById(id);
        return optionalAccount.orElse(null);
    }
    @Override
    public Account save(Account account){
        return accountRepository.save(account);
    }
    @Override
    public void deleteById(long id){
        accountRepository.deleteById(id);
    }
    @Override
    public Account AccountLogin(String userMail,String username,String password){
        String SHA_256Password=Encryption.getSHA_256Str(password);
        if(SHA_256Password==null){
            return null;
        }
        Optional<Account>account=accountRepository.findAccountByUserMailAndUsernameAndPassword(userMail,username,SHA_256Password);
        return account.orElse(null);
    }
    @Override
    public Account findByUserMailAndPassword(String userMail,String password){
        String SHA_256Password=Encryption.getSHA_256Str(password);
        if(SHA_256Password==null){
            return null;
        }
        Optional<Account>account=accountRepository.findByUserMailAndPassword(userMail,SHA_256Password);
        return account.orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }


}