package com.example.ooadgroupproject.Filter;

import cn.hutool.core.util.StrUtil;
import com.example.ooadgroupproject.Utils.JwtUtils;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.handler.LoginFailureHandler;
import com.example.ooadgroupproject.handler.LoginSuccessHandler;
import com.example.ooadgroupproject.service.AccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private  AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String jwt=request.getHeader("ooad_project");
        if(StrUtil.isBlankOrUndefined(jwt)){
            chain.doFilter(request,response);
            return;
        }
        Jws<Claims> jws= JwtUtils.parseClaim(jwt);
        if(jws==null){
            throw new JwtException("token解析器返回为null");
        }
        if(jwtUtils.ExpiredCheck(jws.getPayload())){
            throw new JwtException("token已过期");
        }
        String userMail = (String) jws.getPayload().get("userMail");
        Account account = accountService.findAccountByUserMail(userMail);
        if(account!=null) {
            String username=account.getUsername();
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userMail, username, account.getAuthorities());
            //令牌通过了验证
            Authentication authentication=authenticationManager.authenticate(token);
            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            this.securityContextHolderStrategy.setContext(context);
            chain.doFilter(request,response);
        }else {
            chain.doFilter(request,response);
            return;
        }
    }
}
