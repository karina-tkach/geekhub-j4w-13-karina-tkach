package org.geekhub.hw7;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceTest {
    private static List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(10.0, "Food", LocalDate.of(2023, 1, 1)));
        transactions.add(new Transaction(15.0, "Food", LocalDate.of(2023, 1, 2)));
        transactions.add(new Transaction(20.0, "Shopping", LocalDate.of(2023, 1, 1)));
        transactions.add(new Transaction(25.0, "Shopping", LocalDate.of(2023, 1, 2)));
        return transactions;
    }

    @Test
    void getBiggestTransactionInCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Optional<Transaction> biggestFoodTransaction = service.getBiggestTransactionInCategory("Food");
        assertThat(biggestFoodTransaction).isPresent();
        assertThat(biggestFoodTransaction.get().amount()).isEqualTo(15.0);
    }

    @Test
    void getBiggestTransactionInCategory_absentCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Optional<Transaction> biggestNonPresentTransaction = service.getBiggestTransactionInCategory("Car");
        assertThat(biggestNonPresentTransaction).isEmpty();
    }

    @Test
    void getBiggestTransactionInCategory_emptyList() {
        List<Transaction> transactions = new ArrayList<>();
        TransactionService service = new TransactionService(transactions);

        Optional<Transaction> biggestTransaction = service.getBiggestTransactionInCategory("Car");
        assertThat(biggestTransaction).isEmpty();
    }

    @Test
    void getTotalSpentForDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        double totalSpentOnDate = service.getTotalSpentForDate(LocalDate.of(2023, 1, 1));
        assertThat(totalSpentOnDate).isEqualTo(30.0);
    }

    @Test
    void getTotalSpentForDate_absentDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        double totalSpentOnAbsentDate = service.getTotalSpentForDate(LocalDate.of(2023, 1, 3));
        assertThat(totalSpentOnAbsentDate).isEqualTo(0.0);
    }

    @Test
    void getTotalSpentForDate_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        double totalSpentOnEmptyList = service.getTotalSpentForDate(LocalDate.of(2023, 1, 3));
        assertThat(totalSpentOnEmptyList).isEqualTo(0.0);
    }

    @Test
    void getTransactionsByCategoryAndDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> foodTransactionsOnDate = service.getTransactionsByCategoryAndDate("Food", LocalDate.of(2023, 1, 1));
        assertThat(foodTransactionsOnDate).hasSize(1);
        assertThat(foodTransactionsOnDate.get(0).amount()).isEqualTo(10.0);
    }

    @Test
    void getTransactionsByCategoryAndDate_absentCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Car", LocalDate.of(2022, 1, 1));
        assertThat(absentTransactions).isEmpty();
    }

    @Test
    void getTransactionsByCategoryAndDate_absentDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Food", LocalDate.of(2024, 1, 3));
        assertThat(absentTransactions).isEmpty();
    }

    @Test
    void getTransactionsByCategoryAndDate_absentBoth() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Car", LocalDate.of(2022, 1, 3));
        assertThat(absentTransactions).isEmpty();
    }

    @Test
    void getTransactionsByCategoryAndDate_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Food", LocalDate.of(2023, 1, 1));
        assertThat(absentTransactions).isEmpty();
    }

    @Test
    void getSpentAmountByCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> spentAmountByCategory = service.getSpentAmountByCategory();

        assertThat(spentAmountByCategory).hasSize(2)
            .containsEntry("Food",25.0)
            .containsEntry("Shopping",45.0);
    }

    @Test
    void getSpentAmountByCategory_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> spentAmountByCategory = service.getSpentAmountByCategory();
        assertThat(spentAmountByCategory).isEmpty();
    }

    @Test
    void getSpentAmountByCategory_descendingOrder() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> spentAmountByCategory = service.getSpentAmountByCategory();
        Map<String, Double> expectedMap = new LinkedHashMap<>();
        expectedMap.put("Shopping", 45.0);
        expectedMap.put("Food", 25.0);

        assertThat(spentAmountByCategory).isEqualTo(expectedMap);
    }

    @Test
    void getDateWithMostExpenses() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Optional<LocalDate> dateWithMostExpenses = service.getDateWithMostExpenses();
        assertTrue(dateWithMostExpenses.isPresent(), "Expected a date to be present.");
        assertEquals(LocalDate.of(2023, 1, 2), dateWithMostExpenses.get(), "The date with the most expenses was incorrect.");
    }

    @Test
    void getDateWithMostExpenses_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        Optional<LocalDate> emptyDateWithMostExpenses = service.getDateWithMostExpenses();
        assertTrue(emptyDateWithMostExpenses.isEmpty(), "Expected no date to be present for an empty list of transactions.");
    }

    @Test
    void getAverageSpendingPerCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> averageSpendingPerCategory = service.getAverageSpendingPerCategory();

        assertEquals(2, averageSpendingPerCategory.size(), "The size of the average spending per category map was incorrect.");

        assertEquals(12.5, averageSpendingPerCategory.get("Food"), "The average spending for the 'Food' category was incorrect.");
        assertEquals(22.5, averageSpendingPerCategory.get("Shopping"), "The average spending for the 'Shopping' category was incorrect.");
    }

    @Test
    void getAverageSpendingPerCategory_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> averageSpendingPerCategory = service.getAverageSpendingPerCategory();

        assertTrue(averageSpendingPerCategory.isEmpty(), "Expected no data to be present for an empty list of transactions.");
    }

    @Test
    void getMostPopularCategory() {
        List<Transaction> transactions = getTransactions();
        TransactionService service = new TransactionService(transactions);

        Optional<String> mostPopularCategory = service.getMostPopularCategory();
        assertTrue(mostPopularCategory.isPresent(), "Expected a category to be present.");
        assertEquals("Shopping", mostPopularCategory.get(), "The most popular category was incorrect.");
    }

    @Test
    void getMostPopularCategory_emptyList() {
        List<Transaction> transactions = new ArrayList<>();
        TransactionService service = new TransactionService(transactions);

        Optional<String> emptyMostPopularCategory = service.getMostPopularCategory();
        assertTrue(emptyMostPopularCategory.isEmpty(), "Expected no category to be present for an empty list of transactions.");
    }

    @Test
    void getCategoryWiseDistribution() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> categoryWiseDistribution = service.getCategoryWiseDistribution();

        assertEquals(2, categoryWiseDistribution.size(), "The size of the category-wise distribution map was incorrect.");

        assertEquals(36, Math.round(categoryWiseDistribution.get("Food")), "The category-wise distribution for the 'Food' category was incorrect.");
        assertEquals(64, Math.round(categoryWiseDistribution.get("Shopping")), "The category-wise distribution for the 'Shopping' category was incorrect.");
    }

    @Test
    void getCategoryWiseDistribution_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> categoryWiseDistribution = service.getCategoryWiseDistribution();

        assertTrue(categoryWiseDistribution.isEmpty(), "Expected no data to be present for an empty list of transactions.");
    }
}
