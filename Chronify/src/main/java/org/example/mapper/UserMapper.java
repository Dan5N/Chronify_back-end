package org.example.mapper;

import org.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户数据访问层接口
 *
 * 定义用户相关的数据库操作
 *
 * @author Chronify
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息，不存在返回null
     */
    @Select("SELECT * FROM users WHERE username = #{username} AND deleted_at IS NULL")
    User findByUsername(String username);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息，不存在返回null
     */
    @Select("SELECT * FROM users WHERE id = #{id} AND deleted_at IS NULL")
    User findById(Long id);

    /**
     * 插入新用户
     *
     * @param user 用户信息
     */
    @Insert("INSERT INTO users(username, password, nickname, gender, school) VALUES(#{username}, #{password}, #{nickname}, #{gender}, #{school})")
    void insert(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    @Update("UPDATE users SET nickname = #{nickname}, gender = #{gender}, school = #{school}, " +
            "updated_at = NOW() WHERE id = #{id} AND deleted_at IS NULL")
    void updateProfile(User user);
}