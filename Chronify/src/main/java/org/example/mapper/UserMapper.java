package org.example.mapper;

import org.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username} AND deleted_at IS NULL")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE id = #{id} AND deleted_at IS NULL")
    User findById(Long id);

    @Insert("INSERT INTO users(username, password, nickname, gender, school) VALUES(#{username}, #{password}, #{nickname}, #{gender}, #{school})")
    void insert(User user);
}