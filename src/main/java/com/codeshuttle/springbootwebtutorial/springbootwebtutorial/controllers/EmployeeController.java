package com.codeshuttle.springbootwebtutorial.springbootwebtutorial.controllers;

import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDto;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.exception.ResourceNotFoundException;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "employeeId") Long id){
       // return employeeService.getEmployeeById(id);
        Optional<EmployeeDto> employeeDto = employeeService.getEmployeeById(id);
        return employeeDto
                .map(employeeDto1 -> ResponseEntity.ok(employeeDto1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID : "+ id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(@RequestParam(required = false, name = "inputAge") Integer age, @RequestParam(required = false) String sortby){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }
    @PostMapping
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody @Valid EmployeeDto inputEmployee){
        EmployeeDto savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    @PutMapping(path = "{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@RequestBody EmployeeDto employeeDto, @PathVariable Long employeeId){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDto));
    }
    @DeleteMapping (path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        //return  employeeService.deleteEmployeeById(employeeId);
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }
    @PatchMapping (path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updatePartialEMployeeById(@RequestBody Map<String, Objects> updates, @PathVariable Long employeeId){
        EmployeeDto employeeDto = employeeService.updatePartialEMployeeById(employeeId,updates);
        if (employeeDto==null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDto);
    }
}
