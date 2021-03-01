package com.luoyang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Analysis {
    private String id;
    private String task_name;
    private String file_id;
    private String hall_name;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String begin_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String end_time;
    private String ocr_filename;
    private String wordcard_filename;
    private String action_filename;
    private String video_filename;
}
