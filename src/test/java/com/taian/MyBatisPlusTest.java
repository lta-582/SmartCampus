package com.taian;

import com.taian.util.PythonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusTest {

//    @Autowired
//    private ClazzScheduleMapper clazzScheduleMapper;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Test
//    public void testSelectList(){
//        //通过条件构造器查询一个List集合，若没有集合，可以设置null为参数
//        List<ClazzSchedule> clazzSchedules = clazzScheduleMapper.selectList(null);
//        clazzSchedules.forEach(System.out::println);
//    }
//    @Test
//    public void testRedis(){
//        Boolean flag = null;
//        //插入string
//        flag = redisUtil.setString("testString","testString");
//        System.out.println(flag);
//        String result = "";
//        //获取string
//        result = redisUtil.getString("testString");
//        System.out.println(result);
//        //修改string
//        flag = redisUtil.getAndSetString("testString","rename");
//        System.out.println(flag);
//        //获取string
//        result = redisUtil.getString("testString");
//        System.out.println(result);
//        //栓除string
//        //flag = redisUtil.delete("testString");
//        result = redisUtil.getString("testString");
//        System.out.println(result);
//    }

    @Test
    public void python(){
        PythonUtils pythonUtils = new PythonUtils();
        Boolean b = pythonUtils.dataStorage("2140521001", "hzm.10106001");
        System.out.println(b);
    }
}
