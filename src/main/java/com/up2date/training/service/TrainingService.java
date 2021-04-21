package com.up2date.training.service;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.model.TrainingModel;
import com.up2date.training.repository.EmployeeRepository;
import com.up2date.training.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRep;
    private final EmployeeRepository employeeRep;

    public TrainingService(TrainingRepository trainingRep, EmployeeRepository employeeRep) {
        this.trainingRep = trainingRep;
        this.employeeRep = employeeRep;
    }

    /**
     * Get a list of Trainings according to the specified employee ID (int)
     * @param employeeId int of the employee ID
     * @return a List of trainings with the specified employee ID
     */
    public List<TrainingModel> findTrainingsByEmployeeId(int employeeId) {
        return trainingRep.findByEmployeeEmployeeId(employeeId);
    }

    /**
     * Save a new training in the DB
     * @param training the EmployeeModel to save
     * @param employeeId Integer of the employeeId to retrieve
     * @return trainingModel saved
     */
    public TrainingModel saveTraining(TrainingModel training, Integer employeeId) {
        EmployeeModel employee = employeeRep.findByEmployeeId(employeeId);
        training.setEmployee(employee);
        trainingRep.save(training);
        return training;
    }
}