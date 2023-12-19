package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.Encryption;
import com.example.ooadgroupproject.dao.AccountRepository;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.ooadgroupproject.Utils.JwtUtils;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
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
    public Account save(Account account) {
        try {
            return accountRepository.save(account);
        }catch (Exception e){
            return null;
        }
    }
    @Override
    public void deleteById(long id){
        accountRepository.deleteById(id);
    }
    @Override
    public Account AccountExistCheck(String userMail, String password){
        String SHA_256Password=Encryption.getSHA_256Str(password);
        if(SHA_256Password==null){
            return null;
        }
        Optional<Account>account=accountRepository.findAccountByUserMailAndPassword(userMail,SHA_256Password);
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

    @Override
    public Account findAccountByUserMail(String userMail) {
       Optional<Account>optional=accountRepository.findAccountByUserMail(userMail);
       return optional.orElse(null);
    }


    //由于在项目实际内容中，我们的username不具备唯一性，因此，该方法实际要求填入的为userMail
    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException,NumberFormatException {
        return accountRepository.findAccountByUserMail(userMail).orElse(null);
    }
}
