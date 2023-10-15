package com.example.campus.service;

import com.example.campus.entity.Account;

public interface AccountService {
 //返回所有用户的所有信息,10.11更新，我觉得该方法不必要存在
// public List<Account> findAll();
 //如名
public Account getUserById(long id);
//保存一个用户信息
 public Account save(Account account);
 //依据id删除一名用户的所有信息
 public void deleteById(long id);
 //利用三个信息获得一名用户的账户信息，如果返回Account内容不为空，说明存在这一用户的信息，如果返回null，说明不存在这一用户，登录失败
 public Account AccountLogin(String userMail,String username,String password);

 //首先判定管理人员的权限级别，然后依照提供的id找出目标修改的用户，然后决定将目标用户移入或移除黑名单
 public Account findByUserMailAndPassword(String userMail, String password);
}


