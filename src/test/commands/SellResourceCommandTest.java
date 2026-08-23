package test.commands;

import core.commands.SellResourceCommand;
import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;
import core.products.Product;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SellResourceCommandTest {

    private Player seller;
    private Player buyer;

    private IProduct product1;
    private IProduct product2;

    @BeforeEach
    void setUp() {

        GameManager.getInstance().reset();

        seller = new Player("Alice");
        buyer = new Player("Bob");

        GameManager.getInstance().addPlayer(seller);
        GameManager.getInstance().addPlayer(buyer);

        product1 = new Product(
                Resource.PETROLE,
                10,
                1_000_000,
                Continent.ASIA_OCEANIA,
                Region.MOYEN_ORIENT,
                "Qatar"
        );

        product2 = new Product(
                Resource.PETROLE,
                20,
                2_000_000,
                Continent.ASIA_OCEANIA,
                Region.MOYEN_ORIENT,
                "Arabie Saoudite"
        );

        seller.addProperty(product1);
        seller.addProperty(product2);
    }

    @Test
    @DisplayName("canExecute retourne false si aucun produit")
    void testCanExecuteFalse() {

        SellResourceCommand command =
                new SellResourceCommand(
                        seller,
                        List.of(),
                        1_000_000,
                        new Scanner(
                                new ByteArrayInputStream(
                                        new byte[0]
                                )
                        )
                );

        assertFalse(command.canExecute());
    }

    @Test
    @DisplayName("canExecute retourne true si produits présents")
    void testCanExecuteTrue() {

        SellResourceCommand command =
                new SellResourceCommand(
                        seller,
                        List.of(product1),
                        1_000_000,
                        new Scanner(
                                new ByteArrayInputStream(
                                        new byte[0]
                                )
                        )
                );

        assertTrue(command.canExecute());
    }

    @Test
    @DisplayName("Les produits retournent au magasin si aucune enchère")
    void testNoBidReturnsProductsToShop() {

        Scanner scanner =
                new Scanner(
                        new ByteArrayInputStream(
                                "0\n".getBytes()
                        )
                );

        SellResourceCommand command =
                new SellResourceCommand(
                        seller,
                        List.of(product1, product2),
                        1_500_000,
                        scanner
                );

        command.execute();

        assertNull(product1.getOwner());
        assertNull(product2.getOwner());

        assertFalse(
                seller.getProperties()
                        .getOrDefault(
                                Resource.PETROLE,
                                List.of()
                        )
                        .contains(product1)
        );

        assertFalse(
                seller.getProperties()
                        .getOrDefault(
                                Resource.PETROLE,
                                List.of()
                        )
                        .contains(product2)
        );
    }

    @Test
    @DisplayName("Le plus offrant remporte les produits")
    void testAuctionWinnerGetsProducts() {

        int sellerMoney = seller.getMoney();
        int buyerMoney = buyer.getMoney();

        Scanner scanner =
                new Scanner(
                        new ByteArrayInputStream(
                                "2000000\n0\n".getBytes()
                        )
                );

        SellResourceCommand command =
                new SellResourceCommand(
                        seller,
                        List.of(product1, product2),
                        1_500_000,
                        scanner
                );

        command.execute();

        assertEquals(buyer, product1.getOwner());
        assertEquals(buyer, product2.getOwner());

        assertTrue(
                buyer.getProperties()
                        .get(Resource.PETROLE)
                        .contains(product1)
        );

        assertTrue(
                buyer.getProperties()
                        .get(Resource.PETROLE)
                        .contains(product2)
        );

        assertEquals(
                sellerMoney + 2_000_000,
                seller.getMoney()
        );

        assertEquals(
                buyerMoney - 2_000_000,
                buyer.getMoney()
        );
    }
}
