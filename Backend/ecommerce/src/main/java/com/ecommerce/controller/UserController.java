package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Dto.UserDTO;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    private final AuthController authController;

    @Autowired
    UserService userService;

    UserController(AuthController authController, AuthService authService) {
        this.authController = authController;
        this.authService = authService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Get User By Id Error : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addUser(@RequestBody UserDTO user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) {
        try {
            System.out.println("USER TO UPDATE " + updatedUser.toString());
            userService.updateUser(id, updatedUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("UPDATE USER ERROR : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/keyword/{keyword}")
    public ResponseEntity<List<UserDTO>> searchUser(@PathVariable String keyword) {
        try {
            return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error While Searching User : " + e.getMessage());
        }
        return null;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@RequestBody UserDTO user) {
        userService.signUp(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
