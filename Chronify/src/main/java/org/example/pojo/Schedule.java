package org.example.pojo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Schedule {
    private Long id;
    private String event;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String people;
    private String priority;
    private Long userId;
}