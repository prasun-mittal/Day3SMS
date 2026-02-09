package com.example.day3sms.service;

import com.example.day3sms.dto.StudentRequestDto;
import com.example.day3sms.dto.StudentResponseDto;
import com.example.day3sms.exception.StudentNotFoundException;
import com.example.day3sms.model.StudentModel;
import com.example.day3sms.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public StudentResponseDto addStudent(StudentRequestDto dto) {
        StudentModel student = new StudentModel();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setEmail(dto.getEmail());

        StudentModel saved = repository.save(student);

        return new StudentResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getAge(),
                saved.getEmail()
        );
    }

    // READ ALL
    public List<StudentResponseDto> getAllStudents() {
        return repository.findAll()
                .stream()
                .map(s -> new StudentResponseDto(
                        s.getId(),
                        s.getName(),
                        s.getAge(),
                        s.getEmail()
                ))
                .toList();
    }

    // READ BY ID
    public StudentResponseDto getStudent(String id) {
        StudentModel student = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id: " + id));

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getEmail()
        );
    }

    // UPDATE (PUT)
    public StudentResponseDto updateStudent(String id, StudentRequestDto student) {
        StudentModel existingStudent = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id: " + id));

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());

        StudentModel updated = repository.save(existingStudent);

        return new StudentResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getAge(),
                updated.getEmail()
        );
    }

    // DELETE
    public void deleteStudent(String id) {
        repository.deleteById(id);
    }

    // PATCH (FINAL & CORRECT)
    public StudentResponseDto patchStudent(String id, Map<String, Object> updates) {

        StudentModel student = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found"));

        if (updates.containsKey("name")) {
            student.setName((String) updates.get("name"));
        }

        if (updates.containsKey("age")) {
            Object ageObj = updates.get("age");
            if (ageObj instanceof Number) {
                student.setAge(((Number) ageObj).intValue());
            }
        }

        if (updates.containsKey("email")) {
            student.setEmail((String) updates.get("email"));
        }

        StudentModel updated = repository.save(student);

        return new StudentResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getAge(),
                updated.getEmail()
        );
    }
}
