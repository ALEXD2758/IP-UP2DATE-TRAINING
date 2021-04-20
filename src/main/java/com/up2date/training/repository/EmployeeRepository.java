package com.up2date.training.repository;

import com.up2date.training.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {
    List<EmployeeModel> findAll();

    EmployeeModel findByEmployeeId(int id);

    boolean existsById(int id);

}