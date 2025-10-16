package org.example.service.impl;

import org.example.mapper.ReminderMapper;
import org.example.pojo.Reminder;
import org.example.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderMapper reminderMapper;

    @Override
    public List<Reminder> getActiveReminders(Long userId) {
        return reminderMapper.findActiveReminders(userId);
    }

    @Override
    public void add(Reminder reminder) {
        reminderMapper.insert(reminder);
    }

    @Override
    public void delete(Long id, Long userId) {
        reminderMapper.cancel(id, userId);
    }
}