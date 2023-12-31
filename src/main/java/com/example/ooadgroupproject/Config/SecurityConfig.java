package com.example.ooadgroupproject.Config;

import com.example.ooadgroupproject.Filter.MyUsernamePasswordAuthenticationFilter;
import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.handler.JwtAccessDeniedHandler;
import com.example.ooadgroupproject.handler.JwtAuthenticationEntryPoint;
import com.example.ooadgroupproject.Filter.JwtAuthenticationFilter;
import com.example.ooadgroupproject.handler.LoginFailureHandler;
import com.example.ooadgroupproject.handler.LoginSuccessHandler;
import com.example.ooadgroupproject.handler.LogoutSuccessHandler;
import com.example.ooadgroupproject.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    AccountService accountService;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new AuthenticationManager() {
            @Autowired
            private AccountService accountService;

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
                UsernamePasswordAuthenticationToken authenticatedToken = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null, authorities);
                return authenticatedToken;
            }
        };
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter()throws Exception{
        return new JwtAuthenticationFilter(authenticationManager());
    }
    @Bean
    public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter()throws Exception{

        return new MyUsernamePasswordAuthenticationFilter(authenticationManager());
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.csrf(csrf-> csrf.disable());
        http.authorizeHttpRequests(auth->
                auth.anyRequest().access((authentication,object)->{
                    boolean isMatched=false;
                    String requestUrl=object.getRequest().getRequestURI();
                    Collection<? extends GrantedAuthority> authorities=
                            authentication.get().getAuthorities();
                    for(GrantedAuthority authority:authorities){
                        if(authority.getAuthority().equals(IdentityLevel.roleAccountAdmin)){
                            isMatched=true;
                            break;
                        }else if(authority.getAuthority().equals(IdentityLevel.roleNormalUser)){
                            isMatched=true;
                            break;
                        }else if(authority.getAuthority().equals(IdentityLevel.roleVisitor)){
                            isMatched=true;
                            break;
                        }
                    }
                    return new AuthorizationDecision(isMatched);
                }
                )
        );
        http.cors(c->c
               .configurationSource(corsConfigurationSource())
        );


        http.formLogin(l->l
                .loginPage("/login/loginCheck")
                .successHandler(loginSuccessHandler)
                .permitAll()
                .failureHandler(loginFailureHandler)
                .permitAll()
        );

        http.logout(l->l
                .logoutUrl("/login/logout")
                .logoutSuccessHandler(logoutSuccessHandler).permitAll()
        );
        http.sessionManagement(s->s
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.exceptionHandling(e->e
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        );
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedOrigin("http://localhost:8080");
                corsConfiguration.addAllowedHeader("Content-Type");
                corsConfiguration.addAllowedHeader("Authorization");
                corsConfiguration.addAllowedHeader("passToken");
                corsConfiguration.addAllowedMethod("GET");
                corsConfiguration.addAllowedMethod("POST");
                corsConfiguration.addAllowedMethod("PUT");
                corsConfiguration.addAllowedMethod("DELETE");
                return corsConfiguration;
            }
        };
    }

}


