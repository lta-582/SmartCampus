package com.taian.entity.schsys;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SchsysExamInfo {
    @TableId
    String examInfoId;
    String clazzCode;
    String clazzName;
    String examDate;
    String examTime;
    String examRoomCode;
    String examRoomName;
    String examSeat;
    String examStatus;
}