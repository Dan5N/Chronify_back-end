package org.example.service.impl;

import org.example.mapper.ScheduleMapper;
import org.example.pojo.Schedule;
import org.example.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public List<Schedule> list(Long userId, LocalDate date) {
        return scheduleMapper.findByUserId(userId, date);
    }

    @Override
    public Schedule getById(Long id, Long userId) {
        return scheduleMapper.findById(id, userId);
    }

    @Override
    public void add(Schedule schedule) {
        scheduleMapper.insert(schedule);
    }

    @Override
    public void update(Schedule schedule) {
        scheduleMapper.update(schedule);
    }

    @Override
    public void delete(Long id, Long userId) {
        scheduleMapper.delete(id, userId);
    }
}