package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.control.AgreementService;
import com.example.cardealer.users.control.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/agreements")
@AllArgsConstructor
public class AgreementController {

    private final AgreementService agreementService;
    private final CurrentUser currentUser;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_WORKER')")
    @GetMapping
    public String getSystemAgreements(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("agreements", agreementService.findAllAgreements(PageRequest.of(page, 10))
                .stream().map(AgreementResponse::from).collect(toList()));
        model.addAttribute("pages", agreementService.findAllAgreements(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "agreements/agreements";
    }
}
