package com.up2date.training.service;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.repository.EmployeeRepository;
import com.up2date.training.repository.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRep;

    public EmployeeService(EmployeeRepository employeeRep) {
        this.employeeRep = employeeRep;
    }

    /**
     * Create a list with all values contained in RoleEnum
     * @return an array list of all roles present in RoleEnum
     */
    public List<RoleEnum> getListRoles() {
        return new ArrayList<RoleEnum>(Arrays.asList(RoleEnum.values()));
    }

    /**
     * Check if an employee already exists. Search by a combination of given and family names
     * @param employee the whole EmployeeModel object
     * @return true if employee already exists, false if employee doesn't exist
     */
    public boolean checkEmployeeExists(EmployeeModel employee) {
        //TODO Implementing a better check (check with birthday included maybe)
        String givenName = employee.getGivenName();
        String familyName = employee.getFamilyName();
        return employeeRep.existsByGivenNameAndFamilyName(givenName, familyName);
    }

    /**
     * Check if an employee ID already exists.
     * @param employeeId the int of the employee ID
     * @return true if employee ID already exists, false if employee ID doesn't exist
     */
    public boolean checkEmployeeIdExists(int employeeId) {
        return employeeRep.existsById(employeeId);
    }

    /**
     * Get a list of all employees present in the DB "Up2Date" => table "employees"
     * @return list of EmployeeModel containing all employees
     */
    public List<EmployeeModel> findAllEmployees() {
        return employeeRep.findAll();
    }

    /**
     * Find an employee based on its employee ID
     * @param employeeId primitive type int
     * @return EmployeeModel containing the employee found
     */
    public EmployeeModel findEmployee(int employeeId) {
        return employeeRep.findByEmployeeId(employeeId);
    }

    /**
     * Save a new employee in the DB
     *
     * @param employee the EmployeeModel to save
     * @return EmployeeModel saved
     */
    public EmployeeModel saveEmployee(EmployeeModel employee) {
        employeeRep.save(employee);
        return employee;
    }
}