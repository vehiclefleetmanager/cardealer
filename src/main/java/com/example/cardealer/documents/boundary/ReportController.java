package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.control.InvoiceService;
import com.example.cardealer.users.control.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final InvoiceService invoiceService;
    private final CurrentUser currentUser;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS')")
    @GetMapping()
    public String getInvoicesAmount(Model model) {
        invoiceService.getReportWithSevenLastDays();
        setBasicModelAttributes(model);
        return "reports/reports";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS')")
    @PostMapping()
    public String getInvoiceAmount(@ModelAttribute("dateScope") DateScopeRequest request, Model model) {
        invoiceService.getReportWithDateScope(request.from, request.to);
        setBasicModelAttributes(model);
        return "redirect:/reports";
    }

    private void setBasicModelAttributes(Model model) {
        model.addAttribute("saleAmounts", invoiceService.getSaleValue());
        model.addAttribute("purchaseAmounts", invoiceService.getPurchaseValue());
        model.addAttribute("taxAmount", invoiceService.getTaxValue());
        model.addAttribute("profitAmount", invoiceService.getProfitValue());
        model.addAttribute("currentUser", currentUser.getUser());
    }
}
