package com.jxoo.j2cachetest;

import net.oschina.j2cache.config.J2Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void test() {
        j2Cache.getChannel().set("test", "test", "123");
        System.out.println(j2Cache.getChannel().get("test", "test"));
    }

}
