package com.example.ooadgroupproject.Filter;

import com.example.ooadgroupproject.Utils.JwtUtils;
import jdk.jfr.BooleanFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

}
