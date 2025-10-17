package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Schedule;
import org.example.service.ScheduleService;
import org.example.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public Result list(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.info("Query schedule list, date: {}", date);

        Long userId = CurrentUserUtil.getCurrentUserId();
        List<Schedule> schedules = scheduleService.list(userId, date);
        return Result.success(schedules);
    }

    @PostMapping
    public Result add(@RequestBody Schedule schedule) {
        log.info("Add schedule: {}", schedule);

        Long userId = CurrentUserUtil.getCurrentUserId();
        schedule.setUserId(userId);

        scheduleService.add(schedule);
        return Result.success("Add successful");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("Query schedule details, ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();
        Schedule schedule = scheduleService.getById(id, userId);
        if (schedule == null) {
            return Result.error("Schedule not found");
        }
        return Result.success(schedule);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Schedule schedule) {
        log.info("Update schedule, ID: {}, data: {}", id, schedule);

        Long userId = CurrentUserUtil.getCurrentUserId();
        schedule.setId(id);
        schedule.setUserId(userId);

        scheduleService.update(schedule);
        return Result.success("Update successful");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        log.info("Delete schedule, ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();
        scheduleService.delete(id, userId);
        return Result.success("Delete successful");
    }
}