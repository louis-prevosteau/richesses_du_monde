package core.factories;

import core.models.CollectSquare;
import core.models.ISquare;

public class CollectSquareFactory extends SquareFactory {

    @Override
    public ISquare createSquare(int position) {
        return new CollectSquare(position);
    }
}
