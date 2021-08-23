package kg.itschool.booking.service;


import kg.itschool.booking.dto.AuthorizationDetails;
import kg.itschool.booking.model.Passenger;
import kg.itschool.booking.model.enums.Role;

import java.util.List;

public interface PassengerService {

    Passenger getOneById(Long id);
    void addPassenger(Passenger passenger);
    void checkPassengerData(AuthorizationDetails details);
    Role getRoleByUserName(String userName);
    List<String> getAllUserNames();
    Passenger getOneByUsername(String username);
}
