package org.geekhub.hw9;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StockManager {
    private final OnlineStore onlineStore;
    private final ScheduledExecutorService scheduler;
    private final int restockInterval;
    private final int quantityToAdd;

    public StockManager(OnlineStore onlineStore, int restockInterval, int quantityToAdd) {
        this.onlineStore = onlineStore;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.restockInterval = restockInterval;
        this.quantityToAdd = quantityToAdd;

        scheduleRestocking();
    }
    private void scheduleRestocking() {
        scheduler.scheduleAtFixedRate(() -> {
            Map<String, Integer> inventory = onlineStore.getInventory();
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                onlineStore.addProduct(entry.getKey(), quantityToAdd);
            }
        }, restockInterval, restockInterval, TimeUnit.SECONDS);
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public void shutdown() throws InterruptedException {
        scheduler.shutdown();
        scheduler.awaitTermination(1, TimeUnit.SECONDS);
    }
}
