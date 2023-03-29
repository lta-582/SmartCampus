package com.taian.entity.schsys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("schsys_comp_clazz")
public class SchsysAllClazz {
    @TableId
    String allClazzId;
    Integer semesterNo;
    String clazzCode;
    String clazzName;
    String credit;
    String examType;
    String semester;
    String score;
    String ableCredit;
}
