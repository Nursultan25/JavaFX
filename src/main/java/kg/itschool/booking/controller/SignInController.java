package kg.itschool.booking.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kg.itschool.booking.dao.PassengerDAO;
import kg.itschool.booking.dao.impl.PassengerDAOImpl;
import kg.itschool.booking.dto.AuthorizationDetails;
import kg.itschool.booking.model.enums.Role;
import kg.itschool.booking.service.PassengerService;
import kg.itschool.booking.service.impl.PassengerServiceImpl;

import java.io.IOException;


public class SignInController {

    private PassengerService passengerService;

    public SignInController() {
        this.passengerService = new PassengerServiceImpl();
    }

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button submitButton;

    @FXML
    private void onCloseButtonClicked() {
        closeButton.getScene().getWindow().hide();
    }

    @FXML
    private void onSignUpButtonClicked() throws IOException {
        Stage stage = (Stage) signUpButton.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("/static/forms/sign-up-form.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    @FXML
    private void onSubmitButtonClicked() throws IOException {


        AuthorizationDetails details = new AuthorizationDetails();
        details.setUsername(userNameTextField.getText().trim());
        details.setPassword(passwordTextField.getText().trim());

        String pathToForm = null;
        String title = null;
        try {
            passengerService.checkPassengerData(details);
            Role role = passengerService.getRoleByUserName(userNameTextField.getText().trim());

            switch (role) {
                case ADMIN:
                    pathToForm = "/static/forms/main-menu-admin.fxml";
                    title = "Admin panel";
                    break;
                case USER:
                    pathToForm = "/static/forms/main-menu-user.fxml";
                    title = "User panel";
                    break;
            }
        } catch (Exception ex) {

        }
        UserInfoController.setCurrentPassenger(passengerService.getOneByUsername(userNameTextField.getText().trim()));
        submitButton.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource(pathToForm));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/static/styles/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
}
