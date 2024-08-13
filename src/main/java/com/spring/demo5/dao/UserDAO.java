package com.spring.demo5.dao;

import com.spring.demo5.domain.UserDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDAO {

  @Select("select * from user order by mid")
  List<UserDTO> getUserList();
  // find all 문법 = select * from user
  /*
  @Delete("delete from user where idx = #{idx}")
  int setUserDelete(int idx);
  */

  public int setUserInput(UserDTO dto);
  // application.properites에 aliases를 등록했기 때문에 @Param이 없어도 인식 됨

  @Select("select * from user where mid = #{mid}")
  public UserDTO getUserSearch(String mid);

  @Delete("delete from user where idx = #{idx}")
  public int setUserDelete(int idx);

}

