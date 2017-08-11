import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by daniel.graef on 8/11/17.
 */
public class CheckoutTest {

    @Test
    public void a_item_can_be_added_to_the_supermarket() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item(0, "name"));
        Supermarket supermarket = new Supermarket(items);

        assertThat(supermarket.getItems(), is(notNullValue()));
    }

    @Test
    public void an_item_should_have_a_name() throws Exception {
        Item itemA = new Item(0, "A");

        assertThat(itemA.getName(), is(equalTo("A")));
    }

    @Test
    public void multiple_items_can_be_added_to_the_supermarket() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item(0, "name"));
        items.add(new Item(0, "name"));

        Supermarket supermarket = new Supermarket(items);
        assertThat(supermarket.getItems().size(), is(equalTo(2)));
    }

    @Test
    public void can_work_total_of_all_items_bought_when_all_items_have_no_value() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item(0, "name"));
        items.add(new Item(0, "name"));

        Supermarket supermarket = new Supermarket(items);

        for (Item item : items) {
            supermarket.buy(item);
        }

        assertThat(supermarket.total(), is(equalTo(0)));
    }

    @Test
    public void can_work_total_of_all_items_bought_when_items_have_value() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item(50, "A"));
        items.add(new Item(30, "B"));

        Supermarket supermarket = new Supermarket(items);

        for (Item item : items) {
            supermarket.buy(item);
        }

        assertThat(supermarket.total(), is(equalTo(80)));
    }

    @Test
    public void an_item_with_a_special_price_should_have_its_reduction_applied() throws Exception {
        List<Item> items = new ArrayList<>();
        Item itemA = new Item(50, 3, 130, "A");
        Item itemC = new Item(20, "C");
        items.add(itemA);
        items.add(itemC);

        Supermarket supermarket = new Supermarket(items);
        supermarket.buy(itemA);
        supermarket.buy(itemA);
        supermarket.buy(itemA);
        supermarket.buy(itemC);

        assertThat(supermarket.total(), is(equalTo(150)));
    }

    class Item {
        private final int price;
        private final int discountThreshold;
        private final int discountPrice;
        private final String name;

        public Item(int price, int discountThreshold, int discountPrice, String name) {
            this.price = price;
            this.discountThreshold = discountThreshold;
            this.discountPrice = discountPrice;
            this.name = name;
        }

        public Item(int price, String name) {
            this(price, 0, 0, name);
        }

        public int getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }
    }

    class Supermarket {
        private List<Item> items;
        private List<Item> boughtItems;

        public Supermarket(List items) {
            this.items = items;
            boughtItems = new ArrayList<Item>();
        }

        public List<Item> getItems() {
            return items;
        }

        public int total() {
            int total = 0;

            List<Item> possibleDiscountedItems = new ArrayList<>();

            for(Item item : boughtItems) {

                if (item.discountThreshold > 0) {
                    possibleDiscountedItems.add(item);
                }

                total += item.getPrice();
            }

            return total;
        }

        public void buy(Item item) {
            boughtItems.add(item);
        }
    }

}
