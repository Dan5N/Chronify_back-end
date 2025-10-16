package org.example.mapper;

import org.example.pojo.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT * FROM courses WHERE user_id = #{userId} AND deleted_at IS NULL " +
            "AND (#{dayOfWeek} IS NULL OR day_of_week = #{dayOfWeek}) " +
            "ORDER BY day_of_week, time")
    List<Course> findByUserId(@Param("userId") Long userId, @Param("dayOfWeek") Integer dayOfWeek);

    @Insert("INSERT INTO courses(user_id, name, day_of_week, time, location, teacher, weeks, notes) " +
            "VALUES(#{userId}, #{name}, #{dayOfWeek}, #{time}, #{location}, #{teacher}, #{weeks}, #{notes})")
    void insert(Course course);

    @Update("UPDATE courses SET name = #{name}, day_of_week = #{dayOfWeek}, time = #{time}, " +
            "location = #{location}, teacher = #{teacher}, weeks = #{weeks}, notes = #{notes} " +
            "WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    int update(Course course);

    @Update("UPDATE courses SET deleted_at = NOW() WHERE id = #{id} AND user_id = #{userId}")
    int delete(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT * FROM courses WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    Course findById(@Param("id") Long id, @Param("userId") Long userId);
}