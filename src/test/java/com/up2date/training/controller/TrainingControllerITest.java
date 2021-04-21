package com.up2date.training.controller;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.model.TrainingModel;
import com.up2date.training.repository.RoleEnum;
import com.up2date.training.service.EmployeeService;
import com.up2date.training.service.TrainingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainingControllerITest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    private TrainingService trainingService;

    @MockBean
    private EmployeeService employeeService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    public TrainingModel trainingModel1() {
        TrainingModel training1 = new TrainingModel();

        training1.setTrainingId(1);
        training1.setEmployee(employeeModel1());
        training1.setCategory("JAVA");
        training1.setName("Java SE 16 - new feature");
        training1.setStartDate(LocalDate.of(2021,05,01));
        training1.setEndDate(LocalDate.of(2021,05,02));
        training1.setTotalHours("16");

        return training1;
    }

    public TrainingModel trainingModel2() {
        TrainingModel training2 = new TrainingModel();

        training2.setTrainingId(2);
        training2.setEmployee(employeeModel1());
        training2.setCategory("JAVA");
        training2.setName("Java SE 8");
        training2.setStartDate(LocalDate.of(2021,06,01));
        training2.setEndDate(LocalDate.of(2021,06,02));
        training2.setTotalHours("16");

        return training2;
    }

    public TrainingModel trainingModel3() {
        TrainingModel training3 = new TrainingModel();

        training3.setTrainingId(3);
        training3.setEmployee(employeeModel1());
        training3.setCategory("JAVA");
        training3.setName("Java SE 11");
        training3.setStartDate(LocalDate.of(2021,05,01));
        training3.setEndDate(LocalDate.of(2021,05,03));
        training3.setTotalHours("8");

        return training3;
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
    public void getTrainingListForEmployeeShouldReturnEmployeeListViewWithTrainings() throws Exception {
        //1. Setup
        List<TrainingModel> trainingList = new ArrayList<>();
        trainingList.add(trainingModel1());
        trainingList.add(trainingModel2());

        //2. Act
        doReturn(trainingList)
                .when(trainingService)
                .findTrainingsByEmployeeId(1);

        mockMvc.perform(get("/training/list/{employeeId}", "1"))
                //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("training/list"))
                .andExpect(model().attributeExists("trainings"))
                .andReturn();

        assertTrue(trainingList.get(0).getName().equals("Java SE 16 - new feature"));
        assertTrue(trainingList.get(1).getName().equals("Java SE 8"));
    }

    @Test
    public void getTrainingAddWShouldReturnTrainingAddView() throws Exception {
        //1. Setup
        //2. Act

        mockMvc.perform(get("/training/add/{employeeId}","1"))
        //3. Assert
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("training/add"))
                .andExpect(model().attributeExists("trainingToCreate"))
                .andReturn();
    }

    @Test
    public void getTrainingAddWShouldReturnClientError() throws Exception {
        //1. Setup
        //2. Act
        mockMvc.perform(get("/training/add/{employeeId}","$"))
        //3. Assert
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void postTrainingAddValidateWithExistentEmployeeIdShouldReturnSuccess() throws Exception {
        //1. Setup
        //2. Act
        doReturn(true)
                .when(employeeService)
                .checkEmployeeIdExists(1);

        doReturn(trainingModel1())
                .when(trainingService)
                .saveTraining(trainingModel1(), 1);

        mockMvc.perform(post("/training/add/validate/{employeeId}", "1")
                .flashAttr("successSaveMessage", "Training was successfully added")
                .param("trainingId", "1")
                .param("employeeId", "1")
                .param("category", "JAVA")
                .param("name", "Java SE 16 - new feature")
                .param("startDate", LocalDate.of(2021,05,01).toString())
                .param("endDate", LocalDate.of(2021,05,02).toString())
                .param("totalHours", "16"))
                //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/training/list/{employeeId}"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
    }

    @Test
    public void postTrainingAddValidateWithNonExistentEmployeeIdShouldReturnErrorMessage() throws Exception {
        //1. Setup
        //2. Act
        doReturn(false)
                .when(employeeService)
                .checkEmployeeIdExists(1);

        mockMvc.perform(post("/training/add/validate/{employeeId}", "1")
                .flashAttr("ErrorEmployeeNonExistentMessage", "Employee doesn't exist")
                .param("trainingId", "1")
                .param("employeeId", "1")
                .param("category", "JAVA")
                .param("name", "Java SE 16 - new feature")
                .param("startDate", LocalDate.of(2021,05,01).toString())
                .param("endDate", LocalDate.of(2021,05,02).toString())
                .param("totalHours", "16"))
                //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/training/add/{employeeId}"))
                .andExpect(flash().attributeExists("ErrorEmployeeNonExistentMessage"))
                .andReturn();
    }

    @Test
    public void postTrainingAddValidateWithInvalidParamShouldReturnBindingResultErrors() throws Exception {
        //1. Setup
        TrainingModel trainingToCreate = trainingModel1();
        //2. Act
        doReturn(true)
                .when(employeeService)
                .checkEmployeeIdExists(1);

        mockMvc.perform(post("/training/add/validate/{employeeId}", "1")
                .flashAttr("trainingToCreate", trainingToCreate)
                .param("trainingId", "1")
                .param("employeeId", "1")
                .param("category", "")
                .param("name", "J")
                .param("startDate", LocalDate.of(2021,05,01).toString())
                .param("endDate", LocalDate.of(2021,05,02).toString())
                .param("totalHours", "16"))
                //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/training/add/{employeeId}"))
                .andExpect(flash().attributeExists("trainingToCreate"))
                .andReturn();
    }
}