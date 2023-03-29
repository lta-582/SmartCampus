package com.taian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taian.entity.Schedule;
import com.taian.service.IScheduleService;
import com.taian.service.UserService;
import com.taian.util.DateUtils;
import com.taian.util.Result;
import com.taian.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    IScheduleService iScheduleService;

    @Autowired
    UserService userService;


    @PostMapping("/add")
    public Result add(
            @RequestBody Map<String, Object> map,
            @RequestHeader("token")String token
            ){

        Schedule schedule = new Schedule();
        Integer userId = userService.openidToUserid(TokenUtil.tokenUtil(token));
        schedule.setUserId(userId);
        schedule.setCreateTime(DateUtils.getDateNowTimeNo());
        schedule.setContent(map.get("content").toString());
        schedule.setEventDate(Long.valueOf(map.get("eventDate").toString()));
        System.out.println(map.get("eventDate").toString());
        schedule.setEventStart(Integer.valueOf(1+map.get("eventStart").toString()+1));
        schedule.setEventEnd(Integer.valueOf(1+map.get("eventEnd").toString()+1));
        System.out.println(schedule);

        iScheduleService.save(schedule);

        return Result.ok();
    }

    @PostMapping("/get")
    public Result get(
            @RequestHeader("token") String token,
            @RequestBody Map<String, Object> map
    ){
        Long eventDate = Long.valueOf(map.get("eventDate").toString());
        Integer userid = userService.openidToUserid(TokenUtil.tokenUtil(token));
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        Page<Schedule> page = new Page<>(Integer.valueOf(map.get("pageNo").toString()),Integer.valueOf(map.get("pageSize").toString()));
        queryWrapper.eq("user_id",userid);
        queryWrapper.eq("event_date",eventDate);
        IPage<Schedule> scheduleByOpr = iScheduleService.getScheduleByOpr(page, queryWrapper);
        return Result.ok(scheduleByOpr);

    }

    @PostMapping("/del")
    public  Result del(
            @RequestHeader("token") String token,
            @RequestBody Map<String, Integer> map
    ) {
        String openId = TokenUtil.tokenUtil(token);
        Integer userid = userService.openidToUserid(openId);
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        queryWrapper.eq("schedule_id",map.get("scheduleId"));
        System.out.println(map.get("scheduleId"));
        System.out.println("是否remove" + iScheduleService.remove(queryWrapper));;
        return Result.ok();
    }
}
