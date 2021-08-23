package kg.itschool.booking.model;

import kg.itschool.booking.model.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class Passenger {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String passportDetails;
    private Date birthDate;
    private String sex;
    private String password;
    private Role role;

}
/*
S -> Single responsibility
O -> Open-Close principle
L
I
D
 */