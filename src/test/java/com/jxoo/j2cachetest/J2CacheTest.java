package com.jxoo.j2cachetest;

import net.oschina.j2cache.model.Command;
import net.oschina.j2cache.config.J2Cache;
import net.oschina.j2cache.service.cluster.ClusterPolicy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: J2CacheTest
 * Package: com.jxoo.j2cachetest
 * Description:
 *
 * @author JX
 * @version 1.0
 * @date 2023/10/26 11:37
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class J2CacheTest {

    @Autowired
    private J2Cache j2Cache;
    String redColor = "\u001B[31m";
    String defaultColor = "\u001B[0m";

    @Test
    public void test1() {
        //测试基本数据类型
        Map<String, Object> objs = new HashMap<>();
        objs.put("k1", 123);
        objs.put("k2", "v2");
        objs.put("k3", true);
        j2Cache.getChannel().set("region-1", objs, 60, true);
        System.out.println(redColor + j2Cache.getChannel().get("region-1", "k1") + defaultColor);
        System.out.println(redColor + j2Cache.getChannel().get("region-1", "k2") + defaultColor);
        System.out.println(redColor + j2Cache.getChannel().get("region-1", "k3") + defaultColor);
    }

    @Test
    public void test2() {
        //测试基本从L2缓存读取
        System.out.println(redColor + j2Cache.getChannel().get("region-1", "k1") + defaultColor);
        System.out.println(redColor + j2Cache.getChannel().get("region-1", "k2") + defaultColor);
        System.out.println(redColor + j2Cache.getChannel().get("region-1", "k3") + defaultColor);
    }

    @Test
    public void testCustomDataType() {
        //测试自定义数据类型
        Person me = new Person("JX", 22, true);
        j2Cache.getChannel().set("region-1", "JX", me, 60, true);
        System.out.println(redColor + j2Cache.getChannel().get("region-1", "JX") + defaultColor);
    }
    
    @Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        j2Cache.getChannel().set("region-2", "list", list, 60, true);
        System.out.println(redColor + j2Cache.getChannel().get("region-2", "list") + defaultColor);
    }

    @Test
    public void testMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);
        j2Cache.getChannel().set("region-2", "map", map, 60, true);
        System.out.println(redColor + j2Cache.getChannel().get("region-2", "map") + defaultColor);
    }

    @Test
    public void testRabbitMq() {
        ClusterPolicy policy = j2Cache.getBuilder().getPolicy();
        Command cmd = new Command();
        cmd.setOperator(Command.OPT_JOIN);
        cmd.setRegion("region-1");
        cmd.setKeys(new String[]{"k1"});
        policy.publish(cmd);
    }

    class Person {
        private String name;
        private int age;
        private boolean isMale;

        public Person(String name, int age, boolean isMale) {
            this.name = name;
            this.isMale = isMale;
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMale(boolean male) {
            isMale = male;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public boolean isMale() {
            return isMale;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", isMale=" + isMale +
                    '}';
        }

    }

}
