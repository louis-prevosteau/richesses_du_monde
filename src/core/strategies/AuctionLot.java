package core.strategies;

import core.enums.Resource;
import core.products.IProduct;

import java.util.List;

public record AuctionLot(
        Resource resource,
        List<IProduct> products,
        int startingPrice
) {
}
