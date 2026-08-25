package core.factories;

import core.models.GoSquare;
import core.models.ISquare;

public class GoSquareFactory extends SquareFactory {

    @Override
    public ISquare createSquare(int position) {
        return new GoSquare(position);
    }
}
