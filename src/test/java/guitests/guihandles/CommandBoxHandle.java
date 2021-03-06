package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle<TextField> {

    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    /**
     * Constructor for handler.
     *
     * @param commandBoxNode
     */
    public CommandBoxHandle(TextField commandBoxNode) {
        super(commandBoxNode);

    }

    /**
     * Returns the text in the command box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     */
    public void run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
    }

    /**
     * Presses the UP button.
     */
    public void pressUp() {
        click();
        guiRobot.type(KeyCode.UP);
    }

    /**
     * Presses the DOWN button.
     */
    public void pressDown() {
        click();
        guiRobot.type(KeyCode.DOWN);
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
