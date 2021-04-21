package com.up2date.training.repository;

import com.up2date.training.model.TrainingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<TrainingModel, Integer> {

    List<TrainingModel> findByEmployeeEmployeeId(int ownerId);
}