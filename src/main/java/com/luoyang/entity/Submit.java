package com.luoyang.entity;

import lombok.Data;

import java.util.List;

@Data
public class Submit {
    private String task_name;
    private String file_id;
    private String hall_name;
    private Boolean immediately;
    private List<String> type;
    private String remark;
}
