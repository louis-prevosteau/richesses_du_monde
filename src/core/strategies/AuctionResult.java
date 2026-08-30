package core.strategies;

import core.models.Player;

public record AuctionResult(
        Player highestBidder,
        int finalPrice
) {
}
