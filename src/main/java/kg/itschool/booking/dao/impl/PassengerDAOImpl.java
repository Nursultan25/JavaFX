package kg.itschool.booking.dao.impl;


import kg.itschool.booking.dao.BaseDAO;
import kg.itschool.booking.dao.PassengerDAO;
import kg.itschool.booking.model.Passenger;
import kg.itschool.booking.model.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAOImpl implements PassengerDAO { // DAO -> Data Access Object

    private static PassengerDAOImpl passengerDAOImpl; // null

    public static PassengerDAOImpl getInstance() {
        if (passengerDAOImpl == null) {
            passengerDAOImpl = new PassengerDAOImpl();
        }
        return passengerDAOImpl;
    }

    {
        String createTable = "" +
                "CREATE TABLE IF NOT EXISTS passengers(" +
                    "id               BIGSERIAL    NOT NULL," +
                    "first_name       VARCHAR(100) NOT NULL," +
                    "last_name        VARCHAR(100) NOT NULL," +
                    "middle_name      VARCHAR(100) DEFAULT 'unspecified'," +
                    "email            VARCHAR(100) NOT NULL," +
                    "phone_number     VARCHAR(100) NOT NULL," +
                    "birth_date       DATE         NOT NULL," +
                    "sex              VARCHAR(10)  DEFAULT 'neutral'," +
                    "passport_details VARCHAR(100) NOT NULL," +
                    "CONSTRAINT pk_passenger_id " +
                    "PRIMARY KEY(id)" +
                ");";

        Connection connection = null;
        Statement statement = null;
        try {
            connection = BaseDAO.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(createTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }
    }

    public PassengerDAOImpl() {

    }

    public int insertPassenger(Passenger passenger) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int response = 0;

        String query = "INSERT INTO passengers(first_name, last_name, email, user_name, password, phone_number, birth_date, sex, passport_details, user_role) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            connection = BaseDAO.getConnection();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, passenger.getFirstName());
            preparedStatement.setString(2, passenger.getLastName());
            preparedStatement.setString(3, passenger.getEmail());
            preparedStatement.setString(4, passenger.getUsername());
            preparedStatement.setString(5, passenger.getPassword());
            preparedStatement.setString(6, passenger.getPhoneNumber());
            preparedStatement.setDate(7, new Date(passenger.getBirthDate().getTime()));
            preparedStatement.setString(8, passenger.getSex());
            preparedStatement.setString(9, passenger.getPassportDetails());
            preparedStatement.setString(10, passenger.getRole().toString());
            System.out.println("Passenger added");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return response;
    }

    public Passenger getOneById(Long id) {
        Passenger passenger = new Passenger();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "" +
                "SELECT * FROM passengers " +
                "WHERE id = ?;";
        try {
            connection = BaseDAO.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            passenger.setId(resultSet.getLong(1));
            passenger.setFirstName(resultSet.getString(2));
            passenger.setLastName(resultSet.getString(3));
            passenger.setBirthDate(resultSet.getDate(7));
            passenger.setEmail(resultSet.getString(6));
            passenger.setSex(resultSet.getString(8));
            passenger.setPassportDetails(resultSet.getString(9));

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return passenger;
    }

    @Override
    public boolean isUserExistsByUserName(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isExists = false;

        String query = "" +
                "SELECT EXISTS(SELECT 1 FROM passengers WHERE user_name = ?)";

        try {
            connection = BaseDAO.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            isExists = resultSet.getBoolean(1);
            System.out.println("Is user exists: " + isExists);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return isExists;
    }

    @Override
    public boolean isUserPasswordCorrect(String username, String password) {
        String userPassword = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "" +
                "SELECT password FROM passengers WHERE user_name = ?";
        try {
            connection = BaseDAO.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userPassword = resultSet.getString(1);
            System.out.println(password + " : " + userPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return password.equals(userPassword);
    }

    @Override
    public Role getRoleByUserName(String userName) {
        Role role = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "" +
                "SELECT user_role FROM passengers WHERE user_name IN (?)";

        try {
            connection = BaseDAO.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            role = Role.valueOf(resultSet.getString(1).toUpperCase());

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return role;
    }

    @Override
    public List<String> getAllUserName() {
        List<String> allUserNames = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "" +
                "SELECT DISTINCT user_name FROM passengers";
        try {
            connection = BaseDAO.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                allUserNames.add(resultSet.getString("user_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(connection);
            close(statement);
            close(resultSet);
        }

        return allUserNames;
    }

    @Override
    public Passenger getOneByUsername(String username) {
        Passenger passenger = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "" +
                "Select * FROM passengers WHERE user_name IN (?);";
        try {
            connection = BaseDAO.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            passenger = new Passenger();
            passenger.setId(resultSet.getLong("id"));
            passenger.setUsername(resultSet.getString("user_name"));
            passenger.setFirstName(resultSet.getString("first_name"));
            passenger.setLastName(resultSet.getString("last_name"));
            passenger.setEmail(resultSet.getString("email"));
            passenger.setPassportDetails(resultSet.getString("passport_details"));
            passenger.setPassword(resultSet.getString("password"));
            passenger.setPhoneNumber(resultSet.getString("phone_number"));
            passenger.setBirthDate(resultSet.getDate("birth_date"));
            passenger.setSex(resultSet.getString("sex"));
            passenger.setRole(Role.valueOf(resultSet.getString("user_role").toUpperCase()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(connection);
            close(resultSet);
            close(preparedStatement);
        }

        return passenger;
    }

    private void close(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
