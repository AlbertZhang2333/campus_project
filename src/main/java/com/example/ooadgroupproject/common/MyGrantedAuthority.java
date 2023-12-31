package com.example.ooadgroupproject.common;

import org.springframework.security.core.GrantedAuthority;
import com.example.ooadgroupproject.IdentityLevel;

public final class MyGrantedAuthority implements GrantedAuthority {
    private String role;
    public MyGrantedAuthority(int identity){
        switch (identity){
            case IdentityLevel.NORMAL_USER:
                role=IdentityLevel.roleNormalUser;
                break;
            case IdentityLevel.ACCOUNT_ADMIN:
                role=IdentityLevel.roleAccountAdmin;
                break;
            case IdentityLevel.VISITOR:
            default:
                role=IdentityLevel.roleVisitor;
                break;
        }
    }
    @Override
    public String getAuthority() {
        return this.role;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }else {
            if(obj instanceof MyGrantedAuthority){
                if(this.role.equals(((MyGrantedAuthority)obj).role)){
                    return true;
                }
            }
        }
        return false;
    }
    public String toString(){
        return this.role;
    }
}
