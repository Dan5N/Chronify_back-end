package org.example.service;

import org.example.pojo.Reminder;
import java.util.List;

public interface ReminderService {

    List<Reminder> getActiveReminders(Long userId);

    void add(Reminder reminder);

    void delete(Long id, Long userId);
}