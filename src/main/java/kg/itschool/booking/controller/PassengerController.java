package kg.itschool.booking.controller;


import kg.itschool.booking.model.Passenger;
import kg.itschool.booking.service.impl.PassengerServiceImpl;

public class PassengerController {

    private final PassengerServiceImpl passengerServiceImpl;

    public PassengerController() {
        passengerServiceImpl = new PassengerServiceImpl();
    }

    public Passenger getOneById(Long id) {
        return passengerServiceImpl.getOneById(id);
    }
}
