package controllers.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.user.SignInService;
import utils.session;

import java.io.IOException;


public class Sign_InController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter both email and password.");
            return;
        }

        try {
            SignInService service = new SignInService();
            User user = service.authenticate(email, password);

            if (user != null) {
                // Save the session
                utils.session.setCurrentUser(user);

                // Load and display users_list.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/welcome.fxml"));
                javafx.scene.Parent root = loader.load();
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("User List");
                stage.setScene(new javafx.scene.Scene(root));
                stage.show();

                // Close login window
                emailField.getScene().getWindow().hide();
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid email or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Login failed due to a system error.");
        }
    }


    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }

    public void goToSignUp(javafx.event.ActionEvent actionEvent) {
        try {
            // Load the sign-up FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/sign_up.fxml"));
            Parent root = loader.load();

            // Get the current stage from the event
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // You can log this or show an alert
        }
    }
}
