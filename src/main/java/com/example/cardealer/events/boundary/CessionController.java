/*package com.example.cardealer.events.boundary;

import com.example.cardealer.events.control.CessionService;
import com.example.cardealer.users.control.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/cessions")
@AllArgsConstructor
public class CessionController {
    private final CessionService cessionService;
    private final CurrentUser currentUser;


    @GetMapping()
    public String getSystemCessions(@RequestParam(defaultValue = "0") int page, Model model) {
        if (currentUser.getUser().getRoles().stream().anyMatch(role -> role.getName().matches("CLIENT"))) {
            model.addAttribute("cessions", cessionService.findAllCessionsOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10))
                    .stream().map(CessionResponse::from).collect(toList()));
            model.addAttribute("pages", cessionService.findAllCessionsOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10)));
        }else {
            model.addAttribute("cessions", cessionService.findAllCessions(PageRequest.of(page, 10))
                    .stream().map(CessionResponse::from).collect(toList()));
            model.addAttribute("pages", cessionService.findAllCessions(PageRequest.of(page, 10)));
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "cessions/cessions";
    }
}*/
