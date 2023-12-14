package com.example.ooadgroupproject.handler;

import cn.hutool.json.JSONUtil;
import com.example.ooadgroupproject.Utils.JwtUtils;
import com.example.ooadgroupproject.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.example.ooadgroupproject.common.Result;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain,
                                        Authentication authentication)
            throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        Object authenticationPrincipal=authentication.getPrincipal();
        if(authenticationPrincipal instanceof Account) {
            Account authenticationAccount = (Account) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken( authenticationAccount.getUserMail()
                    , authenticationAccount.getUsername(), authenticationAccount.getIdentity()
            );
            Jws<Claims> aa = JwtUtils.parseClaim(jwt);
            Claims c = JwtUtils.parsePayload(jwt);


            response.setHeader(JwtUtils.getHeader(), jwt);
            Result result = Result.success(jwt);

            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        }else {
            Result result = Result.fail("登录失败");
        }
        outputStream.flush();
        outputStream.close();
    }
}
