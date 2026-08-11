package core.factories;

import core.models.ISquare;

public abstract class SquareFactory {

    public abstract ISquare createSquare(int position);
}
