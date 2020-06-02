
package com.example.cardealer.users.boundary;

import com.example.cardealer.users.control.CurrentUser;
import com.example.cardealer.users.control.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CurrentUser currentUser;
    private final RoleRepository roleRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS')")
    @GetMapping()
    public String getSystemUsers(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("users", userService.findAllUsers(PageRequest.of(page, 10))
                .stream().map(UserResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages", userService.findAllUsers(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        model.addAttribute("roles", roleRepository.findAll());
        return "users/users";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS')")
    @PostMapping()
    public String addNewUser(@ModelAttribute("newUser") CreateUserRequest request, Model model) {
        String msg = userService.addNewUser(request);
        model.addAttribute("message", msg);
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS')")
    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("updatePassword") UpdateUserPasswordRequest request) {
        userService.updateUserPassword(request);
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS')")
    @PostMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}