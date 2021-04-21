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
public class TrainingServiceUTests {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private EmployeeService employeeService;

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

    @Before
    public void savePatientsToDbBeforeTests() {
        employeeService.saveEmployee(employeeModel1());
    }

    @Test
    public void save3TrainingsShouldSave3TrainingModel() {
        TrainingModel training1 = trainingService.saveTraining(trainingModel1(), 1);
        TrainingModel training2 = trainingService.saveTraining(trainingModel2(), 1);
        TrainingModel training3 = trainingService.saveTraining(trainingModel3(), 1);

        Assert.assertTrue(training1.getTotalHours().equals("16"));
        Assert.assertTrue(training2.getName().equals("Java SE 8"));
        Assert.assertTrue(training3.getTotalHours().equals("8"));
    }

    @Test
    public void findTrainingsByEmployeeIdReturnsCorrectListTrainings() {
        LocalDate date1 = LocalDate.of(2021,05,01);
        LocalDate date2 = LocalDate.of(2021,06,01);

        List<TrainingModel> trainingList = trainingService.findTrainingsByEmployeeId(1);

        Assert.assertTrue(trainingList.get(0).getTotalHours().equals("16"));
        Assert.assertTrue(trainingList.get(0).getName().equals("Java SE 16 - new feature"));
        Assert.assertTrue(trainingList.get(0).getStartDate().equals(date1));
        Assert.assertTrue(trainingList.get(1).getTotalHours().equals("16"));
        Assert.assertTrue(trainingList.get(1).getName().equals("Java SE 8"));
        Assert.assertTrue(trainingList.get(1).getStartDate().equals(date2));
        Assert.assertTrue(trainingList.size() == 3);
    }
}