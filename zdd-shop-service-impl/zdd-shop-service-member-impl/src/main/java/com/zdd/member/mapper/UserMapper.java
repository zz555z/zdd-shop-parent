package com.zdd.member.mapper;

import com.zdd.member.dto.UserDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Mapper
@Repository
public interface UserMapper {

	@Insert("INSERT INTO `meite_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
	int register(UserDO userDTO);

	@Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USERNAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID FROM meite_user WHERE MOBILE=#{mobile};")
	UserDO existMobile(@Param("mobile") String mobile);

	@Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USERNAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID "
			+ "  FROM meite_user  WHERE MOBILE=#{mobile} and password=#{newPwd};")
	UserDO login(@Param("mobile") String mobile, @Param("newPwd") String newPwd);

	@Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USERNAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID"
			+ " FROM meite_user WHERE user_Id=#{userId}")
	UserDO findByUserId(@Param("userId") Long userId);

	@Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USER_NAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID"
			+ " FROM meite_user WHERE qq_openid=#{qqOpenId}")
	UserDO findByOpenId(@Param("qqOpenId") String qqOpenId);

	@Update("update meite_user set QQ_OPENID =#{0} WHERE USER_ID=#{1}")
	int updateUserOpenId(@Param("qqOpenId") String qqOpenId, @Param("userId") Long userId);
}