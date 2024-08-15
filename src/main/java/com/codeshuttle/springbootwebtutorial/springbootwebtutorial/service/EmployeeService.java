package com.codeshuttle.springbootwebtutorial.springbootwebtutorial.service;

import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.configs.MapperConfig;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDto;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.exception.ResourceNotFoundException;
import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDto> getEmployeeById(Long id) {
//        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
//        return modelMapper.map(employeeEntity, EmployeeDto.class);
        return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class));
    }

    public List<EmployeeDto> getAllEmployee() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class)).collect(Collectors.toList());
    }

    public EmployeeDto createNewEmployee(EmployeeDto inputEmployee) {
        EmployeeEntity tosaveEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(tosaveEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDto.class);
    }

    public EmployeeDto updateEmployeeById(Long employeeId, EmployeeDto employeeDto) {
        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity =employeeRepository.save(employeeEntity);
        return  modelMapper.map(savedEmployeeEntity, EmployeeDto.class);
    }

    private void isExistsByEmployeeId(Long employeeId) {
        boolean exist = employeeRepository.existsById(employeeId);
        if (!exist) throw new ResourceNotFoundException("Employee not found with ID : "+employeeId);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        isExistsByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDto updatePartialEMployeeById(Long employeeId, Map<String, Objects> updates) {
        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field, value) ->{
            Field fieldToBeUpdated=ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDto.class);
    }


}
