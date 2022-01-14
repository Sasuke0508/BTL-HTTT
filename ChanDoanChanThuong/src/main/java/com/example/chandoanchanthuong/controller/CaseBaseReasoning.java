package com.example.chandoanchanthuong.controller;

import com.example.chandoanchanthuong.entity.*;
import com.example.chandoanchanthuong.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;
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
    private DiseaseDefingSymptonRepo diseaseDefingSymptonRepo;

    @Autowired
    private CaseRepo caseRepo;

    @Autowired
    private DiseaseRepo diseaseRepo;

    @Autowired
    private MedicalHistoryRepo medicalHistoryRepo;

    @PostMapping("")
    public String handleDiagnose(HttpServletRequest request, HttpServletResponse response, Model model) {

        // Get list loại chấn thương bệnh nhân gặp phải
        String[] types = request.getParameterValues("vung-chan-thuong");
        List<String> typeList = new ArrayList<>();
        if (types != null) {
            typeList = Arrays.stream(types).collect(Collectors.toList());
        }

        // Get list thuộc tính được người dùng chọn
        String[] medicalHistories = request.getParameterValues("benh-su");
        List<String> medicalHistoryList = new ArrayList<>();
        if (medicalHistories != null) {
            medicalHistoryList = Arrays.stream(medicalHistories).collect(Collectors.toList());
        }
        String[] reasons = request.getParameterValues("nguyen-nhan");
        List<String> reasonList = new ArrayList<>();
        if (reasons != null) {
            reasonList = Arrays.stream(reasons).collect(Collectors.toList());
        }
        String[] symptons = request.getParameterValues("trieu-chung");
        List<String> symptonList = new ArrayList<>();
        if (symptons != null) {
            symptonList = Arrays.stream(symptons).collect(Collectors.toList());
        }

        System.out.println("===================================== Bệnh sử ==================================");
        medicalHistoryList.forEach(System.out::println);
        System.out.println("===================================== Nguyên nhân ==================================");
        reasonList.forEach(System.out::println);
        System.out.println("===================================== Triệu chứng ==================================");
        symptonList.forEach(System.out::println);

        // Tính tổng trọng số của case người dùng nhập vào
        double sumOfWeight = 0;
        List<Reason> listReasonInData = reasonRepo.findAll();
        List<MedicalHistory> listMedicalHistoryInData = medicalHistoryRepo.findAll();
        List<DiseaseDefingSympton> listDiseaseDefingSymptonInData = diseaseDefingSymptonRepo.findAll();

        // Định nghĩa các List thuộc tính được chọn để chút nữa đẩy sang KQ
        List<Reason> listReasonResult = new ArrayList<>();
        List<MedicalHistory> listMedicalHistoryResult = new ArrayList<>();
        List<DiseaseDefingSympton> listDDSResult = new ArrayList<>();

        for (String s : reasonList) {
            for (Reason reason : listReasonInData) {
                if (s.equals(reason.getId() + "")) {
                    sumOfWeight += reason.getWeight();
                    listReasonResult.add(reason);
                    break;
                }
            }
        }
        for (String s : medicalHistoryList) {
            for (MedicalHistory medicalHistory : listMedicalHistoryInData) {
                if (s.equals(medicalHistory.getId() + "")) {
                    sumOfWeight += medicalHistory.getWeight();
                    listMedicalHistoryResult.add(medicalHistory);
                    break;
                }
            }
        }
        for (String s : symptonList) {
            for (DiseaseDefingSympton diseaseDefingSympton : listDiseaseDefingSymptonInData) {
                if (s.equals(diseaseDefingSympton.getId() + "")) {
                    sumOfWeight += diseaseDefingSympton.getWeight();
                    listDDSResult.add(diseaseDefingSympton);
                    break;
                }
            }
        }

        // Get list case có trong hệ thống
        List<Case> listCase = caseRepo.findAll();
        listCase.forEach(aCase -> System.out.println(aCase.getId()));
        Map<String, Double> listCaseInSystem = new TreeMap<String, Double>();
        if (typeList.size() > 0) {
            // Get list case by list type of disease
            for (Case aCase : listCase) {
                if (checkTypeDiseaseOfCase(typeList, aCase)) {
                    listCaseInSystem.put(aCase.getId()+"", Double.valueOf(0.0));
                }
            }
        } else {
            for (Case aCase : listCase) {
                listCaseInSystem.put(aCase.getId()+"", Double.valueOf(0.0));
            }
        }

        // So sánh
        double finalSumOfWeight = sumOfWeight;
        List<String> finalReasonList = reasonList;
        List<String> finalMedicalHistoryList = medicalHistoryList;
        List<String> finalSymptonList = symptonList;
        listCaseInSystem.forEach((idCase, aDouble) -> listCaseInSystem.put(idCase, cbr(caseRepo.getById(Integer.valueOf(idCase)), finalReasonList, finalMedicalHistoryList, finalSymptonList, finalSumOfWeight)));
        // Kiểm tra và kết luận
        Map<String, Double> mapResult = new HashMap<>();
        Set<String> listCaseSystem = listCaseInSystem.keySet();
        //
        double maxCompare = 0;
        for (String key : listCaseSystem) {
            // Nếu độ tương đồng lớn nhất nhỏ hơn 40% thì break
               if( maxCompare < listCaseInSystem.get(key)){
                   maxCompare = listCaseInSystem.get(key);
               }
        }

        if (maxCompare < 0.4) {
            //Thông báo không xác định được bệnh
            model.addAttribute("error", "Không thể xác định được bệnh!");
            model.addAttribute("typeOfDisease", typeList);
            model.addAttribute("listReason", listReasonResult);
            model.addAttribute("listMedicalHistory", listMedicalHistoryResult);
            model.addAttribute("listDDS", listDDSResult);
            return "result";
        }
        // Lấy ra tất cae case case có cùng độ tương đồng cao nhất
        for (String key : listCaseSystem) {
            System.out.println(listCaseInSystem.get(key));
            if (listCaseInSystem.get(key) == maxCompare) {
                mapResult.put(key, maxCompare);
            }
        }
        // Lấy ra tất cả các bệnh
        Set<Disease> output = new HashSet<>();
        mapResult.forEach((aCase, aDouble) -> caseRepo.getById(Integer.valueOf(aCase)).getDiseaseList().forEach(disease -> output.add(disease)));
        if (output.size() > 2 || output.size() == 0) {
            //Thông báo không xác định được bệnh
            model.addAttribute("error", "Không thể xác định được bệnh!");
        } else {
            List<Disease> listResult = output.stream().collect(Collectors.toList());
            Case newCase = new Case();
            newCase.setCreateDate(new Date(new Date().getTime()));
            newCase.setDiseaseDefingSymptonList(listDDSResult);
            newCase.setReasonList(listReasonResult);
            newCase.setDiseaseList(listResult);
            newCase.setMedicalHistoryList(listMedicalHistoryResult);
            System.out.println("Bệnh: " + listResult.get(0).getName() + " " + maxCompare*100 + "%");
            System.out.println("Tổng trọng số của case: " + sumOfWeight);
            newCase.setId((int)caseRepo.count()+ 1);
            caseRepo.save(newCase);

            model.addAttribute("listResult", listResult);
            model.addAttribute("compare", 100*Math.round(maxCompare * 100) / 100);
//            Nếu chỉ có 1 bệnh thì kiểm tra xem bệnh đó có phân chia mức độ không?
            if (listResult.size() == 1 && listResult.get(0).getDiseaseLevels()!=null) {
                model.addAttribute("diseaseLevelList", listResult.get(0).getDiseaseLevels());
                System.out.println("Bệnh: " + listResult.get(0).getName());
            }
        }

        // Set list thuoc tinh nguoi dung chon
        model.addAttribute("typeOfDisease", typeList);
        model.addAttribute("listReason", listReasonResult);
        model.addAttribute("listMedicalHistory", listMedicalHistoryResult);
        model.addAttribute("listDDS", listDDSResult);
        return "result";
    }

    private boolean checkTypeDiseaseOfCase(List<String> listType, Case aCase) {
        for (String type : listType) {
            if (aCase.getDiseaseList().stream().anyMatch(disease -> disease.getType().equals(type))) {
                return true;
            }
        }
        return false;
    }

    private double cbr(Case caseInSystem, List<String> reasonList, List<String> medicalHistoryList, List<String> diseaseDefingSymptonList, double sumOfWeight) {
        List<Reason> reasonListInSystem = caseInSystem.getReasonList();
        List<MedicalHistory> medicalHistoryListInSystem = caseInSystem.getMedicalHistoryList();
        List<DiseaseDefingSympton> diseaseDefingSymptonListInSystem = caseInSystem.getDiseaseDefingSymptonList();

        double res = 0;
        for(String reason : reasonList){
            for(Reason reasonCompare: reasonListInSystem){
                if(reason.equals(reasonCompare.getId()+"")){
                    res+=reasonCompare.getWeight();
                    break;
                }
            }
        }

        for(String medicalHistory : medicalHistoryList){
            for(MedicalHistory medicalHistory1: medicalHistoryListInSystem){
                if(medicalHistory.equals(medicalHistory1.getId()+"")){
                    res+=medicalHistory1.getWeight();
                    break;
                }
            }
        }

        for(String dds : diseaseDefingSymptonList){
            for(DiseaseDefingSympton dds1 : diseaseDefingSymptonListInSystem){
                if(dds.equals(dds1.getId()+"")){
                    res+=dds1.getWeight();
                    break;
                }
            }
        }
//        res = res + reasonListInSystem.stream().filter(reason -> reasonList.stream().anyMatch(reasonCompare -> reasonCompare.equals(reason.getId() + ""))).mapToDouble(Reason::getWeight).sum();
//        res = res + medicalHistoryListInSystem.stream().filter(medicalHistory -> medicalHistoryList.stream().anyMatch(medicalHistoryCompare -> medicalHistoryCompare.equals(medicalHistory.getId() + ""))).mapToDouble(MedicalHistory::getWeight).sum();
//        res = res + diseaseDefingSymptonListInSystem.stream().filter(ddSympton -> diseaseDefingSymptonList.stream().anyMatch(ddSymptonCompare -> ddSymptonCompare.equals(ddSympton.getId() + ""))).mapToDouble(DiseaseDefingSympton::getWeight).sum();
        System.out.println("Case " + caseInSystem.getId() + " tương đồng " + res);
        caseInSystem.getReasonList().forEach(reason -> System.out.println(reason.getName()));
        return res / sumOfWeight;
    }

    @GetMapping("")
    public String showInputPage(Model model) {
        // Get list bệnh sử
        List<MedicalHistory> listMedicalHistory = medicalHistoryRepo.findAll();
        model.addAttribute("listMedicalHistory", listMedicalHistory);
        // Get list nguyên nhân
        List<Reason> listReason = reasonRepo.findAll();
        listReason = listReason.stream().sorted(Comparator.comparingInt(o -> o.getName().length())).collect(Collectors.toList());
        model.addAttribute("listReason", listReason);
        // Get list triệu chứng

        // get All group sympton
        List<GroupSympton> groupSymptonList = groupSymptonRepo.findAll();
        groupSymptonList = groupSymptonList.stream().filter(groupSympton -> !groupSympton.getName().equals("NoName")).collect(Collectors.toList());

        // get symptons khong thuoc group nao
        // 2 la group , sau dua ve la 1
        List<Sympton> symptonList = groupSymptonRepo.findById(1).get().getSymptonList();
        // Lọc triệu chứng lâm sàng
        List<Sympton> listClinicalSymptom = symptonList.stream().filter(sympton -> sympton.getType().equals("LS")).collect(Collectors.toList());
        List<GroupSympton> listGroupClinicalSymptom = new ArrayList<>();
        List<GroupSympton> listGroupSubclinicalSymptom = new ArrayList<>();
        for (GroupSympton groupSympton : groupSymptonList) {
            if (!groupSympton.getName().equals("NoName")) {
                for (Sympton sympton : groupSympton.getSymptonList()) {
                    if(sympton.getTypeOfSympton().equals("DiseaseDefingSympton")){
                        if (sympton.getType().equals("LS")) {
                            listGroupClinicalSymptom.add(groupSympton);
                            break;
                        } else {
                            listGroupSubclinicalSymptom.add(groupSympton);
                            break;
                        }
                    }
                }
            }
        }
        listClinicalSymptom = listClinicalSymptom.stream().sorted(Comparator.comparingInt(o -> o.getName().length())).collect(Collectors.toList());
        model.addAttribute("listClinicalSymptom", listClinicalSymptom);
        listGroupClinicalSymptom.forEach(groupSympton -> groupSympton.setSymptonList(groupSympton.getSymptonList().stream().sorted(Comparator.comparingInt(o -> o.getName().length())).collect(Collectors.toList())));
        model.addAttribute("listGroupClinicalSymptom", listGroupClinicalSymptom);

        // Lọc triệu chứng cận lâm sàng

        List<Sympton> listSubclinicalSymptom = symptonList.stream().filter(sympton -> sympton.getType().equals("CLS") && sympton.getTypeOfSympton().equals("DiseaseDefingSympton")).collect(Collectors.toList());
        listSubclinicalSymptom = listSubclinicalSymptom.stream().sorted(Comparator.comparingInt(o -> o.getName().length())).collect(Collectors.toList());
        listGroupSubclinicalSymptom.forEach(groupSympton -> groupSympton.setSymptonList(groupSympton.getSymptonList().stream().sorted(Comparator.comparingInt(o -> o.getName().length())).collect(Collectors.toList())));
        model.addAttribute("listSubclinicalSymptom", listSubclinicalSymptom);
        model.addAttribute("listGroupSubclinicalSymptom", listGroupSubclinicalSymptom);
        return "diagnose";
    }

}
