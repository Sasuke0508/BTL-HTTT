package com.example.chandoanchanthuong;

import com.example.chandoanchanthuong.entity.Case;
import com.example.chandoanchanthuong.entity.Disease;
import com.example.chandoanchanthuong.entity.GroupSympton;
import com.example.chandoanchanthuong.entity.Sympton;
import com.example.chandoanchanthuong.repository.CaseRepo;
import com.example.chandoanchanthuong.repository.GroupSymptonRepo;
import com.example.chandoanchanthuong.repository.ReasonRepo;
import com.example.chandoanchanthuong.repository.SymptonRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ChanDoanChanThuongApplicationTests {
    @Autowired
    private ReasonRepo reasonRepo;

    @Autowired
    private GroupSymptonRepo groupSymptonRepo;

    @Autowired
    private SymptonRepo symptonRepo;

    @Autowired
    private CaseRepo caseRepo;

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
       List<Sympton> symptonList = symptonRepo.findById(1).get().getGroupSympton().getSymptonList();
       symptonList.forEach(sympton -> {
           System.out.println(sympton.name);
       });
<<<<<<< Updated upstream
=======

    }

    @Test
    void loadCase() {
        List<Case> caseList = caseRepo.findAll();
        System.out.println(caseList.size());
>>>>>>> Stashed changes
    }

    @Test
    public void setValueInMap(){
        Map<String, String> mapTest = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            mapTest.put("Key" + i, ""+i);
        }
        System.out.println("==================== Before ====================");
        mapTest.forEach((s, s2) -> System.out.println(s + ": " + s2));
        mapTest.forEach((s, s2) -> mapTest.put(s, s2+" change"));
        System.out.println("==================== After ====================");
        mapTest.forEach((s, s2) -> System.out.println(s + ": " + s2));
    }
}
