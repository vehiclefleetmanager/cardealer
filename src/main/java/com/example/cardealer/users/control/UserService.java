
package com.example.cardealer.users.control;

import com.example.cardealer.employees.boundary.EmployeeRepository;
import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.users.boundary.CreateUserRequest;
import com.example.cardealer.users.boundary.RoleRepository;
import com.example.cardealer.users.boundary.UpdateUserPasswordRequest;
import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public String addNewUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Podany adres email " + request.getEmail() + " istnieje w systemie";
        } else {
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(request.getPassword());
            user.setActive(true);
            user.addRole(roleRepository.findByName(request.getRoles()));
            userRepository.save(user);
            return "Dodano użytkonika " + request.getFirstName() + " " + request.getLastName();
        }
    }

    public void updateUserPassword(UpdateUserPasswordRequest request) {
        userRepository.findById(request.getId()).ifPresent(
                u -> {
                    u.setPassword(passwordEncoder.encode(request.getPassword()));
                    userRepository.save(u);
                }
        );
        //TODO
        /*send message about change password (requset.getPassword()) on request.getEmail() */
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(u -> userRepository.deleteById(u.getId()));
    }


    /*public User addUser(String firstName, String lastName, String password,
                        String email, String phoneNumber, String role) {
        Role userRole = roleRepository.findByName(role.toUpperCase());
        User addUser = new User(firstName, lastName, passwordEncoder.encode(password), email, phoneNumber, userRole);
        User newUser = userRepository.save(addUser);
        addEmployee(newUser);
        return addUser;
    }*/

    private void addEmployee(User newUser) {
        Employee employee = new Employee();
        employee.setFirstName(newUser.getFirstName());
        employee.setLastName(newUser.getLastName());
        employee.setPhoneNumber(newUser.getPhoneNumber());
        employee.setAddress(newUser.getAddress());
        employee.setId(newUser.getId());
        employeeRepository.save(employee);
    }

    /*public User registerUser(UserDto userDto) {
        return userRepository.save(userMapper.reverse(userDto));
    }

    public UserDto getOwnerById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::map)
                .get();
    }

    public void updateOwner(UserDto userDto) {
        userRepository.findById(userDto.getId())
                .ifPresent(user -> {
                    user.setAddress(userDto.getAddress());
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    user.setPhoneNumber(userDto.getPhoneNumber());
                    userRepository.save(user);
                });
    }

    public List<UserDto> getOwnersDto() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<User> getOwnerByEmail(String email) {
        return userRepository.findByEmail(email);
    }*/


    /*Registration*/

    public void registrationNewAppUser(CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setActive(true);
        user.setRoles(Arrays.asList(roleRepository.findByName("CLIENT")));
        userRepository.save(user);
    }


    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
