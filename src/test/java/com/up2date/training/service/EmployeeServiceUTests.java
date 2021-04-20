package com.up2date.training.service;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.model.TrainingModel;
import com.up2date.training.repository.RoleEnum;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeServiceUTests {

    @Autowired
    TrainingService trainingService;

    @Autowired
    EmployeeService employeeService;

    public TrainingModel trainingModel1() {
        TrainingModel training1 = new TrainingModel();

        training1.setTrainingId(1);
        training1.setCategory("JAVA");
        training1.setName("Java SE 11");
        training1.setStartDate(LocalDate.of(2021,05,01));
        training1.setEndDate(LocalDate.of(2021,05,03));
        training1.setTotalHours("16");

        return training1;
    }

    public EmployeeModel employeeModel1() {
        EmployeeModel employee1 = new EmployeeModel();

        employee1.setEmployeeId(1);
        employee1.setFamilyName("Dubois");
        employee1.setGivenName("Alexandre");
        employee1.setRole(RoleEnum.ADMIN);

        return employee1;
    }

    @Test
    @DisplayName("getListRoles returns List<RoleEnum> with correct values")
    public void getListRolesShouldReturnListRoles() {
        List<RoleEnum> rolesList = employeeService.getListRoles();

        Assert.assertTrue(rolesList.get(1).equals(RoleEnum.ADMIN));
        Assert.assertTrue(rolesList.size() == 2);
    }
}