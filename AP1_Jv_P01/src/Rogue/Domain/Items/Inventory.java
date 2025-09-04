package Domain.Items;

import Constants.GameConstants;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public Inventory() {
        this.items = new ArrayList<>();

    }

    public boolean addItem(Item item) {
        if (item == null)
            return false;

        if (items.size() < GameConstants.MAX_SPACE) {
            items.add(item);
            return true;
        }

        return false;
    }

    public boolean removeItem(Item item) {
        if (item == null) {
            return false;
        }

        if (items.contains(item)) {
            items.remove(item);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Backpack(");
        sb.append(items.size()).append("/").append(GameConstants.MAX_SPACE).append("): ");
        sb.append(items.toString());
        return sb.toString();
    }
}
