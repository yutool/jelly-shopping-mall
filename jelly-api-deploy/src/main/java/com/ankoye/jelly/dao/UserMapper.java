package com.ankoye.jelly.dao;

import com.ankoye.jelly.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
