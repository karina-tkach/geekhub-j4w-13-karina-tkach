package org.geekhub.hw7;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        assertTrue(biggestFoodTransaction.isPresent(), "Expected a transaction to be present.");
        assertEquals(15.0, biggestFoodTransaction.get().amount(), "The biggest transaction in the 'Food' category was incorrect.");
    }

    @Test
    void getBiggestTransactionInCategory_absentCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Optional<Transaction> biggestNonPresentTransaction = service.getBiggestTransactionInCategory("Car");
        assertTrue(biggestNonPresentTransaction.isEmpty(), "Expected no transaction to be present for a non-present category.");
    }

    @Test
    void getBiggestTransactionInCategory_emptyList() {
        List<Transaction> transactions = new ArrayList<>();
        TransactionService service = new TransactionService(transactions);

        Optional<Transaction> biggestTransaction = service.getBiggestTransactionInCategory("Car");
        assertTrue(biggestTransaction.isEmpty(), "Expected no transaction to be present for an empty list");
    }

    @Test
    void getTotalSpentForDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        double totalSpentOnDate = service.getTotalSpentForDate(LocalDate.of(2023, 1, 1));
        assertEquals(30.0, totalSpentOnDate, "The total spending for the date 2023-01-01 was incorrect.");
    }

    @Test
    void getTotalSpentForDate_absentDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        double totalSpentOnAbsentDate = service.getTotalSpentForDate(LocalDate.of(2023, 1, 3));
        assertEquals(0.0, totalSpentOnAbsentDate, "The total spending for a non-present date was incorrect.");
    }

    @Test
    void getTotalSpentForDate_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        double totalSpentOnEmptyList = service.getTotalSpentForDate(LocalDate.of(2023, 1, 3));
        assertEquals(0.0, totalSpentOnEmptyList, "The total spending for an empty list was incorrect.");
    }

    @Test
    void getTransactionsByCategoryAndDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> foodTransactionsOnDate = service.getTransactionsByCategoryAndDate("Food", LocalDate.of(2023, 1, 1));
        assertEquals(1, foodTransactionsOnDate.size(), "The number of transactions for the 'Food' category and the date 2023-01-01 was incorrect.");
        assertEquals(10.0, foodTransactionsOnDate.get(0).amount(), "The transaction amount for the 'Food' category and the date 2023-01-01 was incorrect.");
    }

    @Test
    void getTransactionsByCategoryAndDate_absentCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Car", LocalDate.of(2022, 1, 1));
        assertTrue(absentTransactions.isEmpty(), "Expected no transactions to be present for an absent category.");
    }

    @Test
    void getTransactionsByCategoryAndDate_absentDate() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Food", LocalDate.of(2024, 1, 3));
        assertTrue(absentTransactions.isEmpty(), "Expected no transactions to be present for an absent date.");
    }

    @Test
    void getTransactionsByCategoryAndDate_absentBoth() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Car", LocalDate.of(2022, 1, 3));
        assertTrue(absentTransactions.isEmpty(), "Expected no transactions to be present for an absent category and date.");
    }

    @Test
    void getTransactionsByCategoryAndDate_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        List<Transaction> absentTransactions = service.getTransactionsByCategoryAndDate("Food", LocalDate.of(2023, 1, 1));
        assertTrue(absentTransactions.isEmpty(), "Expected no transactions to be present for an empty list.");
    }

    @Test
    void getSpentAmountByCategory() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> spentAmountByCategory = service.getSpentAmountByCategory();

        assertEquals(2, spentAmountByCategory.size(), "The size of the spent amount by category map was incorrect.");

        assertEquals(25.0, spentAmountByCategory.get("Food"), "The spent amount for the 'Food' category was incorrect.");
        assertEquals(45.0, spentAmountByCategory.get("Shopping"), "The spent amount for the 'Shopping' category was incorrect.");
    }

    @Test
    void getSpentAmountByCategory_emptyList() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> spentAmountByCategory = service.getSpentAmountByCategory();

        assertTrue(spentAmountByCategory.isEmpty(), "The spent amount by category map for an empty list was incorrect.");
    }

    @Test
    void getSpentAmountByCategory_descendingOrder() {
        List<Transaction> transactions = getTransactions();

        TransactionService service = new TransactionService(transactions);

        Map<String, Double> spentAmountByCategory = service.getSpentAmountByCategory();
        Map<String, Double> expectedMap = new LinkedHashMap<>();
        expectedMap.put("Shopping", 45.0);
        expectedMap.put("Food", 25.0);

        assertEquals(expectedMap, spentAmountByCategory, "The spent amount by category map was in wrong order.");
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
