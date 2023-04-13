package com.mk.openai;

import com.mk.openai.dto.LawRaw;
import com.mk.openai.infrastructure.mongo.repository.LawRawRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = SpringApplication.class)
public class SpringTest {

    @Resource private LawRawRepository lawRawRepository;

    @Test
    public void test() {
        LawRaw raw =
                LawRaw.builder()
                        .lawName("xxxx法")
                        .lawNo(1)
                        .lawContent("专门测试的数据")
                        .status(0)
                        .build();

        LawRaw data = lawRawRepository.insert(raw);
        System.out.println(data);
    }
}
