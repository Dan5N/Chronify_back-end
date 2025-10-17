package org.example.pojo;

import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.example.typehandler.IntegerListTypeHandler;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import java.util.List;

@Data
public class Course {
    private Long id;
    private String name;
    private Integer dayOfWeek;
    private String time;
    private String location;
    private String teacher;
    private List<Integer> weeks;
    private String notes;
    private Long userId;
}