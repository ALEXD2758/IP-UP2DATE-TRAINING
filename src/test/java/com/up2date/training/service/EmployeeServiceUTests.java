package com.up2date.training.service;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.model.TrainingModel;
import com.up2date.training.repository.RoleEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
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

    public EmployeeModel employeeModel2() {
        EmployeeModel employee2 = new EmployeeModel();

        employee2.setEmployeeId(2);
        employee2.setFamilyName("Musk");
        employee2.setGivenName("Elon");
        employee2.setRole(RoleEnum.USER);

        return employee2;
    }

    public EmployeeModel employeeModel3() {
        EmployeeModel employee3 = new EmployeeModel();

        employee3.setEmployeeId(3);
        employee3.setFamilyName("Gates");
        employee3.setGivenName("Bill");
        employee3.setRole(RoleEnum.USER);

        return employee3;
    }

    public EmployeeModel employeeModel4() {
        EmployeeModel employee4 = new EmployeeModel();

        employee4.setEmployeeId(4);
        employee4.setFamilyName("Vandenbroucke");
        employee4.setGivenName("Frank");
        employee4.setRole(RoleEnum.USER);

        return employee4;
    }

    @Before
    public void savePatientsToDbBeforeTests() {
        employeeService.saveEmployee(employeeModel1());
        employeeService.saveEmployee(employeeModel2());
    }

    @Test
    public void findAllEmployeesReturnsAListOf2Employees() {
        List<EmployeeModel> employeeList = employeeService.findAllEmployees();

        Assert.assertTrue(employeeList.get(0).getEmployeeId() == 1);
        Assert.assertTrue(employeeList.get(0).getFamilyName().equals("Dubois"));
        Assert.assertTrue(employeeList.get(0).getGivenName().equals("Alexandre"));
        Assert.assertTrue(employeeList.get(0).getRole().equals(RoleEnum.ADMIN));
        Assert.assertTrue(employeeList.get(1).getEmployeeId() == 2);
        Assert.assertTrue(employeeList.get(1).getFamilyName().equals("Musk"));
        Assert.assertTrue(employeeList.get(1).getGivenName().equals("Elon"));
        Assert.assertTrue(employeeList.get(1).getRole().equals(RoleEnum.USER));
        Assert.assertTrue(employeeList.size() == 2);
    }

    @Test
    public void findEmployeeWithEmployeeId1ReturnsEmployeeModelWithId1() {
        EmployeeModel employeeList = employeeService.findEmployee(1);

        Assert.assertTrue(employeeList.getEmployeeId() == 1);
        Assert.assertTrue(employeeList.getFamilyName().equals("Dubois"));
        Assert.assertTrue(employeeList.getGivenName().equals("Alexandre"));
        Assert.assertTrue(employeeList.getRole().equals(RoleEnum.ADMIN));
    }

    @Test
    public void checkEmployeeExistsReturnsEmployeeModelWithId1() {
        boolean employeeExist1 = employeeService.checkEmployeeExists(employeeModel1());
        boolean employeeExist3 = employeeService.checkEmployeeExists(employeeModel3());

        Assert.assertEquals(employeeExist1, true);
        Assert.assertEquals(employeeExist3, false);
    }

    @Test
    public void getListRolesShouldReturnListRoles() {
        List<RoleEnum> rolesList = employeeService.getListRoles();

        Assert.assertTrue(rolesList.get(0).equals(RoleEnum.USER));
        Assert.assertTrue(rolesList.get(1).equals(RoleEnum.ADMIN));
        Assert.assertTrue(rolesList.size() == 2);
    }

    @Test
    public void saveEmployeeShouldSave1EmployeeModel() {
        EmployeeModel employee4 = employeeService.saveEmployee(employeeModel4());

        Assert.assertTrue(employee4.getEmployeeId() == 4);
        Assert.assertTrue(employee4.getFamilyName().equals("Vandenbroucke"));
        Assert.assertTrue(employee4.getGivenName().equals("Frank"));
        Assert.assertTrue(employee4.getRole().equals(RoleEnum.USER));
    }
}