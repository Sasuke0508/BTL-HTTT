package com.example.chandoanchanthuong;

import com.example.chandoanchanthuong.entity.Disease;
import com.example.chandoanchanthuong.repository.ReasonRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ChanDoanChanThuongApplicationTests {
    @Autowired
    private ReasonRepo reasonRepo;

    @Test
    void contextLoads() {
        List<Disease> diseaseList = reasonRepo.getById(1).getDiseaseList();
        diseaseList.forEach(disease -> System.out.println(disease.getName()));
    }

}
