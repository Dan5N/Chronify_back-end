package org.example.service;

import org.example.pojo.Schedule;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    List<Schedule> list(Long userId, LocalDate date);

    Schedule getById(Long id, Long userId);

    void add(Schedule schedule);

    void update(Schedule schedule);

    void delete(Long id, Long userId);
}