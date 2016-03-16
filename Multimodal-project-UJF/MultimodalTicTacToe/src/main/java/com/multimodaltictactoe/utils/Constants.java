package com.multimodaltictactoe.utils;

import com.multimodaltictactoe.gui.utils.icon.FontAwesomeIcon;
import com.multimodaltictactoe.logic.utils.Player;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class Constants {
    public static final class Gui {
	public static final class Colors {	    
	    public static final Color COLOR_PLAYER_1 = Color.web("#39ABD9");
	    public static final Color COLOR_PLAYER_2 = Color.web("#EEDA76");
	    public static final Color BOARD_LINE_COLOR = Color.web("#A9A9A4");
	    public static final Color COLOR_VICTORY_LINE = Color.RED;
	    public static final Color COLOR_STRING_TURN = Color.web("#A9A9A4");
	    public static final Color COLOR_STROKE_LIGHT_BUTTON = Color.BLACK;
	    public static final Color COLOR_BACKGROUND_APP = Color.web("#544F4A");
	    public static final Background BACKGROUND_APP = new Background(
		    new BackgroundFill(COLOR_BACKGROUND_APP, CornerRadii.EMPTY, Insets.EMPTY));
	    public static final Color COLOR_ICON_QUIT_RESTART = BOARD_LINE_COLOR;
	    public static final Color COLOR_ICON_MICRO_ON = Color.LIMEGREEN;
	    public static final Color COLOR_ICON_MICRO_OFF = Color.TOMATO;
	    public static final Color COLOR_COORDINATE_BOARD = BOARD_LINE_COLOR;
	}
	
	public static final class Sizes {
	    public static final double BOARD_LINE_SIZE = 2.0;
	    public static final double RATIO_CELL_STROKE_PIECE = 10.0;
	    public static final double LIGHT_RADIUS_BUTTON = 7.0;
	    public static final double LIGHT_STROKE_BUTTON = 1.0;
	    public static final double MARGIN_AROUND_PIECE_RIGHT_PANE = 10.0;
	    //=== Espacements et marges ===
	    public static final double GAP_BOARD_INFOTEXT = 50.0;
	    public static final double GAP_INFOTEXT_TOPPANE = 30.0;
	    public static final double PADDING_APP = 20.0;
	    public static final double GAP_TOPBUTTONS = 10.0;
	    public static final double GAP_RIGHTBUTTONS = 10.0;
	}
	
	public static final class Times {
	    public static final double TRANSITION_COLOR_PIECE_TIME = 200.0;
	}
	
	public static final class Fonts {
	    private static final String FAMILY_FONT_INFO_TURN = "Helvetica";
	    private static final double SIZE_FONT_INFO_TURN = 20.0;
	    public static final Font FONT_INFO_TURN = Font.font(FAMILY_FONT_INFO_TURN, SIZE_FONT_INFO_TURN);
	    public static final Font FONT_INFO_TURN_BOLD = Font.font(FAMILY_FONT_INFO_TURN, FontWeight.BOLD, SIZE_FONT_INFO_TURN);
	    public static final double SIZE_FONT_COORDINATE_BOARD = 20.0;
	}
	
	public static final class Strings {
	    public static final String WINDOW_TITLE = "Morpion";
	    public static final String[] MESSAGE_TURN_PLAYER_1 = {"A ", "vous", " de jouer"};
	    public static final int INDICE_TURN_PLAYER_1 = 1;
	    public static final String[] MESSAGE_TURN_PLAYER_2 = {"A l'", "ordinateur", " de jouer"};
	    public static final int INDICE_TURN_PLAYER_2 = 1;
	    public static final String[] MESSAGE_PLAYER_1_WIN = {"Vous", " gagnez"};
	    public static final int INDICE_PLAYER_1_WIN = 0;
	    public static final String[] MESSAGE_PLAYER_2_WIN = {"L'", "ordinateur", " gagne"};
	    public static final int INDICE_PLAYER_2_WIN = 1;
	    public static final String[] MESSAGE_DRAW_GAME = {"Match nul"};
	    public static final int INDICE_DRAW_GAME = -1;
	}
	
	public static final class Others {
	    public static final boolean OUTLINE_BOARD = false;
	    public static final double OPACITY_PIECE_HOVER = 0.15;
	    public static final Player PLAYER_HOVER = Player.PLAYER_1;
	}
	
	public static final class Buttons {
	    public static final String LABEL_BUTTON_MICRO = "Microphone";
	    public static final String LABEL_BUTTON_RESTART = "Recommencer";
	    public static final String LABEL_BUTTON_QUIT = "Quitter";
	    //=== Boutons panneau de droite ===
	    public static final double FONT_SIZE_LABEL_BUTTON_RIGHT_PANE = 15.0;
	    public static final double FONT_SIZE_ICON_BUTTON_RIGHT_PANE = 20.0;
	    public static final ContentDisplay DISPLAY_ICON_BUTTON_RIGHT_PANE = ContentDisplay.LEFT;
	    //=== Boutons panneau du haut ===
	    public static final double FONT_SIZE_LABEL_BUTTON_TOP_PANE = 11.0;
	    public static final double FONT_SIZE_ICON_BUTTON_TOP_PANE = 25.0;
	    public static final ContentDisplay DISPLAY_ICON_BUTTON_TOP_PANE = ContentDisplay.TOP;
	    //=== Icones ===
	    public static final FontAwesomeIcon ICON_BUTTON_MICRO_ON = FontAwesomeIcon.MICROPHONE;
	    public static final FontAwesomeIcon ICON_BUTTON_MICRO_OFF = FontAwesomeIcon.MICROPHONE_SLASH;
	    public static final FontAwesomeIcon ICON_BUTTON_QUIT = FontAwesomeIcon.POWER_OFF;
	    public static final FontAwesomeIcon ICON_BUTTON_RESTART = FontAwesomeIcon.REFRESH;
	}
    }
    
    public static final class Logic {
	public static final class Times {
	    public static final int THINKING_TIME = 1000;
	}
	
	public static final class Others {
	    public static final String IP = "192.168.0.110";
//	    public static final String IP = "192.168.0.100";
	    public static final int PORT = 1234;
	    public static final boolean DEMO_MODE = false;
	}
    }
    
    
    
    
    
    
    
    
    
}
