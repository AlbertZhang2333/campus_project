package com.example.ooadgroupproject.entity;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import com.example.ooadgroupproject.Encryption;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String userMail;
    @NotNull
    private boolean inBlackList;
    private int identity;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    //对设定密码进行密码加密
    public void setPassword(String password) {
        this.password = Encryption.getSHA_256Str(password);
    }

    public boolean isInBlackList() {
        return inBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        this.inBlackList = inBlackList;
    }

    public int getIdentity() {
        return identity;
    }

    //注意！在调用此方法时，为避免因手误或者后续方案调整导致的bug，请不要直接输入int数值，而是通过调用IdentityLevel类输入数值
    public void setIdentity(int identity) {
        this.identity = identity;
    }
    public String getUserMail(){
        return this.userMail;
    }
    public void setUserMail(String userMail){
        //以后会考虑废弃或在修改此方法的基础上，加入邮箱验证功能
        this.userMail=userMail;
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
}
