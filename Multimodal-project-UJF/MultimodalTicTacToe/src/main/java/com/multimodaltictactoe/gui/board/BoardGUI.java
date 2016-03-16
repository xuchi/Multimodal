package com.multimodaltictactoe.gui.board;

import com.multimodaltictactoe.gui.piece.PieceGUI;
import com.multimodaltictactoe.gui.utils.ApplicationUtils;
import com.multimodaltictactoe.gui.utils.ResizableGroup;
import com.multimodaltictactoe.logic.board.BoardListener;
import com.multimodaltictactoe.logic.checker.CheckerListener;
import com.multimodaltictactoe.logic.utils.Coordinate;
import com.multimodaltictactoe.logic.utils.GameStatus;
import com.multimodaltictactoe.logic.utils.Player;
import com.multimodaltictactoe.utils.Constants;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class BoardGUI extends StackPane implements BoardGUIInterface, BoardListener, CheckerListener {
    private static final int NB_ROWS = 3;
    private static final int NB_COLUMNS = 3;

    private static final Color COLOR_1 = Constants.Gui.Colors.COLOR_PLAYER_1;
    private static final Color COLOR_2 = Constants.Gui.Colors.COLOR_PLAYER_2;
    private static final double LINE_SIZE = Constants.Gui.Sizes.BOARD_LINE_SIZE;
    private static final Color LINE_COLOR = Constants.Gui.Colors.BOARD_LINE_COLOR;
    private static final boolean OUTLINE_BOARD = Constants.Gui.Others.OUTLINE_BOARD;
    private static final double RATIO_CELL_STROKE_PIECE = Constants.Gui.Sizes.RATIO_CELL_STROKE_PIECE;
    private static final double TRANSITION_COLOR_PIECE_TIME = Constants.Gui.Times.TRANSITION_COLOR_PIECE_TIME;
    private static final double OPACITY_PIECE_HOVER = Constants.Gui.Others.OPACITY_PIECE_HOVER;
    private static final Player PLAYER_HOVER = Constants.Gui.Others.PLAYER_HOVER;
    private static final Color COLOR_VICTORY_LINE = Constants.Gui.Colors.COLOR_VICTORY_LINE;
    
    private final Group board;
    private final NumberBinding cellSize, strokeWidth;
    private PieceGUI pieces[][];
    private Text coordTexts[][];
    private Coordinate lastCoordHover;
    private Line victoryLine;
    
    private final Set<BoardGUIListener> listeners;
    
    public BoardGUI() {
	super();
	this.board = new ResizableGroup();
	this.lastCoordHover = null;
	this.listeners = new HashSet<>();
	
	//calculs graphiques
	double lineSize = LINE_SIZE;
	int nbRows = NB_ROWS;
	int nbColumns = NB_COLUMNS;
	this.cellSize = Bindings.min(
		//largeur
		this.widthProperty().subtract(lineSize * (nbColumns + 1)).divide(nbColumns),
		//hauteur
		this.heightProperty().subtract(lineSize * (nbRows + 1)).divide(nbRows)
	);
	NumberBinding boardWidth = this.cellSize.multiply(nbColumns).add(lineSize * (nbColumns + 1));
	NumberBinding boardHeight = this.cellSize.multiply(nbRows).add(lineSize * (nbRows + 1));
	this.strokeWidth = this.cellSize.divide(RATIO_CELL_STROKE_PIECE);
	
	this.initVictoryLine(boardWidth, boardHeight);
	this.drawGrid(boardWidth, boardHeight);
	this.drawCoordinates();
	this.drawPieces();
	
	this.board.getChildren().add(this.victoryLine);
	this.getChildren().addAll(this.board);
	this.setEvents();
    }
    
    private void initVictoryLine(NumberBinding boardWidth, NumberBinding boardHeight) {
	this.victoryLine = new Line();
	NumberBinding middleWidth = boardWidth.divide(2.0);
	NumberBinding middleHeight = boardHeight.divide(2.0);
	this.victoryLine.startXProperty().bind(middleWidth);
	this.victoryLine.startYProperty().bind(middleHeight);
	this.victoryLine.endXProperty().bind(middleWidth);
	this.victoryLine.endYProperty().bind(middleHeight);
	this.victoryLine.setStroke(COLOR_VICTORY_LINE);
	this.victoryLine.setFill(Color.TRANSPARENT);
	this.victoryLine.setStroke(Color.TRANSPARENT);
	this.victoryLine.strokeWidthProperty().bind(this.strokeWidth);
	this.victoryLine.setStrokeType(StrokeType.CENTERED);
	this.victoryLine.setStrokeLineCap(StrokeLineCap.ROUND);
    }
    
    private void drawGrid(NumberBinding boardWidth, NumberBinding boardHeight) {
	int nbRows = NB_ROWS;
	int nbColumns = NB_COLUMNS;
	Color lineColor = LINE_COLOR;
	double lineSize = LINE_SIZE;
	for(int r = 0; r <= nbRows; r++) {
	    if((r != 0 && r != nbRows) || OUTLINE_BOARD){
		Rectangle rectangle = new Rectangle();
		rectangle.setFill(lineColor);
		rectangle.setHeight(lineSize);
		rectangle.widthProperty().bind(boardWidth);
		rectangle.setX(0.0);
		rectangle.yProperty().bind(this.cellSize.add(lineSize).multiply(r));
		this.board.getChildren().add(rectangle);
	    }
	}
	for(int c = 0; c <= nbColumns; c++) {
	    if((c != 0 && c != nbColumns) || OUTLINE_BOARD){
		Rectangle rectangle = new Rectangle();
		rectangle.setFill(lineColor);
		rectangle.setWidth(lineSize);
		rectangle.heightProperty().bind(boardHeight);
		rectangle.setY(0.0);
		rectangle.xProperty().bind(this.cellSize.add(lineSize).multiply(c));
		board.getChildren().add(rectangle);
	    }
	}
    }
    
    private void drawCoordinates() {
	int nbRows = NB_ROWS;
	int nbColumns = NB_COLUMNS;
	double lineSize = LINE_SIZE;
	this.coordTexts = new Text[nbRows][nbColumns];
	for(int r = 0; r < nbRows; r++) {
	    for(int c = 0; c < nbColumns; c++) {
		NumberBinding middleX = this.cellSize.add(lineSize).multiply(c).add(lineSize).add(this.cellSize.divide(2));
		NumberBinding middleY = this.cellSize.add(lineSize).multiply(r).add(lineSize).add(this.cellSize.divide(2));
		Text coordText = new Text(new Coordinate(r, c).toStringForBoard());
		coordText.setFont(Font.font(Constants.Gui.Fonts.SIZE_FONT_COORDINATE_BOARD));
		coordText.setFill(ApplicationUtils.setOpacity(Constants.Gui.Colors.COLOR_COORDINATE_BOARD, 0.5));
		coordText.setBoundsType(TextBoundsType.VISUAL);		
		coordText.xProperty().bind(middleX.subtract(coordText.getLayoutBounds().getWidth() / 2.0));
		coordText.yProperty().bind(middleY.add(coordText.getLayoutBounds().getHeight() / 2.0));
		this.coordTexts[r][c] = coordText;
		board.getChildren().add(coordText);
	    }
	}
    }
    
    private void drawPieces() {
	int nbRows = NB_ROWS;
	int nbColumns = NB_COLUMNS;
	double lineSize = LINE_SIZE;
	this.pieces = new PieceGUI[nbRows][nbColumns];
	for(int r = 0; r < nbRows; r++) {
	    for(int c = 0; c < nbColumns; c++) {
		PieceGUI piece = new PieceGUI(this.cellSize, this.strokeWidth, TRANSITION_COLOR_PIECE_TIME,
			OPACITY_PIECE_HOVER, COLOR_1, COLOR_2);
		piece.layoutXProperty().bind(this.cellSize.add(lineSize).multiply(c).add(lineSize).add(this.cellSize.divide(2)));
		piece.layoutYProperty().bind(this.cellSize.add(lineSize).multiply(r).add(lineSize).add(this.cellSize.divide(2)));
		this.pieces[r][c] = piece;
		board.getChildren().add(piece);
	    }
	}
    }
    
    private boolean inIndex(double xory, int ind) {
	double lineSize = LINE_SIZE;
	double cellSizeDouble = this.cellSize.getValue().doubleValue();
	double start = lineSize + (ind * (lineSize + cellSizeDouble));
	return (xory >= start && xory <= start + cellSizeDouble);
    }
    
    public Coordinate getPieceCoord(double x, double y) {
	double boardX = x - this.board.getLayoutX();
	double boardY = y - this.board.getLayoutY();
	
	int nbRows = NB_ROWS;
	int nbColumns = NB_COLUMNS;
	
	int max = Math.max(nbRows, nbColumns);
	int r = -1, c = -1;
	for(int i = 0; i < max; i++) {
	    if(inIndex(boardX, i)) {
		c = i;
	    }
	    if(inIndex(boardY, i)) {
		r = i;
	    }
	}
	if(r != -1 && c != -1) {
	    return new Coordinate(r, c);
	}
	else {
	    return null;
	}
    }
    
    private void setEvents() {
	//evenement hover
	this.setOnMouseMoved(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		double x = event.getX();
		double y = event.getY();
		Coordinate coordHover = getPieceCoord(x, y);
		if(lastCoordHover != null && !lastCoordHover.equals(coordHover)) {
		    pieces[lastCoordHover.getTabRow()][lastCoordHover.getTabColumn()].unhover();
		}
		if(coordHover != null) {
		    pieces[coordHover.getTabRow()][coordHover.getTabColumn()].hover(PLAYER_HOVER);
		}
		lastCoordHover = coordHover;
	    }
	});
	
	this.setOnMouseExited(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		if(lastCoordHover != null) {
		    pieces[lastCoordHover.getTabRow()][lastCoordHover.getTabColumn()].unhover();
		    lastCoordHover = null;
		}
	    }
	});
	
	this.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		double x = event.getX();
		double y = event.getY();
		Coordinate coordClic = getPieceCoord(x, y);
		if(coordClic != null) {
		    fireClic(coordClic);
		}
	    }
	});
    }
    
    private void fireClic(Coordinate coord) {
	for(BoardGUIListener bl : this.listeners) {
	    bl.userClicked(coord);
	}
    }
    
    @Override
    public void pieceChanged(Coordinate coord, Player[][] board) {
	int r = coord.getTabRow();
	int c = coord.getTabColumn();
	this.coordTexts[r][c].setVisible(false);
	this.pieces[r][c].setPlayer(board[r][c]);
	this.pieces[r][c].unhover();
    }
    
    @Override
    public void statusChanged(GameStatus gameStatus, Player player) {
	if(gameStatus == GameStatus.VICTORY) {
	    double startX = gameStatus.getStart().getTabColumn();
	    double startY = gameStatus.getStart().getTabRow();
	    double endX = gameStatus.getEnd().getTabColumn();
	    double endY = gameStatus.getEnd().getTabRow();
	    double lineSize = LINE_SIZE;
	    NumberBinding startXbinding = this.cellSize.multiply((2.0 * startX + 1.0) / 2.0).add(lineSize * (startX + 1.0));
	    NumberBinding startYbinding = this.cellSize.multiply((2.0 * startY + 1.0) / 2.0).add(lineSize * (startY + 1.0));
	    NumberBinding endXbinding = this.cellSize.multiply((2.0 * endX + 1.0) / 2.0).add(lineSize * (endX + 1.0));
	    NumberBinding endYbinding = this.cellSize.multiply((2.0 * endY + 1.0) / 2.0).add(lineSize * (endY + 1.0));
	    this.victoryLine.startXProperty().bind(startXbinding);
	    this.victoryLine.startYProperty().bind(startYbinding);
	    this.victoryLine.endXProperty().bind(endXbinding);
	    this.victoryLine.endYProperty().bind(endYbinding);
	    this.victoryLine.setStroke(COLOR_VICTORY_LINE);
	}
    }
    
    @Override
    public void boardCleared() {
	for(int r = 0; r < NB_ROWS; r++) {
	    for(int c = 0; c < NB_COLUMNS; c++) {
		this.pieces[r][c].clear();
		this.coordTexts[r][c].setVisible(true);
		this.victoryLine.setStroke(Color.TRANSPARENT);
	    }
	}
    }
    
    @Override
    public boolean addListener(BoardGUIListener bl) {
	if(this.listeners.contains(bl)) {
	    return false;
	}
	else return this.listeners.add(bl);
    }
    
    @Override
    public boolean removeListener(BoardGUIListener bl) {
	return this.listeners.remove(bl);
    }
}
