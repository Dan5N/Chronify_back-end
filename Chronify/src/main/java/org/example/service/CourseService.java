package org.example.service;

import org.example.pojo.Course;
import java.util.List;

public interface CourseService {

    List<Course> list(Long userId, Integer dayOfWeek);

    Course getById(Long id, Long userId);

    void add(Course course);

    void update(Course course);

    void delete(Long id, Long userId);
}