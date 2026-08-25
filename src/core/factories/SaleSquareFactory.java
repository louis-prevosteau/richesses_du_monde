package core.factories;

import core.models.ISquare;
import core.models.SaleSquare;

public class SaleSquareFactory extends SquareFactory {

    @Override
    public ISquare createSquare(int position) {
        return new SaleSquare(position);
    }
}
