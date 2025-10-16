package org.example.service.impl;

import org.example.mapper.CourseMapper;
import org.example.pojo.Course;
import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> list(Long userId, Integer dayOfWeek) {
        return courseMapper.findByUserId(userId, dayOfWeek);
    }

    @Override
    public Course getById(Long id, Long userId) {
        return courseMapper.findById(id, userId);
    }

    @Override
    public void add(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }

    @Override
    public void delete(Long id, Long userId) {
        courseMapper.delete(id, userId);
    }
}