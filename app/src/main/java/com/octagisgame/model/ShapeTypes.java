package com.octagisgame.model;

public class ShapeTypes {
    public static final Shape I_SHAPE = new Shape(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false}});

    public static final Shape O_SHAPE = new Shape(new boolean[][]{
            {true, true},
            {true, true}});

    public static final Shape T_SHAPE = new Shape(new boolean[][]{
            {true, true, true},
            {false, true, false}});

    public static final Shape L_SHAPE = new Shape(new boolean[][]{
            {true, false},
            {true, false},
            {true, true}});

    public static final Shape J_SHAPE = new Shape(new boolean[][]{
            {false, true},
            {false, true},
            {true, true}});

    public static final Shape S_SHAPE = new Shape(new boolean[][]{
            {false, true, true},
            {true, true, false}});

    public static final Shape Z_SHAPE = new Shape(new boolean[][]{
            {true, true, false},
            {false, true, true}});
}
