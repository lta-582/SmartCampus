package com.taian.entity.schsys;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SchsysElectiveClazz {
    @TableId
    String electiveClazzId;
    String clazzCode;
    String clazzName;
    String credit;
    String examType;
    String semester;
    String score;
    String ableCredit;
}