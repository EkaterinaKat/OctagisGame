package com.octagisgame.builders;

import android.graphics.Point;

import com.octagisgame.activities.GameActivity;
import com.octagisgame.controller.ClassicControlInterface;
import com.octagisgame.controller.ControlInterface;
import com.octagisgame.controller.Game;
import com.octagisgame.controller.PolygonControlInterface;
import com.octagisgame.drawers.ClassicFieldDrawer;
import com.octagisgame.drawers.FieldDrawer;
import com.octagisgame.drawers.PolygonFieldDrawer;
import com.octagisgame.model.PlayingField;
import com.octagisgame.stylers.BasicStyler;
import com.octagisgame.stylers.Styler;

public class GameBuilder {
    public enum Mode {CLASSIC, POLYGON}

    private final int DEFAULT_NUMBER_OF_COLUMNS = 15;
    private final int DEFAULT_NUMBER_OF_ROWS = 15;
    private GameActivity activity;
    private int numberOfColumns;
    private int numberOfRows;
    private Mode mode;
    private Styler styler;
    private Point displaySize;
    private Game game;
    private FieldDrawer fieldDrawer;
    private ControlInterface controlInterface;
    PlayingField playingField;

    public GameBuilder(GameActivity activity, Point displaySize) {
        this.activity = activity;
        this.displaySize = displaySize;
        numberOfColumns = DEFAULT_NUMBER_OF_COLUMNS;
        numberOfRows = DEFAULT_NUMBER_OF_ROWS;
        mode = Mode.CLASSIC;
        styler = new BasicStyler();
    }

    public void build() {
        playingField = new PlayingField(numberOfColumns, numberOfRows);
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
        controlInterface = new ClassicControlInterface(game);
        fieldDrawer = new ClassicFieldDrawer(playingField, displaySize, styler);
    }

    private void buildPolygonMode() {
        controlInterface = new PolygonControlInterface(game, displaySize);
        fieldDrawer = new PolygonFieldDrawer(playingField, controlInterface, displaySize, styler);
    }

    public Game getGame() {
        return game;
    }

    public ControlInterface getControlInterface() {
        return controlInterface;
    }

    public FieldDrawer getFieldDrawer() {
        return fieldDrawer;
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

    public GameBuilder setStyler(Styler styler) {
        this.styler = styler;
        return this;
    }
}
