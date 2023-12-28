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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

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
        if(account==null){
            LoginFailureHandler loginFailureHandler1=new LoginFailureHandler();
            loginFailureHandler1.onAuthenticationFailure(request,response,new LoginFailedException("请检查账号密码或是否尚未注册账号"));
//            throw new LoginFailedException("请检查账号密码或是否尚未注册账号");
            return null;
        }
        Account authenticationAccount=new Account();
      //  authenticationAccount.setId(account.getId());
        authenticationAccount.setUsername(account.getUsername());
        authenticationAccount.setUserMail(account.getUserMail());
        authenticationAccount.setIdentity(account.getIdentity());
        if(account!=null&&account.isEnabled()) {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(authenticationAccount,null, account.getAuthorities());
            //令牌通过了验证
            Authentication authentication=authenticationManager.authenticate(token);
            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            this.securityContextHolderStrategy.setContext(context);
            loginSuccessHandler.onAuthenticationSuccess(request,response,authentication);
            return authentication;
        }else if(account!=null&&!account.isEnabled()){
            AuthenticationException authenticationException=new LoginFailedException("登陆失败！该账号已被禁用。" +
                    "请和管理员联系了解详情");
            loginFailureHandler.onAuthenticationFailure(request,response,authenticationException);
            return null;
        }else{
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
