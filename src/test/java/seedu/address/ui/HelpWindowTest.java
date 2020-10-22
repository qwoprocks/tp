package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static seedu.address.ui.HelpWindow.USERGUIDE_URL;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;
import javafx.stage.Stage;
import seedu.address.MainApp;

public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private HelpWindowHandle helpWindowHandle;

    @BeforeEach
    public void setUp() throws Exception {
        guiRobot.interact(() -> helpWindow = new HelpWindow(new MainApp()));
        FxToolkit.registerStage(helpWindow::getRoot);
        helpWindowHandle = new HelpWindowHandle(helpWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        FxToolkit.showStage();
        assertEquals(USERGUIDE_URL, helpWindowHandle.getUgUrl());
    }

    @Test
    public void isShowing_helpWindowIsShowing_returnsTrue() {
        guiRobot.interact(helpWindow::show);
        assertTrue(helpWindow.isShowing());
    }

    @Test
    public void isShowing_helpWindowIsHiding_returnsFalse() {
        assertFalse(helpWindow.isShowing());
    }

    @Test
    public void pressEscKey_helpWindowIsShowing_returnsFalse() throws Exception {
        FxToolkit.showStage();
        guiRobot.pauseForHuman();

        helpWindowHandle.pressEscKey();
        assertFalse(helpWindow.isShowing());
    }

    @Test
    public void copyUrl_userGuideUrl_copiesCorrectly() throws Exception {
        // Clipboard access in headless environment will throw an error.
        assumeFalse(guiRobot.isHeadlessMode(), "Test skipped in headless mode.");

        FxToolkit.showStage();

        helpWindowHandle.clickOnCopyUrlButton();
        guiRobot.pauseForHuman();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String ugUrl = (String) clipboard.getData(DataFlavor.stringFlavor);
        assertEquals(USERGUIDE_URL, ugUrl);
    }

    @Test
    public void copyUrlByDirectAccess_userGuideUrl_copiesCorrectly() throws Exception {
        // This test is to increase coverage. Somehow IDEA does not
        // pickup the copyURL method from the previous test.
        // Two tests are needed in fact.
        assumeFalse(guiRobot.isHeadlessMode(), "Test skipped in headless mode.");

        FxToolkit.showStage();

        helpWindowHandle.accessCopyUrlButton();
        guiRobot.pauseForHuman();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String ugUrl = (String) clipboard.getData(DataFlavor.stringFlavor);
        assertEquals(USERGUIDE_URL, ugUrl);
    }

    @Test
    public void focus_helpWindowNotFocused_focused() throws Exception {
        // TODO: This test skip can be removed once this bug is fixed:
        // https://github.com/javafxports/openjdk-jfx/issues/50
        //
        // When there are two stages (stage1 and stage2) shown,
        // stage1 is in focus and stage2.requestFocus() is called,
        // we expect that stage1.isFocused() will return false while
        // stage2.isFocused() returns true. However, as reported in the bug report,
        // both stage1.isFocused() and stage2.isFocused() returns true,
        // which fails the test.
        assumeFalse(guiRobot.isHeadlessMode(), "Test skipped in headless mode: Window focus behavior is buggy.");
        guiRobot.interact(helpWindow::show);

        // Focus on another stage to remove focus from the helpWindow
        guiRobot.interact(() -> {
            Stage temporaryStage = new Stage();
            temporaryStage.show();
            temporaryStage.requestFocus();
        });
        assertFalse(helpWindow.getRoot().isFocused());

        guiRobot.interact(helpWindow::focus);
        assertTrue(helpWindow.getRoot().isFocused());
    }
}
