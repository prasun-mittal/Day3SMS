package com.example.day3sms.service;

import com.example.day3sms.model.StudentModel;
import com.example.day3sms.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // create
    public StudentModel addStudent(StudentModel student){
        return repository.save(student);
    }

    // display students
    public List<StudentModel> getStudents(){
        return repository.findAll();
    }

    // display students by id
    public StudentModel getStudent(String id){
        return repository.findById(id).orElse(null);
    }

    // update
    public StudentModel updateStudent(String id,StudentModel student){
        StudentModel existingStudent = repository.findById(id).orElseThrow(() -> new RuntimeException("No Student Found"));
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());
        return repository.save(existingStudent);
    }

    //delete by id

    public void deleteStudent(String id){
        repository.deleteById(id);
    }
}
