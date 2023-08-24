package com.example.springsecuritydemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springsecuritydemo1.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}
