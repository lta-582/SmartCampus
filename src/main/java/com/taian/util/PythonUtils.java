package com.taian.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taian.entity.User;
import com.taian.entity.schsys.*;
import com.taian.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PythonUtils {

    public static PythonUtils pythonUtils;

    @Autowired(required = false)
    ISchsysStudentInfoService iSchsysStudentInfoService;

    @Autowired
    UserService userService;

    @Autowired
    IClazzScheduleService iClazzScheduleService;

    @Autowired
    ISchsysAllClazzService iSchsysAllClazzService;

    @Autowired
    ISchsysElectiveClazzService iSchsysElectiveClazzService;

    @Autowired
    ISchsysExamInfoService iSchsysExamInfoService;


    List<String> strArry = new ArrayList<>();

    @PostConstruct
    public void init(){
        pythonUtils = this;
    }

    public String getValue(String studentId, String password){
        try {
            System.out.println(studentId+password);
            String[] args1 = new String[] { "python", "E:\\Works\\python\\main.py", studentId, password };

            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line = null;

            while ((line = in.readLine()) != null) {
                strArry.add(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据注意"+strArry.toString());
        return strArry.get(0);
    }

    //对获取的数据进行处理并且保存
    public boolean dataStorage (String studentId,String password){
        this.getValue(studentId,password);

        //对个人信息进行保存
        String s = strArry.get(2);
        String[] split = s.split("@");
        System.out.print(Arrays.toString(split));

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",split[0]);
        User one = pythonUtils.userService.getOne(queryWrapper);
        Integer userId = one.getUserId();
        System.out.println(userId);


        SchsysStudentInfo schsysStudentInfo = new SchsysStudentInfo();
        schsysStudentInfo.setUserId(userId);
        schsysStudentInfo.setStudentId(split[0]);
        schsysStudentInfo.setStudentName(split[1]);
        schsysStudentInfo.setGrade(split[2]);
        schsysStudentInfo.setMajor(split[3]);
        schsysStudentInfo.setSchoolMail(split[5]+"@"+split[6]);
        schsysStudentInfo.setAdminClass(split[9]);
        schsysStudentInfo.setTutorName(split[10]);
        schsysStudentInfo.setInstructorName(split[11]);
        System.out.println("保存前"+ schsysStudentInfo);
        pythonUtils.iSchsysStudentInfoService.saveOrUpdate(schsysStudentInfo);

        //格式化数据进行保存对课表进行保存
        s = strArry.get(1);
        System.out.println(s);
        s = s.replace("&nbsp;","");
        String[] split_classSch = s.split("@");

        int time = 0;
        ClazzSchedule clazzSchedule = new ClazzSchedule();
        System.out.println(Arrays.toString(split_classSch));


        for (;time<7;time++)
        {
            clazzSchedule.setClazzScheduleId(studentId+time);
            clazzSchedule.setMon(split_classSch[time*8+1]);
            clazzSchedule.setTue(split_classSch[time*8+2]);
            clazzSchedule.setWed(split_classSch[time*8+3]);
            clazzSchedule.setThur(split_classSch[time*8+4]);
            clazzSchedule.setFri(split_classSch[time*8+5]);
            clazzSchedule.setSat(split_classSch[time*8+6]);
            clazzSchedule.setSun(split_classSch[time*8+7]);
            pythonUtils.iClazzScheduleService.saveOrUpdate(clazzSchedule);
        }

        //格式化存储全部必修课程信息
        SchsysAllClazz schsysAllClazz = new SchsysAllClazz();
        String table[][] = new String[80][11];
        s = strArry.get(3);
        int table_j = 0;
        String[] split_1 = s.split("\\|");
        for (String strItem : split_1
             ) {
            String[] table_split = strItem.split("@");
            table[table_j] = table_split;
            table_j++;
        }
        for (int i = 0;i<80;i++)
            System.out.println(Arrays.toString(table[i]));
        int clazz_all_i = 0;
        int clazz_all_j = 0;
        while (table[clazz_all_i][3] != null){
            if (!table[clazz_all_i][1].equals("")){
                clazz_all_j++;
            }
            schsysAllClazz.setAllClazzId(studentId+(clazz_all_i+1));
            schsysAllClazz.setSemesterNo(clazz_all_j);
            schsysAllClazz.setClazzCode(table[clazz_all_i][2]);
            schsysAllClazz.setClazzName(table[clazz_all_i][3]);
            schsysAllClazz.setCredit(table[clazz_all_i][4]);
            schsysAllClazz.setExamType(table[clazz_all_i][5]);
            if (table[clazz_all_i].length>6)
                schsysAllClazz.setSemester(table[clazz_all_i][6]);
            else schsysAllClazz.setSemester("");
            if (table[clazz_all_i].length>7)
                schsysAllClazz.setScore(table[clazz_all_i][7]);
            else schsysAllClazz.setScore("");
            if (table[clazz_all_i].length>8)
                schsysAllClazz.setAbleCredit(table[clazz_all_i][8]);
            else schsysAllClazz.setAbleCredit("");

            pythonUtils.iSchsysAllClazzService.saveOrUpdate(schsysAllClazz);
            clazz_all_i++;
        }

        //格式化存储所有选修的课程
        s = strArry.get(4);

        String[] split_ele = s.split("\\|");
        SchsysElectiveClazz schsysElectiveClazz = new SchsysElectiveClazz();
        int ele_index = 1;
        for (String split_ele_item:split_ele){
            split = split_ele_item.split("@");
            schsysElectiveClazz.setElectiveClazzId(studentId+ele_index);
            schsysElectiveClazz.setClazzCode(split[0]);
            schsysElectiveClazz.setClazzName(split[1]);
            schsysElectiveClazz.setCredit(split[2]);
            schsysElectiveClazz.setExamType(split[3]);
            schsysElectiveClazz.setSemester(split[4]);
            if (split.length>5)
                schsysElectiveClazz.setScore(split[5]);
            else schsysElectiveClazz.setScore("");
            if (split.length>6)
                schsysElectiveClazz.setAbleCredit(split[6]);
            else schsysElectiveClazz.setAbleCredit("");
            pythonUtils.iSchsysElectiveClazzService.saveOrUpdate(schsysElectiveClazz);
            System.out.println(Arrays.toString(split_ele_item.split("@")));
            ele_index++;
        }

        //格式化并存储考试时间
        s = strArry.get(5);
        SchsysExamInfo schsysExamInfo = new SchsysExamInfo();
        String[] split_exam = s.split("\\|");
        int index_exam = 1;
        if (split_exam.length <= 2)
            System.out.println("没有考试课程信息");
        else{
            for (String split_exam_item:split_exam){
                split = split_exam_item.split("@");
                schsysExamInfo.setExamInfoId(studentId+index_exam);
                schsysExamInfo.setClazzCode(split[0]);
                schsysExamInfo.setClazzName(split[1]);
                schsysExamInfo.setExamDate(split[2]);
                schsysExamInfo.setExamTime(split[3]);
                schsysExamInfo.setExamRoomCode(split[4]);
                schsysExamInfo.setExamRoomName(split[5]);
                schsysExamInfo.setExamSeat(split[6]);
                schsysExamInfo.setExamStatus(split[7]);
                pythonUtils.iSchsysExamInfoService.saveOrUpdate(schsysExamInfo);
                index_exam++;
            }
        }


        return true;
    }
}
