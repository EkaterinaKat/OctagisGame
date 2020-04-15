package com.octagisgame.builders;

import android.graphics.Point;

import com.octagisgame.activities.GameActivity;
import com.octagisgame.controller.Game;
import com.octagisgame.controller.controlinterfaces.ClassicControlInterface;
import com.octagisgame.controller.controlinterfaces.ControlInterface;
import com.octagisgame.controller.controlinterfaces.PolygonControlInterface;
import com.octagisgame.model.PlayingField;
import com.octagisgame.view.painttuners.BasicStylePaintTuner;
import com.octagisgame.view.painttuners.BrickStylePaintTuner;
import com.octagisgame.view.drawers.ClassicGameDrawer;
import com.octagisgame.view.drawers.GameDrawer;
import com.octagisgame.view.painttuners.MinimalisticStylePaintTuner;
import com.octagisgame.view.painttuners.PaintTuner;
import com.octagisgame.view.drawers.PolygonGameDrawer;

public class GameBuilder {
    public enum Mode {CLASSIC, POLYGON}

    public enum DrawingStyle {BASIC, BRICK, MINIMALISTIC}

    private final int DEFAULT_NUMBER_OF_COLUMNS = 15;
    private final int DEFAULT_NUMBER_OF_ROWS = 15;
    private GameActivity activity;
    private int numberOfColumns;
    private int numberOfRows;
    private Mode mode;
    private PaintTuner paintTuner;
    private Point displaySize;
    private Game game;
    private GameDrawer gameDrawer;
    private ControlInterface controlInterface;

    public GameBuilder(GameActivity activity, Point displaySize) {
        this.activity = activity;
        this.displaySize = displaySize;
        numberOfColumns = DEFAULT_NUMBER_OF_COLUMNS;
        numberOfRows = DEFAULT_NUMBER_OF_ROWS;
        mode = Mode.CLASSIC;
        paintTuner = new BasicStylePaintTuner();
    }

    public void build() {
        PlayingField playingField = new PlayingField(numberOfColumns, numberOfRows);
        game = new Game(activity, playingField);
        switch (mode) {
            case CLASSIC:
                buildClassicMode();
                break;
            case POLYGON:
                buildPolygonMode();
        }
    }

    private void buildClassicMode() {
        controlInterface = new ClassicControlInterface(game, displaySize);
        gameDrawer = new ClassicGameDrawer(game, displaySize, paintTuner);
    }

    private void buildPolygonMode() {
        controlInterface = new PolygonControlInterface(activity, game, displaySize);
        gameDrawer = new PolygonGameDrawer(game, controlInterface, displaySize, paintTuner);
    }

    public Game getGame() {
        return game;
    }

    public ControlInterface getControlInterface() {
        return controlInterface;
    }

    public GameDrawer getGameDrawer() {
        return gameDrawer;
    }

    public GameBuilder setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        return this;
    }

    public GameBuilder setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
        return this;
    }

    public GameBuilder setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public GameBuilder setDrawingStyle(DrawingStyle style) {
        switch (style) {
            case BASIC:
                paintTuner = new BasicStylePaintTuner();
                break;
            case BRICK:
                paintTuner = new BrickStylePaintTuner();
                break;
            case MINIMALISTIC:
                paintTuner = new MinimalisticStylePaintTuner();
        }
        return this;
    }
}
