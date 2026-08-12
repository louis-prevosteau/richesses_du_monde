package core.products;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.models.Player;

public interface IProduct {

    Resource getResource();

    int getPercentage();

    int getPrice();

    Continent getContinent();

    Region getRegion();

    String getCountry();

    Player getOwner();
}
