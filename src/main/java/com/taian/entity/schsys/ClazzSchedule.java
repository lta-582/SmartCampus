package com.taian.entity.schsys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@TableName("schsys_clazz_schedule")
public class ClazzSchedule {


    @TableId
    private String clazzScheduleId;

    private String mon;

    private String tue;

    private String wed;

    private String thur;
    private String fri;

    private String sat;

    private String sun;
}
