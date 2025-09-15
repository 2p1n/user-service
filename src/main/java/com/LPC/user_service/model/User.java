package com.LPC.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;


@Entity //for hibernate
@Table(name = "users") //table for db
@Data
//@AllArgsConstructor //constructor with all arguments
                    // !don't use it on entities with @GeneratedValue
@NoArgsConstructor //default constructor + needed by JPA



// create new schema in db + learn pgadmin + normal forms
public class User
{
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    @Transient// no need for a column in db, calculate it instead
    private Integer age;


    public User(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }


    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
