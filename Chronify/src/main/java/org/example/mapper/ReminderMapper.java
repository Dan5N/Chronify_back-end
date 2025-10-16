package org.example.mapper;

import org.example.pojo.Reminder;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReminderMapper {

    @Select("SELECT * FROM reminders WHERE user_id = #{userId} AND is_active = TRUE " +
            "AND reminder_time >= NOW() AND deleted_at IS NULL " +
            "ORDER BY reminder_time")
    List<Reminder> findActiveReminders(Long userId);

    @Insert("INSERT INTO reminders(user_id, event, reminder_time, schedule_id) " +
            "VALUES(#{userId}, #{event}, #{reminderTime}, #{scheduleId})")
    void insert(Reminder reminder);

    @Update("UPDATE reminders SET is_active = FALSE " +
            "WHERE id = #{id} AND user_id = #{userId}")
    int cancel(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT * FROM reminders WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    Reminder findById(@Param("id") Long id, @Param("userId") Long userId);
}