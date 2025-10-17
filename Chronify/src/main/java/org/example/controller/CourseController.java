package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Course;
import org.example.service.CourseService;
import org.example.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public Result list(@RequestParam(required = false) Integer dayOfWeek) {
        log.info("Query course list, day of week: {}", dayOfWeek);

        Long userId = CurrentUserUtil.getCurrentUserId();

        List<Course> courses = courseService.list(userId, dayOfWeek);
        return Result.success(courses);
    }

    @PostMapping
    public Result add(@RequestBody Course course) {
        log.info("Add course: {}", course);

        Long userId = CurrentUserUtil.getCurrentUserId();
        course.setUserId(userId);

        courseService.add(course);
        return Result.success("Add successful");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("Query course details, ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        Course course = courseService.getById(id, userId);
        if (course == null) {
            return Result.error("Course not found");
        }
        return Result.success(course);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Course course) {
        log.info("Update course, ID: {}, data: {}", id, course);

        Long userId = CurrentUserUtil.getCurrentUserId();
        course.setId(id);
        course.setUserId(userId);

        courseService.update(course);
        return Result.success("Update successful");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        log.info("Delete course, ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        courseService.delete(id, userId);
        return Result.success("Delete successful");
    }
}