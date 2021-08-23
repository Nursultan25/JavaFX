package kg.itschool.booking.controller;

import kg.itschool.booking.model.Passenger;

public class UserInfoController {
    private static Passenger currentPassenger;

    public static Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    public static void setCurrentPassenger(Passenger currentPassenger) {
        UserInfoController.currentPassenger = currentPassenger;
    }
}
