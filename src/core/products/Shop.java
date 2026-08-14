package core.products;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {

    private Map<Region, List<IProduct>> products;

    public Shop() {
        this.products = new HashMap<>();
    }

    public Map<Region, List<IProduct>> getProducts(Continent continent, Region region) {
        Map<Region, List<IProduct>> copy = new HashMap<>();
        List<IProduct> productEntries;
        if (continent != null) {
            for (Map.Entry<Region, List<IProduct>> entry : products.entrySet()) {
                productEntries = entry.getValue()
                        .stream().filter(product -> product.getContinent().equals(continent))
                        .toList();
                if (!productEntries.isEmpty())
                    copy.put(entry.getKey(), productEntries);
            }
        }
        else if (region != null) {
            for (Map.Entry<Region, List<IProduct>> entry : products.entrySet()) {
                productEntries = entry.getValue()
                        .stream().filter(product -> product.getRegion().equals(region))
                        .toList();
                if (!productEntries.isEmpty())
                    copy.put(entry.getKey(), productEntries);
            }
        }
        else
            for (Map.Entry<Region, List<IProduct>> entry : products.entrySet()) {
                productEntries = entry.getValue();
                if (!productEntries.isEmpty())
                    copy.put(entry.getKey(), productEntries);
            }
        return copy;
    }

    public void addProduct(IProduct product) {
        products
                .computeIfAbsent(product.getRegion(), k -> new ArrayList<>())
                .add(product);
    }

    public void returnsProducts(IProduct product) {
        products
                .computeIfAbsent(product.getRegion(), k -> new ArrayList<>())
                .add(product);
    }
}
