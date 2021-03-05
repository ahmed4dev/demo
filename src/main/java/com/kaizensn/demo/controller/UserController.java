package com.kaizensn.demo.controller;

import com.kaizensn.demo.exception.ResourceNotFoundException;
import com.kaizensn.demo.model.User;
import com.kaizensn.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/Users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/Users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long UserId)
        throws ResourceNotFoundException {
        User User = userService.findById(UserId)
          .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));
        return ResponseEntity.ok().body(User);
    }
    
    @PostMapping("/Users")
    public User createUser(@Valid @RequestBody User User) {
        return userService.save(User);
    }

    @PutMapping("/Users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long UserId,
         @Valid @RequestBody User UserDetails) throws ResourceNotFoundException {
        User User = userService.findById(UserId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));

        User.setBirthDay(UserDetails.getBirthDay());
        User.setLastName(UserDetails.getLastName());
        User.setFirstName(UserDetails.getFirstName());
        final User updatedUser = userService.save(User);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/Users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long UserId)
         throws ResourceNotFoundException {
        User User = userService.findById(UserId)
       .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + UserId));

        userService.delete(User);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}