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
        log.info("查询日程列表，日期: {}", date);

        Long userId = CurrentUserUtil.getCurrentUserId();
        List<Schedule> schedules = scheduleService.list(userId, date);
        return Result.success(schedules);
    }

    @PostMapping
    public Result add(@RequestBody Schedule schedule) {
        log.info("添加日程: {}", schedule);

        Long userId = CurrentUserUtil.getCurrentUserId();
        schedule.setUserId(userId);

        scheduleService.add(schedule);
        return Result.success("添加成功");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("查询日程详情，ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();
        Schedule schedule = scheduleService.getById(id, userId);
        if (schedule == null) {
            return Result.error("日程不存在");
        }
        return Result.success(schedule);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Schedule schedule) {
        log.info("更新日程，ID: {}, 数据: {}", id, schedule);

        Long userId = CurrentUserUtil.getCurrentUserId();
        schedule.setId(id);
        schedule.setUserId(userId);

        scheduleService.update(schedule);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        log.info("删除日程，ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();
        scheduleService.delete(id, userId);
        return Result.success("删除成功");
    }
}