package org.example.pojo;

import lombok.Data;
import java.io.Serializable;

/**
 * 分页结果封装
 */
@Data
public class PageResult {
    private Long total;    // 总记录数
    private Object rows;   // 当前页数据列表
}