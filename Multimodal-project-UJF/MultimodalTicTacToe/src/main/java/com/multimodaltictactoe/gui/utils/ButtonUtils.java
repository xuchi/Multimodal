package com.multimodaltictactoe.gui.utils;

import com.multimodaltictactoe.gui.utils.icon.IconButton;
import com.multimodaltictactoe.utils.Constants;
import javafx.scene.control.ContentDisplay;

public class ButtonUtils {
    public static void paramButton(IconButton button, boolean isTopButton) {
	double labelSize = isTopButton?Constants.Gui.Buttons.FONT_SIZE_LABEL_BUTTON_TOP_PANE:
		Constants.Gui.Buttons.FONT_SIZE_LABEL_BUTTON_RIGHT_PANE;
	double iconSize = isTopButton?Constants.Gui.Buttons.FONT_SIZE_ICON_BUTTON_TOP_PANE:
		Constants.Gui.Buttons.FONT_SIZE_ICON_BUTTON_RIGHT_PANE;
	ContentDisplay contentDisplay = isTopButton?Constants.Gui.Buttons.DISPLAY_ICON_BUTTON_TOP_PANE:
		Constants.Gui.Buttons.DISPLAY_ICON_BUTTON_RIGHT_PANE;
	button.setLabelSize(labelSize);
	button.setIconSize(iconSize);
	button.setContentDisplay(contentDisplay);
	button.setFocusTraversable(false);
	button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
}
