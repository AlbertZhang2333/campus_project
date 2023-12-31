package com.example.ooadgroupproject.Filter;

import cn.hutool.core.util.StrUtil;
import com.example.ooadgroupproject.Utils.JwtUtils;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.handler.LoginFailureHandler;
import com.example.ooadgroupproject.handler.LoginSuccessHandler;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.CacheClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
    @Autowired
    private CacheClient cacheClient;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
//        Cookie[]cookies =request.getCookies();
        String jwt=request.getHeader(JwtUtils.getHeader());
//        if(cookies!=null) {
//            for (int i = 0; i < cookies.length; i++) {
//                if (cookies[i].getName().equals("passToken") && cookies[i].getValue() != null &&
//                        !cookies[i].getValue().equals("null")) {
//                    jwt = cookies[i].getValue();
//                    break;
//                }
//            }
//        }

        if(jwt==null){
            jwt=request.getParameter(JwtUtils.getHeader());
        }
        if(StrUtil.isBlankOrUndefined(jwt)){
            chain.doFilter(request,response);
            return;
        }
        Jws<Claims> jws= JwtUtils.parseClaim(jwt);
        if(jws==null){
            chain.doFilter(request,response);
            return;
        }
        if(jwtUtils.ExpiredCheck(jws.getPayload())){
            chain.doFilter(request,response);
            return;
        }


        String userMail = (String) jws.getPayload().get("userMail");
        String username = (String) jws.getPayload().get("username");
        int identity = (int) jws.getPayload().get("identity");
        int userIcon = (int) jws.getPayload().get("userIcon");
        Account authenticationAccount=new Account();
        authenticationAccount.setUserMail(userMail);
        authenticationAccount.setUsername(username);
        authenticationAccount.setIdentity(identity);
        authenticationAccount.setUserIcon(userIcon);
 //       authenticationAccount.setId(id);
        if(checkIfAccountInBlackList(userMail)){
            //踢下线后，删除对应cookie，缓存中也不用再费劲的存被拉黑的人了
            cacheClient.deleteAccountFromBlackList(userMail);
            throw new JwtException("该用户已被拉黑");
        }

        //截至到此处，用户已经通过了所有的验证，是可以正常使用的了

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authenticationAccount, null, authenticationAccount.getAuthorities());
        //截至到此处，用户已经通过了所有的验证，是可以正常使用的了，我来给他刷新一次token
        if(jwtUtils.IfNeedFlush(jws.getPayload())){
            String newToken=jwtUtils.generateToken(userMail,username,identity,userIcon);
            response.setHeader(JwtUtils.getHeader(), newToken);
            response.setStatus(200);
//            Cookie cookie=new Cookie("passToken",newToken);
//            cookie.setMaxAge((int) JwtUtils.expire);
//            cookie.setHttpOnly(true);
//            response.addCookie(cookie);
        }
        //令牌通过了验证
        Authentication authentication=authenticationManager.authenticate(token);
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        this.securityContextHolderStrategy.setContext(context);
        chain.doFilter(request,response);
    }
    private boolean checkIfAccountInBlackList(String userMail){
        return cacheClient.isAccountInBlackList(userMail);
    }



//


}
