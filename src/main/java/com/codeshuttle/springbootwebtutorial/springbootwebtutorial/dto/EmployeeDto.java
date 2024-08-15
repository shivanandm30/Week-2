package com.codeshuttle.springbootwebtutorial.springbootwebtutorial.dto;

import com.codeshuttle.springbootwebtutorial.springbootwebtutorial.anotation.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private long id;

    @NotBlank(message = "Require field should not be blank")
    @Size(min = 3,max = 10, message = "Number of the charactor should be in the range of [3,10]")
    private String name;

    @NotBlank(message = "Employee email should be filled")
    @Email(message = "Email should be valid email")
    private String email;


    @NotNull(message = "age is mandatory")
    @Max(value = 60, message = "Age of the employee cannot greater then 60")
    @Min(value = 18, message = "Minimum age should be 18")
    private Integer age;

    @NotBlank(message = "Employee role can not be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Employee role should be USER or ADMIN")
    @EmployeeRoleValidation
    private String role;

    @NotNull
    @Positive
    @Digits(integer = 6,fraction = 2, message = "6figure")
    @DecimalMin(value = "100.50")
    @DecimalMax(value = "1000.99")
    private double salary;

    @PastOrPresent(message = "date of joining of the employee should be in present and past")
    private LocalDate dateofjoining;

    @AssertTrue(message = "User should be active")
    @JsonProperty("isActive")
    private Boolean isActive;


}
