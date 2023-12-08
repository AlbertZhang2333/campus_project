package com.example.ooadgroupproject.Filter;


import com.example.ooadgroupproject.Exception.LoginFailedException;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.handler.LoginFailureHandler;
import com.example.ooadgroupproject.handler.LoginSuccessHandler;
import com.example.ooadgroupproject.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    private final String passwordParameter="password";
    private final String userMailParameter="userMail";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/login/loginCheck","POST");

    public MyUsernamePasswordAuthenticationFilter(){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);

    }
    public MyUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER,authenticationManager);

    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, ServletException, IOException {
        String password=obtainPassword(request);
        String userMail=obtainUserMail(request);
        Account account=accountService.findByUserMailAndPassword(userMail,password);
        if(account!=null) {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userMail, null, account.getAuthorities());
            Authentication authentication=authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            loginSuccessHandler.onAuthenticationSuccess(request,response,authentication);
            return authentication;
        }else {
            AuthenticationException authenticationException=new LoginFailedException("登陆失败！密码或邮箱错误");
            loginFailureHandler.onAuthenticationFailure(request,response,authenticationException);
            return null;
        }
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }


    @Nullable
    protected String obtainUserMail(HttpServletRequest request) {
        return request.getParameter(this.userMailParameter);
    }


}
