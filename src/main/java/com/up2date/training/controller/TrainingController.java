package com.up2date.training.controller;

import com.up2date.training.model.EmployeeModel;
import com.up2date.training.model.TrainingModel;
import com.up2date.training.service.EmployeeService;
import com.up2date.training.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TrainingController {

    private final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    private final TrainingService trainingService;
    private final EmployeeService employeeService;

    public TrainingController(TrainingService trainingService, EmployeeService employeeService) {
        this.trainingService = trainingService;
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
    @GetMapping("/training/list/{employeeId}")
    public String trainingList(@PathVariable("employeeId") Integer employeeId, Model model, RedirectAttributes ra) {
        model.addAttribute("trainings", trainingService.findTrainingsByEmployeeId(employeeId));
        logger.info("GET /training/list : OK");
        return "training/list";
    }

    /**
     * HTTP GET request to get the "employee/add" view
     * Adds attribute "employees" to the model, containing all employees available in the table "employees"
     *
     * @param model Model Interface, to add attributes to it
     * @return a string to the address "employee/list", returning the associated view
     * with the specified attribute
     */
    @GetMapping("/training/add/{employeeId}")
    public String trainingAdd(@PathVariable("employeeId") Integer employeeId, final Model model) {
        TrainingModel newTrainingModel = new TrainingModel();

        if(!model.containsAttribute("trainingToCreate")) {
            model.addAttribute("trainingToCreate", newTrainingModel);
        }

        //model.addAttribute("trainingToCreate", new TrainingModel());
        logger.info("GET /training/add : OK");
        return "training/add";
    }

    @PostMapping("/training/add/validate/{employeeId}")
    public String trainingAddValidate(@PathVariable("employeeId") Integer employeeId,
                                      @Valid @ModelAttribute("trainingToCreate") final TrainingModel trainingToCreate,
                                      final BindingResult result, final RedirectAttributes ra) {
        if (!result.hasErrors()) {
            EmployeeModel employee = employeeService.findEmployee(employeeId);
            if (employeeService.checkEmployeeExists(employee) == false) {
                logger.info("/employee/add/validate : Employee doesn't exist");
                ra.addFlashAttribute("ErrorEmployeeNonExistentMessage", "Employee doesn't exist");
                return "redirect:/training/add/{employeeId}";
            }
            trainingService.saveTraining(trainingToCreate, employeeId);
            ra.addFlashAttribute("successSaveMessage", "Training was successfully added");
            return "redirect:/training/list/{employeeId}";
        }
        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.trainingToCreate", result);
            ra.addFlashAttribute("trainingToCreate", trainingToCreate);
            logger.info("POST /employee/add/validate : NOK - Request went wrong");
        }
        return "redirect:/training/add/{employeeId}";
    }
}