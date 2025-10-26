package com.lpc.user.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

//info about lombok https://projectlombok.org/features/
@Entity //for hibernate
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email") //better to handle integrity check on
                // database level to avoid problems with possible duplicates on async requests
        }
) //table for db
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

    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 2,max = 30, message = "Given name's length must be between 2 and 30 characters!")
    private String name;

    @Email(message = "Email has to be valid!")
    @NotBlank
    private String email;

    @NotNull(message = "Date of birth is required!")
    private LocalDate dateOfBirth;

    @Transient// no need for a column in db, calculate it instead
    private Integer age;

    private String filePath;


    public User(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
    public User(String name, String email, LocalDate dateOfBirth, String filePath) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.filePath = filePath;
    }



    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }



}
