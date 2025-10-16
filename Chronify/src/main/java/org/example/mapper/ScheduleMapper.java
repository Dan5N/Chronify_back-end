package org.example.mapper;

import org.example.pojo.Schedule;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Select("SELECT * FROM schedules WHERE user_id = #{userId} AND deleted_at IS NULL " +
            "AND (#{date} IS NULL OR date = #{date}) " +
            "ORDER BY date, time")
    List<Schedule> findByUserId(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Insert("INSERT INTO schedules(user_id, event, date, time, location, people, priority) " +
            "VALUES(#{userId}, #{event}, #{date}, #{time}, #{location}, #{people}, #{priority})")
    void insert(Schedule schedule);

    @Update("UPDATE schedules SET event = #{event}, date = #{date}, time = #{time}, " +
            "location = #{location}, people = #{people}, priority = #{priority} " +
            "WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    int update(Schedule schedule);

    @Update("UPDATE schedules SET deleted_at = NOW() WHERE id = #{id} AND user_id = #{userId}")
    int delete(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT * FROM schedules WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    Schedule findById(@Param("id") Long id, @Param("userId") Long userId);
}