package com.luoyang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserFile {
    private String id;
    private String old_name;
    private String new_name;
    private String ext;
    private String path;
    private Integer size;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date upload_time;
}
