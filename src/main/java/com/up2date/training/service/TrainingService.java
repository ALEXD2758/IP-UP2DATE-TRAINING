package com.up2date.training.service;

import com.up2date.training.model.TrainingModel;
import com.up2date.training.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRep;

    public TrainingService(TrainingRepository trainingRep) {
        this.trainingRep = trainingRep;
    }

    /**
     * Get a list of Trainings according to the specified employee ID
     * @param employeeId int of the employee ID
     * @return a List of trainings with the specified employee ID
     */
    public List<TrainingModel> findTrainingsByEmployeeId(int employeeId) {
        return trainingRep.findByEmployeeEmployeeId(employeeId);
    }
}