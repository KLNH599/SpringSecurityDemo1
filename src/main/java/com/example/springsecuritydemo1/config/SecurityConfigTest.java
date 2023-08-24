package com.example.springsecuritydemo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    //注入数据源
    @Autowired
    private DataSource dataSource;
    //配置对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 配置 403 没有权限页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        //退出配置
        http.logout().logoutUrl("/logout") //退出路径
                .logoutSuccessUrl("/test/hello").permitAll();  //退出成功后跳转到的地址
        http.formLogin()  //定义自己写的登陆页面
                .loginPage("/login.html") //登录页面设置
                .loginProcessingUrl("/user/login") //登录访问路径
                .defaultSuccessUrl("/success.html").permitAll() //登录成功之后跳转的路径
                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll() //哪些路径不需要进行认证
                // 当前登录用户只有拥有 admins 权限才可以访问这个路径
                //.antMatchers("/test/index").hasAuthority("admins")

                //.antMatchers("/test/index").hasAnyAuthority("admins,manager")
                //.antMatchers("/test/index").hasRole("sale")
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                //设置token 有效时长，单位为秒
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailsService);
                //.and().csrf().disable(); //关闭 csrf 防护网
    }


}
