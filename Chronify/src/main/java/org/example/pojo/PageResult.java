package org.example.pojo;

import lombok.Data;
import java.io.Serializable;

/**
 * Pagination result wrapper
 */
@Data
public class PageResult {
    private Long total;    // Total record count
    private Object rows;   // Current page data list
}