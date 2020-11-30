package com.example.cardealer.repairs.boundary;

import com.example.cardealer.config.Clock;
import com.example.cardealer.repairs.control.RepairService;
import com.example.cardealer.repairs.entity.Repair;
import com.example.cardealer.users.control.CurrentUser;
import com.example.cardealer.utils.GeneratePdfDocument;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@AllArgsConstructor
@RequestMapping("/repairs")
public class RepairController {

    private final RepairService repairService;
    private final CurrentUser currentUser;
    private final GeneratePdfDocument generatePdfDocument;
    private final Clock clock;


    @GetMapping()
    public String getRepairs(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("repairs", repairService.findAllRepairs(PageRequest.of(page, 10))
                .stream().map(RepairResponse::from).collect(toList()));
        model.addAttribute("pages", repairService.findAllRepairs(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "repairs/repairs";
    }

    @PostMapping("/search")
    public String filterRepairsList(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(value = "from", required = false) @PathVariable String from,
                                    @RequestParam(value = "to", required = false) @PathVariable String to,
                                    @RequestParam(value = "vinNumber", required = false) @PathVariable String vinNumber,
                                    @RequestParam(value = "repairAmount", required = false) @PathVariable String repairAmount, Model model) {
        model.addAttribute("repairs",
                repairService.getRepairsFormSearchButton(from, to, vinNumber, repairAmount)
                        .stream().map(RepairResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages",
                repairService.getRepairsFormSearchButton(from, to, vinNumber, repairAmount, PageRequest.of(page, 10)));
        model.addAttribute("currentUser", currentUser.getUser());
        return "repairs/repairs";
    }

    @GetMapping("/generate/{id}")
    public void getPdfOfRepair(@PathVariable("id") Long id, HttpServletResponse response) {
        Repair repair = repairService.getRepairById(id);
        String markOfCar = repair.getCar().getMark() + "_" + repair.getCar().getModel();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy_HH:mm:ss");
        String time = format.format(clock.time());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=repair_" + markOfCar + "_" + time + ".pdf";
        response.setHeader(headerKey, headerValue);
        generatePdfDocument.pdfRepairReport(response, repair);
    }
}
