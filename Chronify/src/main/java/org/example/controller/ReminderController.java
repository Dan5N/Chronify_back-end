package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Reminder;
import org.example.service.ReminderService;
import org.example.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping("/reminders/active")
    public Result getActiveReminders() {
        log.info("获取活跃提醒列表");

        Long userId = CurrentUserUtil.getCurrentUserId();

        List<Reminder> reminders = reminderService.getActiveReminders(userId);
        return Result.success(reminders);
    }

    @PostMapping("/reminders")
    public Result addReminder(@RequestBody Reminder reminder) {
        log.info("创建提醒: {}", reminder);

        Long userId = CurrentUserUtil.getCurrentUserId();
        reminder.setUserId(userId);

        reminderService.add(reminder);
        return Result.success("创建成功");
    }

    @DeleteMapping("/reminders/{id}")
    public Result deleteReminder(@PathVariable Long id) {
        log.info("取消提醒，ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        reminderService.delete(id, userId);
        return Result.success("删除成功");
    }
}