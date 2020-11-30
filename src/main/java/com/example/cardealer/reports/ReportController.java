package com.example.cardealer.reports;

import com.example.cardealer.users.control.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final CurrentUser currentUser;

    @GetMapping()
    public String getInvoicesAmount(Model model) {
        reportService.getReportWithSevenLastDays();
        setBasicModelAttributes(model);
        return "reports/reports";
    }

    @PostMapping()
    public String getInvoiceAmount(@ModelAttribute("dateScope") DateScopeRequest request, Model model) {
        reportService.getReportWithDateScope(request.from, request.to);
        setBasicModelAttributes(model);
        return "redirect:/reports";
    }

    private void setBasicModelAttributes(Model model) {
        model.addAttribute("saleAmounts", reportService.getSaleValue());
        model.addAttribute("purchaseAmounts", reportService.getPurchaseValue());
        model.addAttribute("taxAmount", reportService.getTaxValue());
        model.addAttribute("profitAmount", reportService.getProfitValue());
        model.addAttribute("currentUser", currentUser.getUser());
    }
}
