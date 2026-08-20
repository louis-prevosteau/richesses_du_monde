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

    public Map<Region, List<IProduct>> getProducts(
            Continent continent,
            Region region) {

        Map<Region, List<IProduct>> result = new HashMap<>();

        for (Map.Entry<Region, List<IProduct>> entry : products.entrySet()) {

            List<IProduct> filtered = entry.getValue()
                    .stream()
                    .filter(product ->
                            continent != null
                                    ? continent.equals(product.getContinent())
                                    : region == null || region.equals(product.getRegion()))
                    .toList();

            if (!filtered.isEmpty()) {
                result.put(entry.getKey(), filtered);
            }
        }

        return result;
    }

    public void addProduct(IProduct product) {
        products
                .computeIfAbsent(product.getRegion(), k -> new ArrayList<>())
                .add(product);
    }

    public void removeProduct(IProduct product) {
        List<IProduct> regionProducts = products.get(product.getRegion());

        if (regionProducts != null) {
            regionProducts.remove(product);

            if (regionProducts.isEmpty()) {
                products.remove(product.getRegion());
            }
        }
    }

    public void returnsProducts(IProduct product) {
        products
                .computeIfAbsent(product.getRegion(), k -> new ArrayList<>())
                .add(product);
        System.out.println("-> Le titre a été remis dans le magasin.");
    }
}
