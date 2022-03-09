package com.company.vueblog;

import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VueblogApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMd5(){
        System.out.println(!"330c52c7e1e94a47712f39b16188dbd9".equals(SecureUtil.md5("123123")));
    }
}
