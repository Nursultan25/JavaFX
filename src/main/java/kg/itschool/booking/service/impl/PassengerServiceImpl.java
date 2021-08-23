package kg.itschool.booking.service.impl;

import kg.itschool.booking.dao.BaseDAO;
import kg.itschool.booking.dao.PassengerDAO;
import kg.itschool.booking.dto.AuthorizationDetails;
import kg.itschool.booking.exceptions.IncorrectPasswordException;
import kg.itschool.booking.exceptions.IncorrectUserNameException;
import kg.itschool.booking.exceptions.NegativeIdException;
import kg.itschool.booking.exceptions.PassengerNotFoundException;
import kg.itschool.booking.model.Passenger;
import kg.itschool.booking.model.enums.Role;
import kg.itschool.booking.service.PassengerService;

import java.util.List;

public class PassengerServiceImpl implements PassengerService { // class for Logic

    private PassengerDAO passengerDAO;

    public PassengerServiceImpl() {
        passengerDAO = BaseDAO.getPassengerDAO();
    }

    public Passenger getOneById(Long id) {
        if (id <= 0) {
            throw new NegativeIdException("Passenger id: " + id); // write own exception class
        }
        Passenger passenger = passengerDAO.getOneById(id);
        if (passenger.getId() == null) {
            throw new PassengerNotFoundException("For id: " + id);
        }
        return passenger;
    }

    @Override
    public void addPassenger(Passenger passenger) {
        passengerDAO.insertPassenger(passenger);
    }

    public void checkPassengerData(AuthorizationDetails details) {
        if (!passengerDAO.isUserExistsByUserName(details.getUsername())) {
            throw new IncorrectUserNameException("For username: " + details.getUsername());
        }
        if (!passengerDAO.isUserPasswordCorrect(details.getUsername(), details.getPassword())) {
            throw new IncorrectPasswordException("For user:" + details.getUsername());
        }
    }

    @Override
    public Role getRoleByUserName(String userName) {
        return passengerDAO.getRoleByUserName(userName);
    }

    @Override
    public List<String> getAllUserNames() {
        List<String> allUserNames = passengerDAO.getAllUserName();
        if (allUserNames == null || allUserNames.isEmpty()) {
            throw new RuntimeException("there are no any passengers in database");
        }
        return allUserNames;
    }

    @Override
    public Passenger getOneByUsername(String username) {
        Passenger passenger = passengerDAO.getOneByUsername(username);
        if (passenger == null) {
            throw new PassengerNotFoundException(username + ": not found");
        }
        return passenger;
    }
}
