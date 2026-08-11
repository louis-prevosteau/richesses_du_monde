package core.models;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;

public interface IProduct {

    Resource getResource();

    int getPercentage();

    int getPrice();

    Continent getContinent();

    Region getRegion();

    String getCountry();
}
