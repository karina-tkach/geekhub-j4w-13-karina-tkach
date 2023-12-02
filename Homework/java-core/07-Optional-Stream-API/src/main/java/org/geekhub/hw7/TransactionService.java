package org.geekhub.hw7;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService implements TransactionAnalyzer {
    private final List<Transaction> transactions;

    public TransactionService(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> getBiggestTransactionInCategory(String category) {
        return transactions.stream()
            .filter(transaction -> transaction.category().equals(category))
            .max(Comparator.comparingDouble(Transaction::amount));
    }

    @Override
    public double getTotalSpentForDate(LocalDate date) {
        return transactions.stream()
            .filter(transaction -> transaction.date().equals(date))
            .mapToDouble(Transaction::amount)
            .sum();
    }

    @Override
    public List<Transaction> getTransactionsByCategoryAndDate(String category, LocalDate date) {
        return transactions.stream()
            .filter(transaction -> transaction.category().equals(category)
                && transaction.date().equals(date))
            .toList();
    }

    @Override
    public Map<String, Double> getSpentAmountByCategory() {
        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::category,
                Collectors.summingDouble(Transaction::amount)))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (x, y) -> x,
                LinkedHashMap::new));
    }

    @Override
    public Optional<LocalDate> getDateWithMostExpenses() {
        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::date,
                Collectors.summingDouble(Transaction::amount)))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey);
    }

    @Override
    public Map<String, Double> getAverageSpendingPerCategory() {
        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::category,
                Collectors.averagingDouble(Transaction::amount)));
    }

    @Override
    public Optional<String> getMostPopularCategory() {
        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::category,
                Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey);
    }

    @Override
    public Map<String, Double> getCategoryWiseDistribution() {
        double totalSpent = transactions.stream()
            .mapToDouble(Transaction::amount)
            .sum();

        return transactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::category,
                Collectors.summingDouble(Transaction::amount)
            ))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> (entry.getValue() / totalSpent) * 100));
    }
}
