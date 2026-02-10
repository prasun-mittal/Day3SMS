package com.example.day3sms.controller;

import com.example.day3sms.dto.StudentRequestDto;
import com.example.day3sms.dto.StudentResponseDto;
import com.example.day3sms.model.StudentModel;
import com.example.day3sms.service.StudentService;
import com.example.day3sms.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    private final StudentService service;
    private final JwtUtil jwtUtil;

    public StudentController(StudentService service,JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil=jwtUtil;
    }

    public void checkToken(String authHeader){
        if(authHeader== null || !authHeader.startsWith("Bearer ")){
            throw new RuntimeException("Invalid Token");
        }

        String Token = authHeader.substring(7);
        jwtUtil.validateTokenAndGetEmail(Token);
    }


    // Create Function API

    @PostMapping("/add-student")
    public StudentResponseDto addStudent(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody StudentRequestDto student){
        checkToken(authHeader);
        return service.addStudent(student);

    }
    @GetMapping("/students")
    public List<StudentResponseDto> getStudents(
            @RequestHeader(value = "Authorization", required = false) String authHeader){
        checkToken(authHeader);
        return service.getAllStudents();
    }

//    @GetMapping("/students/{id}")
//    public StudentResponseDto getStudents(@PathVariable String id){
//        return service.getStudent(id);
//    }

    @PutMapping("/update/{id}")
    public StudentResponseDto updateStudent(@PathVariable String id,@Valid @RequestBody StudentRequestDto student){
        return service.updateStudent(id,student);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable String id){
        service.deleteStudent(id);
    }
    @PatchMapping("/patch/{id}")
    public StudentResponseDto patchStudent(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates
    ) {
        return service.patchStudent(id, updates);
    }

}
