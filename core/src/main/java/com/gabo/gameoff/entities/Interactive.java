package com.gabo.gameoff.entities;

import com.badlogic.gdx.math.Rectangle;

public interface Interactive {

    public Rectangle getInteractArea();
    public void interact();
}