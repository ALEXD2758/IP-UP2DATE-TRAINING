package com.up2date.training.controller;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.model.TrainingModel;
import com.up2date.training.repository.RoleEnum;
import com.up2date.training.service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerITest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    private EmployeeService employeeService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

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
        employee1.setGivenName("Alexandre");
        employee1.setFamilyName("Dubois");
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

        //employee4.setEmployeeId(4);
        employee4.setFamilyName("Vandenbroucke");
        employee4.setGivenName("Frank");
        employee4.setRole(RoleEnum.USER);

        return employee4;
    }

    @Test
    public void getEmployeeListWithEmployeesShouldReturnEmployeeListView() throws Exception {
        //1. Setup
        List<EmployeeModel> employeeList = new ArrayList<>();
        employeeList.add(employeeModel1());
        employeeList.add(employeeModel2());

        //2. Act
        doReturn(employeeList)
                .when(employeeService)
                .findAllEmployees();

        mockMvc.perform(get("/employee/list"))
         //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("employee/list"))
                .andExpect(model().attributeExists("employees"))
                .andReturn();

        Assert.assertTrue(employeeList.size() == 2);
        Assert.assertFalse(employeeList.size() == 0);
        Assert.assertTrue(employeeList.get(0).getGivenName().equals("Alexandre"));
        Assert.assertTrue(employeeList.get(1).getGivenName().equals("Elon"));

    }

    @Test
    public void getEmployeeAddWShouldReturnEmployeeAddView() throws Exception {
        //1. Setup
        //2. Act

        mockMvc.perform(get("/employee/add/"))
                //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("employee/add"))
                .andExpect(model().attributeExists("employeeToCreate"))
                .andReturn();
    }

    @Test
    public void postEmployeeAddValidateWithNonExistentEmployeeShouldReturnSuccess() throws Exception {
        //1. Setup
        EmployeeModel employeeToAdd = employeeModel1();
        //2. Act
        doReturn(false)
                .when(employeeService)
                .checkEmployeeExists(employeeToAdd);

        doReturn(employeeToAdd)
                .when(employeeService)
                .saveEmployee(employeeToAdd);

        mockMvc.perform(post("/employee/add/validate")
                .flashAttr("successSaveMessage", "Employee was successfully added")
                .param("employeeId", "10")
                .param("givenName", "Barack")
                .param("familyName", "Obama")
                .param("role", RoleEnum.ADMIN.toString()))
                //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employee/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
    }

    @Test
    public void postEmployeeAddValidateWithExistentEmployeeShouldReturnEmployeeAddView() throws Exception {
        //1. Setup
        //2. Act

        doReturn(false)
                .when(employeeService)
                .checkEmployeeExists(employeeModel1());

        mockMvc.perform(post("/employee/add/validate")
                .flashAttr("ErrorEmployeeExistentMessage", "Employee already exists")
                .param("employeeId", "10")
                .param("givenName", "Barack")
                .param("familyName", "Obama")
                .param("role", RoleEnum.USER.toString()))
                //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employee/add"))
                .andExpect(flash().attributeExists("ErrorEmployeeExistentMessage"))
                .andReturn();
    }

    @Test
    public void postEmployeeAddValidateWithEmptyGivenNameParamShouldReturnEmployeeAddView() throws Exception {

        mockMvc.perform(post("/employee/add/validate")
                //.flashAttr("ErrorEmployeeExistentMessage", "Employee already exists")
                .param("employeeId", "1")
                .param("givenName", "")
                .param("familyName", "Frank")
                .param("role", "USER"))

                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("employeeToCreate"))
                .andExpect(view().name("employee/add"))
                //.andExpect(flash().attributeExists("ErrorEmployeeExistentMessage"))
                .andReturn();
    }
}