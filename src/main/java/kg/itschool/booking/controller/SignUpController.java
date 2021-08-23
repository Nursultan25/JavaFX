package kg.itschool.booking.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kg.itschool.booking.model.Passenger;
import kg.itschool.booking.model.enums.Role;
import kg.itschool.booking.model.enums.Sex;
import kg.itschool.booking.service.PassengerService;
import kg.itschool.booking.service.impl.PassengerServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    private PassengerService service;

    public SignUpController() {
        this.service = new PassengerServiceImpl();
    }

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField passportDetailsTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private ComboBox<String> sexComboBox;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private Button closeButton;

    @FXML
    private Button signInButton;

    @FXML
    private void onSubmitButtonClicked() {
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords does not match");
        }
        int index = emailTextField.getText().indexOf('@');
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstNameTextField.getText().trim());
        passenger.setLastName(lastNameTextField.getText().trim());
        passenger.setEmail(emailTextField.getText().trim());
        passenger.setUsername(emailTextField.getText().substring(0, index).trim()); //(john_de@gmail.com).substring(0, index) -> john_de
        passenger.setPhoneNumber(phoneNumberTextField.getText().trim());
        passenger.setPassportDetails(passportDetailsTextField.getText().trim());
        passenger.setSex(sexComboBox.getValue().trim());
        passenger.setBirthDate(new Date(dobDatePicker.getValue().toEpochDay()));
        passenger.setPassword(password.trim());
        passenger.setRole(Role.USER);

        System.out.println(passenger);

        service.addPassenger(passenger);
    }

    @FXML
    private void onCloseButtonClicked() {
        closeButton.getScene().getWindow().hide();
    }

    @FXML
    private void onSignInButtonClicked() throws IOException {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/static/forms/sign-in-form.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Sex[] sexes = Sex.values();
        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (Sex sex : sexes) {
            observableList.add(sex.toString().charAt(0) + sex.toString().substring(1).toLowerCase());
        }
        sexComboBox.setItems(observableList);
    }
}
