package kg.itschool.booking.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import kg.itschool.booking.service.PassengerService;
import kg.itschool.booking.service.impl.PassengerServiceImpl;
import kg.itschool.booking.controller.UserInfoController;
public class UserMenuController {
    private PassengerService passengerService;

    @FXML
    private Button userPassButton;

    @FXML
    private Button userInfoButton;

    @FXML
    private Button closeButton;

    @FXML
    private Line slide;

    @FXML
    private Line slide1;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField genderTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label name;

    @FXML
    private void onCloseButtonClicked() {
        closeButton.getScene().getWindow().hide();
    }

    public UserMenuController() {
        this.passengerService = new PassengerServiceImpl();
        System.out.println(passengerService.getAllUserNames());
    }

    @FXML
    void initialize() {
        emailTextField.setText(UserInfoController.getCurrentPassenger().getEmail());
        phoneTextField.setText(UserInfoController.getCurrentPassenger().getPhoneNumber());
        name.setText(UserInfoController.getCurrentPassenger().getUsername());
        dateTextField.setText(String.valueOf(UserInfoController.getCurrentPassenger().getBirthDate()));
        genderTextField.setText(UserInfoController.getCurrentPassenger().getSex());
        usernameTextField.setText(UserInfoController.getCurrentPassenger().getUsername());
        nameTextField.setText(UserInfoController.getCurrentPassenger().getFirstName() + " " + UserInfoController.getCurrentPassenger().getLastName());
    }
}
