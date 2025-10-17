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
        log.info("Get active reminder list");

        Long userId = CurrentUserUtil.getCurrentUserId();

        List<Reminder> reminders = reminderService.getActiveReminders(userId);
        return Result.success(reminders);
    }

    @PostMapping("/reminders")
    public Result addReminder(@RequestBody Reminder reminder) {
        log.info("Create reminder: {}", reminder);

        Long userId = CurrentUserUtil.getCurrentUserId();
        reminder.setUserId(userId);

        reminderService.add(reminder);
        return Result.success("Create successful");
    }

    @DeleteMapping("/reminders/{id}")
    public Result deleteReminder(@PathVariable Long id) {
        log.info("Cancel reminder, ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        reminderService.delete(id, userId);
        return Result.success("Delete successful");
    }
}