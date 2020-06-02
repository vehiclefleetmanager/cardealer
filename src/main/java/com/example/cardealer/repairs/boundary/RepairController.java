package com.example.cardealer.repairs.boundary;

import com.example.cardealer.repairs.control.RepairService;
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
@AllArgsConstructor
@RequestMapping("/repairs")
public class RepairController {

    private final RepairService repairService;
    private final CurrentUser currentUser;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public String getRepairs(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("repairs", repairService.findAllRepairs(PageRequest.of(page, 10))
                .stream().map(RepairResponse::from).collect(toList()));
        model.addAttribute("pages", repairService.findAllRepairs(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "repairs/repairs";
    }
}
