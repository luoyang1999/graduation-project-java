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
public class User {
    private String id;
    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date login_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time;
}
