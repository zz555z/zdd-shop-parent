package com.zdd.member.mapper;

import com.zdd.member.dto.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface UserMapper {

	@Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
	int register(UserDO userDTO);

	@Select("SELECT username,mobile,email,PASSWORD,userid FROM meite_user WHERE MOBILE=#{mobile};")
	UserDO existMobile(@Param("mobile") String mobile);
}