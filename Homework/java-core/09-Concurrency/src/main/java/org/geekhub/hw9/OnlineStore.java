package org.geekhub.hw9;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class OnlineStore {
    private final Map<String, Integer> inventory = new ConcurrentHashMap<>();
    private final AtomicInteger sales = new AtomicInteger();

    public Map<String,Integer> getInventory(){
        return new ConcurrentHashMap<>(inventory);
    }
    public void addProduct(String productName, int quantity) {
        inventory.put(productName, quantity);
    }

    public Future<Boolean> purchase(String productName, int quantity) {
        return Executors.newSingleThreadExecutor().submit(() -> {
            synchronized (inventory) {
                int currentQuantity = inventory.getOrDefault(productName, 0);
                if (currentQuantity >= quantity) {
                    inventory.put(productName, currentQuantity - quantity);
                    sales.addAndGet(quantity);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public int getProductQuantity(String productName) {
        return inventory.getOrDefault(productName, 0);
    }

    public int getTotalSales() {
        return sales.get();
    }
}
