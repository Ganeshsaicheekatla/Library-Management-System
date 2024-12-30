package com.example.Book.Management.controller;

import com.example.Book.Management.dto.UserDto;
import com.example.Book.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto)throws Exception
    {
        Boolean response = userService.createUser(userDto);
        if(response){
            return  ResponseEntity.ok("User Register Successfully");
        }
        return new  ResponseEntity<>("User not register " , HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN' , 'STUDENT')")
    public ResponseEntity<UserDto> getMyProfile(@RequestHeader("Authorization") String jwtToken){
        UserDto userDto = userService.getMyProfile(jwtToken);
        return new ResponseEntity(userDto, HttpStatus.OK);
    }

    @GetMapping("/id/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<UserDto> getUserProfileById(@PathVariable Long userId)throws Exception
    {
        UserDto userDto = userService.getUserProfileById(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/update/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserDetailsById(@RequestBody UserDto userDto , @PathVariable Long userId) throws Exception {
        userService.updateUserDetails(userDto , userId);
        return ResponseEntity.ok("User with given id updated Successfully");
    }

    @DeleteMapping("/delete/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUserDetailsById(@PathVariable Long userId) throws Exception {
        userService.deleteUserDetails(userId);
        return ResponseEntity.ok("User with given id deleted Successfully");
    }

    @PutMapping("/update/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN' , 'STUDENT')")
    public ResponseEntity<String> updateUserProfile(@RequestHeader("Authorization") String jwtToken , @RequestBody UserDto userDto) throws Exception {
        Boolean result = userService.updateUserProfile(jwtToken.substring(7) , userDto);
        if (result)
            return ResponseEntity.ok("user updated successfully");

        return new ResponseEntity<>("invalid user data",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

}
