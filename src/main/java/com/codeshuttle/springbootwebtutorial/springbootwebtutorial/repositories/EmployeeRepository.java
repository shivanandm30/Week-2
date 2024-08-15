package com.codeshuttle.springbootwebtutorial.springbootwebtutorial.repositories;

import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository <EmployeeEntity,Long> {

}
