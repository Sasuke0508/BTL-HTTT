package com.example.chandoanchanthuong.controller;

import com.example.chandoanchanthuong.entity.GroupSympton;
import com.example.chandoanchanthuong.entity.MedicalHistory;
import com.example.chandoanchanthuong.entity.Reason;
import com.example.chandoanchanthuong.entity.Sympton;
import com.example.chandoanchanthuong.repository.GroupSymptonRepo;
import com.example.chandoanchanthuong.repository.MedicalHistoryRepo;
import com.example.chandoanchanthuong.repository.ReasonRepo;
import com.example.chandoanchanthuong.repository.SymptonRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/diagnose")
public class CaseBaseReasoning {

    @Autowired
    private ReasonRepo reasonRepo;

    @Autowired
    private GroupSymptonRepo groupSymptonRepo;

    @Autowired
    private SymptonRepo symptonRepo;

    @Autowired
    private MedicalHistoryRepo medicalHistoryRepo;

    @PostMapping("")
    public String handleDiagnose(HttpServletRequest request, HttpServletResponse response){

        return "result";
    }

    @GetMapping("")
    public String showInputPage(Model model){
        // Get list bệnh sử
        List<MedicalHistory> listMedicalHistory = medicalHistoryRepo.findAll();
        model.addAttribute("listMedicalHistory", listMedicalHistory);
        // Get list nguyên nhân
        List<Reason> listReason = reasonRepo.findAll();
        model.addAttribute("listReason", listReason);
        // Get list triệu chứng

        // get All group sympton
        List<GroupSympton> groupSymptonList =groupSymptonRepo.findAll();
        groupSymptonList = groupSymptonList.stream().filter(groupSympton -> groupSympton.getId()!=2).collect(Collectors.toList());

        // get symptons khong thuoc group nao
        // 2 la group , sau dua ve la 1
        List<Sympton> symptonList = symptonRepo.findById(2).get().getGroupSympton().getSymptonList();
        // Lọc triệu chứng lâm sàng
        List<Sympton> listClinicalSymptom = symptonList.stream().filter(sympton -> sympton.getType().equals("Lâm sàng")).collect(Collectors.toList());
        List<GroupSympton> listGroupClinicalSymptom = new ArrayList<>();
        List<GroupSympton> listGroupSubclinicalSymptom = new ArrayList<>();
        for (GroupSympton groupSympton: groupSymptonList) {
            for (Sympton sympton: groupSympton.getSymptonList()) {
                if(sympton.getType().equals("Lâm sàng")){
                    listGroupClinicalSymptom.add(groupSympton);
                    break;
                }
                else{
                    listGroupSubclinicalSymptom.add(groupSympton);
                    break;
                }
            }
        }
        model.addAttribute("listClinicalSymptom", listClinicalSymptom);
        model.addAttribute("listGroupClinicalSymptom", listGroupClinicalSymptom);
        // Lọc triệu chứng cận lâm sàng
        List<Sympton> listSubclinicalSymptom = symptonList.stream().filter(sympton -> sympton.getType().equals("Cận lâm sàng")).collect(Collectors.toList());
        model.addAttribute("listSubclinicalSymptom", listSubclinicalSymptom);
        model.addAttribute("listGroupSubclinicalSymptom", listGroupSubclinicalSymptom);
        return "diagnose";
    }
}
