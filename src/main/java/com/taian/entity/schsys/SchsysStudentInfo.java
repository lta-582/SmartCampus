package com.taian.entity.schsys;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SchsysStudentInfo {

    @TableId
    Integer userId;
    String studentId;
    String studentName;
    String major;
    String schoolMail;
    String adminClass;
    String instructorName;
    String tutorName;
    String grade;
}
