package com.ankoye.jelly.seckill;

import com.ankoye.jelly.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

//@SpringBootTest
//@RunWith(SpringRunner.class)
@Slf4j
public class SeckillApplicationTests {

    @Test
    public void test() {
        List<String> dateMenus = DateUtils.getDateMenus();
        for (String dateMenu : dateMenus) {
            System.out.println(dateMenu);
        }
    }
}
