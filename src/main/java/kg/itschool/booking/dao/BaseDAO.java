package kg.itschool.booking.dao;


import kg.itschool.booking.dao.impl.PassengerDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static kg.itschool.booking.dao.ConnectionConstants.*;


public class BaseDAO {

    private static PassengerDAO passengerDAO;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static PassengerDAO getPassengerDAO() {
        if (passengerDAO == null) {
            passengerDAO = new PassengerDAOImpl();
        }
        return passengerDAO;
    }

//    public static CityDAO getCityDAO() {
//        return new CityDAOImpl();
//    }
//
//    public static PlaneDAO getPlaneDAO() {
//        return new PlaneDAO();
//    }

}
