package com.example.chandoanchanthuong;

import com.example.chandoanchanthuong.entity.Disease;
import com.example.chandoanchanthuong.entity.GroupSympton;
import com.example.chandoanchanthuong.entity.Sympton;
import com.example.chandoanchanthuong.repository.GroupSymptonRepo;
import com.example.chandoanchanthuong.repository.ReasonRepo;
import com.example.chandoanchanthuong.repository.SymptonRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ChanDoanChanThuongApplicationTests {
    @Autowired
    private ReasonRepo reasonRepo;

    @Autowired
    private GroupSymptonRepo groupSymptonRepo;

    @Autowired
    private SymptonRepo symptonRepo;

    @Test
    void contextLoads() {
        // get All group sympton
       List<GroupSympton> groupSymptonList =groupSymptonRepo.findAll();
       groupSymptonList.forEach(groupSympton -> {
           groupSympton.getSymptonList().forEach(sympton -> {
               System.out.println(sympton.name);
           });
       });

       // get symptons khong thuoc group nao
        // 2 la group , sau dua ve la 1
       List<Sympton> symptonList = symptonRepo.findById(2).get().getGroupSympton().getSymptonList();
       symptonList.forEach(sympton -> {
           System.out.println(sympton.name);
       });

       
    }

}
