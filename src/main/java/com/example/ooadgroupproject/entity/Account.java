package com.example.ooadgroupproject.entity;
import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.service.Impl.AccountServiceImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import com.example.ooadgroupproject.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Entity
public class Account implements UserDetails {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    @NotNull
    private String username;
    @NotNull
    private String password;
    //以后会考虑废弃或在修改此方法的基础上，加入邮箱验证功能
    @Setter
    @Column(unique = true)
    @NotNull
    private String userMail;
    @Setter
    @NotNull
    private boolean inBlackList;
    //注意！在调用此方法时，为避免因手误或者后续方案调整导致的bug，请不要直接输入int数值，而是通过调用IdentityLevel类输入数值
    @Setter
    private int identity;
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.identity== IdentityLevel.NORMAL_USER){
            return new ArrayList<>();
        }else if(this.identity==IdentityLevel.ACCOUNT_ADMIN){
            return new ArrayList<>();
        }else {
            return null;
        }

    }
    public static Collection<? extends GrantedAuthority> getAuthorities(int identity){
        if(identity== IdentityLevel.NORMAL_USER){
            return new ArrayList<>();
        }else if(identity==IdentityLevel.ACCOUNT_ADMIN){
            return new ArrayList<>();
        }else {
            return null;
        }

    }

    //对设定密码进行密码加密
    public void setPassword(String password) {
        this.password = Encryption.getSHA_256Str(password);
    }

    @Override
    public String toString() {
        return "{id:" + this.id + ", "
                + "username:" + this.username + ", "
                + "userMail:" + this.userMail + ", "
                + "password:" + this.password + ", "
                + "inBlackList:" + this.inBlackList + ", "
                + "identity:" + this.identity + "}";
    }

    public Account(String username,String userMail,String password,int level){
        this.setIdentity(level);
        this.setPassword(password);
        this.setUserMail(userMail);
        this.setUsername(username);
        this.setInBlackList(false);

    }
    public Account() {

    }

}
