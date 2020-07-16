package com.ankoye.jelly.user.mapper;

import com.ankoye.jelly.user.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ankoye@qq.com
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
