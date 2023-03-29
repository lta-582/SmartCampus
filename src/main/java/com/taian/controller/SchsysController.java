package com.taian.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taian.entity.User;
import com.taian.entity.schsys.*;
import com.taian.service.*;
import com.taian.util.Result;
import com.taian.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/schsys")
public class SchsysController {
    @Autowired
    ISchsysAllClazzService iSchsysAllClazzService;

    @Autowired
    ISchsysElectiveClazzService iSchsysElectiveClazzService;

    @Autowired
    UserService userService;

    @Autowired
    ISchsysStudentInfoService iSchsysStudentInfoService;

    @Autowired
    IClazzScheduleService iClazzScheduleService;

    @Autowired
    ISchsysExamInfoService iSchsysExamInfoService;

    @GetMapping("/getele")
    public Result getComp(
            @RequestHeader("token") String token
    ) {
        String openId = TokenUtil.tokenUtil(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        User user = userService.getOne(queryWrapper);
        SchsysElectiveClazz schsysElectiveClazz = new SchsysElectiveClazz();
        ArrayList<SchsysElectiveClazz> schsysElectiveClazzes = new ArrayList<>();
        for (int i = 1; ; i++) {
            schsysElectiveClazz = iSchsysElectiveClazzService.getById(user.getStudentId() + i);
            if (schsysElectiveClazz != null) {
                schsysElectiveClazzes.add(schsysElectiveClazz);
                System.out.println(schsysElectiveClazz);
            } else
                break;
        }
        return Result.ok(schsysElectiveClazzes);
    }

    @GetMapping("/getcomp")
    public Result getEle(
            @RequestHeader("token") String token
    ) {
        String openId = TokenUtil.tokenUtil(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        User user = userService.getOne(queryWrapper);
        SchsysAllClazz schsysAllClazz = new SchsysAllClazz();
        ArrayList<SchsysAllClazz> schsysAllClazzeslist = new ArrayList<>();
        for (int i = 1; ; i++) {
            schsysAllClazz = iSchsysAllClazzService.getById(user.getStudentId() + i);
            if (schsysAllClazz != null) {
                schsysAllClazzeslist.add(schsysAllClazz);
                System.out.println(schsysAllClazz);
            } else
                break;
        }
        return Result.ok(schsysAllClazzeslist);
    }

    @GetMapping("/getstudentinfo")
    public Result getStudentInfo(
            @RequestHeader("token") String token
    ) {
        String openId = TokenUtil.tokenUtil(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        User user = userService.getOne(queryWrapper);
        SchsysStudentInfo infoServiceById = iSchsysStudentInfoService.getById(user.getUserId());
        return Result.ok(infoServiceById);
    }

    @GetMapping("/getexaminfo")
    public Result getExamInfo(
            @RequestHeader("token") String token
    ) {
        String openId = TokenUtil.tokenUtil(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        User user = userService.getOne(queryWrapper);
        SchsysExamInfo schsysExamInfo;
        ArrayList<SchsysExamInfo> schsysExamInfos = new ArrayList<>();
        for (int i = 1; ; i++) {
            schsysExamInfo = iSchsysExamInfoService.getById(user.getStudentId() + i);
            if (schsysExamInfo != null) {
                schsysExamInfos.add(schsysExamInfo);
                System.out.println(schsysExamInfo);
            } else
                break;
        }
        return Result.ok(schsysExamInfos);
    }

    @GetMapping("/getclazzschedule")
    public Result getClazzSchedule(
            @RequestHeader("token") String token
    ) {
        String openId = TokenUtil.tokenUtil(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        User user = userService.getOne(queryWrapper);
        ClazzSchedule clazzSchedule;
        String[][] strings = new String[7][7];
        for (int i = 0; ; i++) {
            clazzSchedule = iClazzScheduleService.getById(user.getStudentId() + i);
            if (clazzSchedule != null) {
                strings[i][0] = clazzSchedule.getMon();
                strings[i][1] = clazzSchedule.getTue();
                strings[i][2] = clazzSchedule.getWed();
                strings[i][3] = clazzSchedule.getThur();
                strings[i][4] = clazzSchedule.getFri();
                strings[i][5] = clazzSchedule.getSat();
                strings[i][6] = clazzSchedule.getSun();
                System.out.println(clazzSchedule);
            } else
                break;
        }
        String[][] stringsTemp = new String[7][7];
        for (int i = 0;i<7;i++)
            for (int j = 0;j<7;j++)
                stringsTemp[j][i] = strings[i][j];
        return Result.ok(stringsTemp);
    }
}
