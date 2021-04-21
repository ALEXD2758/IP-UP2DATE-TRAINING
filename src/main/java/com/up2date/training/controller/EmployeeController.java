package com.up2date.training.controller;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EmployeeController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * HTTP GET request to get the "employee/list" view
     * Adds attribute "employees" to the model, containing all employees available in the table "employees"
     *
     * @param model Model Interface, to add attributes to it
     * @return a string to the address "employee/list", returning the associated view
     * with the specified attribute
     */
    @GetMapping("/employee/list")
    public String employeeList(Model model) {
        model.addAttribute("employees", employeeService.findAllEmployees());
        logger.info("GET /employee/list : OK");
        return "employee/list";
    }

    /**
     * HTTP GET request to get the "employee/add" view
     * Adds attribute "employeeToCreate" to the model, containing a new EmployeeModel
     *
     * @param model Model Interface, to add attributes to it
     * @return a string to the address "employee/add", returning the associated view
     * with the specified attribute
     */
    @GetMapping("/employee/add")
    public String employeeAdd(Model model) {
        model.addAttribute("employeeToCreate", new EmployeeModel());
        logger.info("GET /employee/add : OK");
        return "employee/add";
    }

    /**
     * HTML POST request to add a new employee if it doesn't exist already and has no errors
     * Add redirect attributes messages: ErrorEmployeeExistentMessage if the employee already exists
     *                                   successSaveMessage if the employee is new
     *
     * @param employeeToCreate the EmployeeModel with annotation @Valid (for the possible constraints)
     * @param ra the RedirectAttributes to redirect attributes in redirect
     * @return a string to the address "employee/add" or "employee/list", returning the associated view, with attributes
     */
    @PostMapping("/employee/add/validate")
    public String employeeAddValidate(@Valid @ModelAttribute("employeeToCreate") EmployeeModel employeeToCreate,
                                      BindingResult result, RedirectAttributes ra) {
        if (!result.hasErrors()) {
            if (employeeService.checkEmployeeExists(employeeToCreate)) {
                logger.info("/employee/add/validate : Employee already exists");
                ra.addFlashAttribute("ErrorEmployeeExistentMessage", "Employee already exists");
                return "redirect:/employee/add";
            }
            employeeService.saveEmployee(employeeToCreate);
            ra.addFlashAttribute("successSaveMessage", "Employee was successfully added");
            return "redirect:/employee/list";
        }
        if (result.hasErrors()) {
            logger.info("POST /employee/add/validate : NOK - Request went wrong");
        }
        return "employee/add";
    }
}