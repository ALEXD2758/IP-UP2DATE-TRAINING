package com.up2date.training.model;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Transactional
@Entity
@Table(name = "trainings")
public class TrainingModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "training_id")
    private Integer trainingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "employee_id")
    private EmployeeModel employee;

    @Column(name = "employee_id")
    private Integer employeeId;

    @NotEmpty(message="Category cannot be empty")
    @Size(min=2, max=125, message="Name must be between 2 and 125 characters")
    @Column(name="category")
    private String category;

    @NotEmpty(message="Name cannot be empty")
    @Size(min=2, max=255, message="Name must be between 2 and 255 characters")
    @Column(name="name")
    private String name;

    @Future(message = "The date should be a date in the future or now")
    @NotNull(message="Start date cannot be empty")
    @Column(name="start_date")
    private LocalDate startDate;

    @Future(message = "The date should be a date in the future or now")
    @NotNull(message="End date cannot be empty")
    @Column(name="end_date")
    private LocalDate endDate;

    @NotEmpty(message="Total hours cannot be empty")
    @Column(name="total_hours")
    private String totalHours;

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public String toString() {
        return "TrainingModel{" +
                "trainingId=" + trainingId +
                ", employee=" + employee +
                ", employeeId=" + employeeId +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalHours='" + totalHours + '\'' +
                '}';
    }
}