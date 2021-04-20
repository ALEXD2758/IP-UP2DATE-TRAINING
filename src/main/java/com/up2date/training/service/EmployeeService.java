package com.up2date.training.service;

import com.up2date.training.repository.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    /**
     * Create a list with all values contained in RoleEnum
     * @return an array list of all roles present in RoleEnum
     */
    public List<RoleEnum> getListRoles() {
        return new ArrayList<RoleEnum>(Arrays.asList(RoleEnum.values()));
    }
}
