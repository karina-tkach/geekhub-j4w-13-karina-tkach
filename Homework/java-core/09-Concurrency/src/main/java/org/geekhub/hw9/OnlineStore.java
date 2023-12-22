package org.geekhub.hw9;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class OnlineStore {
    private final Map<String, Integer> inventory;
    private final ExecutorService service;
    private final AtomicInteger sales;

    public OnlineStore(ExecutorService service) {
        this.inventory = new ConcurrentHashMap<>();
        this.service = service;
        this.sales = new AtomicInteger();
    }

    public Map<String, Integer> getInventory() {
        return new ConcurrentHashMap<>(inventory);
    }

    public synchronized void addProduct(String productName, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity should be non-negative");
        }
        inventory.merge(productName, quantity, Integer::sum);
    }

    public Future<Boolean> purchase(String productName, int quantity) {
        return service.submit(() -> {
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
