package com.up2date.training.model;

import com.up2date.training.repository.RoleEnum;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "employees")
public class EmployeeModel {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer employeeId;

    @Enumerated(EnumType.STRING)
    @Column()
    private RoleEnum role;

    @Column(name = "given_name")
    @NotEmpty(message="Given name cannot be empty")
    @Size(min=2, max=125, message="Given name must be between 2 and 125 characters")
    private String givenName;

    @Column(name = "family_name")
    @NotEmpty(message="Family name cannot be empty")
    @Size(min=2, max=125, message="Family name must be between 2 and 125 characters")
    private String familyName;

    @Valid
    @OneToMany(fetch = FetchType.EAGER, mappedBy= "employee", cascade = CascadeType.ALL)
    //@JoinColumn(name="training_id", foreignKey = @ForeignKey(name = "trainings_ibfk_1"))
    private List<TrainingModel> training;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<TrainingModel> getTraining() {
        return training;
    }

    public void setTraining(List<TrainingModel> training) {
        this.training = training;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "employeeId=" + employeeId +
                ", role=" + role +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", training=" + training +
                '}';
    }
}