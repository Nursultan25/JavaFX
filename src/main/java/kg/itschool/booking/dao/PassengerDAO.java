package kg.itschool.booking.dao;


import kg.itschool.booking.model.Passenger;
import kg.itschool.booking.model.enums.Role;

import java.util.List;

public interface PassengerDAO {

    int insertPassenger(Passenger passenger);
    Passenger getOneById(Long id);
    boolean isUserExistsByUserName(String username);
    boolean isUserPasswordCorrect(String username, String password);
    Role getRoleByUserName(String userName);
    List<String> getAllUserName();
    Passenger getOneByUsername(String username);
}
