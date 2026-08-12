package core.products;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.models.Player;

public class Product implements IProduct {

    private Resource resource;
    private int percentage, price;
    private Continent continent;
    private Region region;
    private String country;
    private Player owner;

    public Product(Resource resource, int percentage, int price, Continent continent, Region region, String country) {
        this.resource = resource;
        this.percentage = percentage;
        this.price = price;
        this.continent = continent;
        this.region = region;
        this.country = country;
        this.owner = null;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Continent getContinent() {
        return continent;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public Player getOwner() {
        return owner;
    }
}
