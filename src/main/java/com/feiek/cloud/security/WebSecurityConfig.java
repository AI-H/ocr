package com.feiek.cloud.security;

import com.feiek.cloud.service.UserService;
import com.feiek.cloud.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 装载一个对象
     */
    @Autowired
    private UserServiceImpl service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.service).passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //返回一个明文密码编码器
        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * 该方法刻可以指定某些请求，和某些角色访问
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有请求都要经过HTTP Basic认证
//        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();



        http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").and().authorizeRequests()
                .antMatchers("/login.html","/images/**","/css/**","/fonts/**","/js/**","/login")
                .permitAll().anyRequest().authenticated().and().httpBasic();
        http .csrf().disable();



    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("");
    }

    //    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //解决静态资源被拦截的问题
//        web.ignoring().antMatchers("/css/**","/fonts/**","");
//    }

//    protected void configure(HttpSecurity http) throws Exception {
////      http.httpBasic()// httpBasic 登录
//        http.formLogin()// 表单登录  来身份认证
//                .loginPage("/myLogin.html")// 自定义登录页面
//                .loginProcessingUrl("/authentication/form")// 自定义登录路径
//                .and()
//                .authorizeRequests()// 对请求授权
//                // error  127.0.0.1 将您重定向的次数过多
//                .antMatchers("/myLogin.html", "/authentication/require",
//                        "/authentication/form").permitAll()// 这些页面不需要身份认证,其他请求需要认证
//                .anyRequest() // 任何请求
//                .authenticated()//; // 都需要身份认证
//                .and()
//                .csrf().disable();// 禁用跨站攻击
//    }


}
