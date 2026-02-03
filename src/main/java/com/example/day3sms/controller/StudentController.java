package com.example.day3sms.controller;

import com.example.day3sms.dto.StudentRequestDto;
import com.example.day3sms.dto.StudentResponseDto;
import com.example.day3sms.model.StudentModel;
import com.example.day3sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // Create Function API

    @PostMapping("/add-student")
    public StudentResponseDto addStudent(@Valid @RequestBody StudentRequestDto student){
        return service.addStudent(student);
    }
    @GetMapping("/students")
    public List<StudentResponseDto> getStudents(){
        return service.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public StudentResponseDto getStudents(@PathVariable String id){
        return service.getStudent(id);
    }

    @PutMapping("/update/{id}")
    public StudentResponseDto updateStudent(@PathVariable String id,@Valid @RequestBody StudentRequestDto student){
        return service.updateStudent(id,student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable String id){
        service.deleteStudent(id);
    }
}
