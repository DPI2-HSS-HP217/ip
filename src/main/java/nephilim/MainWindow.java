package nephilim;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Nephilim nephilim;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image nephilimImage = new Image(this.getClass().getResourceAsStream("/images/Nephi.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Causes window to send a greeting message.
     */
    public void greet() {
        dialogContainer.getChildren().addAll(
                DialogueBox.getNephilimDialog(nephilim.init(), nephilimImage)
        );
    }

    /** Injects the Nephilim instance */
    public void setNephilim(Nephilim n) {
        nephilim = n;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Nephilim's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = nephilim.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogueBox.getUserDialog(input, userImage),
                DialogueBox.getNephilimDialog(response, nephilimImage)
        );
        userInput.clear();
        if (response.contains("Fair winds guide you home.")) {
            Platform.exit(); //Exit application on goodbye message.
        }
    }
}