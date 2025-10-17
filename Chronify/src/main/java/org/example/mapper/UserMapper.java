package org.example.mapper;

import org.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * User Data Access Layer Interface
 *
 * Defines user-related database operations
 *
 * @author Chronify
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    /**
     * Query user by username
     *
     * @param username Username
     * @return User information, returns null if not exists
     */
    @Select("SELECT * FROM users WHERE username = #{username} AND deleted_at IS NULL")
    User findByUsername(String username);

    /**
     * Query user by user ID
     *
     * @param id User ID
     * @return User information, returns null if not exists
     */
    @Select("SELECT * FROM users WHERE id = #{id} AND deleted_at IS NULL")
    User findById(Long id);

    /**
     * Insert new user
     *
     * @param user User information
     */
    @Insert("INSERT INTO users(username, password, nickname, gender, school) VALUES(#{username}, #{password}, #{nickname}, #{gender}, #{school})")
    void insert(User user);

    /**
     * Update user information
     *
     * @param user User information
     */
    @Update("UPDATE users SET nickname = #{nickname}, gender = #{gender}, school = #{school}, " +
            "updated_at = NOW() WHERE id = #{id} AND deleted_at IS NULL")
    void updateProfile(User user);
}