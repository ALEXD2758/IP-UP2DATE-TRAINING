package com.up2date.training.service;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.model.TrainingModel;
import com.up2date.training.repository.RoleEnum;
import com.up2date.training.repository.TrainingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.net.ConnectException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TrainingServiceUTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    TrainingService trainingService;

    @Autowired
    private TrainingRepository trainingRepository;

    public TrainingModel trainingModel1() {
        TrainingModel training1 = new TrainingModel();

        training1.setTrainingId(1);
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
        training3.setCategory("JAVA");
        training3.setName("Java SE 11");
        training3.setStartDate(LocalDate.of(2021,05,01));
        training3.setEndDate(LocalDate.of(2021,05,03));
        training3.setTotalHours("16");

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
    public void savePatientsToDbBeforeTests() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new FileSystemResource("src/test/resources/sqlscript_test.sql"));
        //trainingRepository.deleteAll();
        //trainingService.saveTraining(patientModel1());
        //trainingService.saveTraining(patientModel2());
    }

    @Test
    @DisplayName("findTrainingsByEmployeeId returns correct List<TrainingModel>")
    public void findTrainingsByEmployeeIdReturnsCorrectListTrainings() throws ConnectException {

        LocalDate date1 = LocalDate.of(2021,05,01);
        LocalDate date2 = LocalDate.of(2021,06,01);

        List<TrainingModel> trainingList = trainingService.findTrainingsByEmployeeId(1);

        Assert.assertTrue(trainingList.get(0).getTotalHours().equals("16"));
        Assert.assertTrue(trainingList.get(0).getName().equals("Java SE 16 - new feature"));
        Assert.assertTrue(trainingList.get(0).getStartDate().equals(date1));
        Assert.assertTrue(trainingList.get(1).getTotalHours().equals("16"));
        Assert.assertTrue(trainingList.get(1).getName().equals("Java SE 8"));
        Assert.assertTrue(trainingList.get(1).getStartDate().equals(date2));
        Assert.assertTrue(trainingList.size() == 2);
    }
}