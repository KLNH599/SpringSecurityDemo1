package com.example.springsecuritydemo1.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springsecuritydemo1.entity.Users;
import com.example.springsecuritydemo1.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用 usersMapper ,根据用户名查询数据库
        QueryWrapper<Users> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",username);
        Users users = usersMapper.selectOne(queryWrapper);
        System.out.println(users);
        if(users == null){//如果等于空表示用户不存在
            throw new UsernameNotFoundException("用户不存在");
        }

        //设置权限集合
        List<GrantedAuthority> list =
                AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale");
        return new User(users.getUserName(),new BCryptPasswordEncoder().encode(users.getPassword()),list);
    }


}
