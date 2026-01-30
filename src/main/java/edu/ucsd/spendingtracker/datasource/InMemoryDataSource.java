package edu.ucsd.spendingtracker.datasource;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.spendingtracker.model.Category;
import edu.ucsd.spendingtracker.model.Expense;

public class InMemoryDataSource implements IDataSource {
    private final List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    public InMemoryDataSource() {
    }

    @Override
    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    @Override
    public void addExpense(Expense expense) {
        int id = expense.getId();
        if (id < 0) {
            id = nextId++;
        } else if (id >= nextId) {
            nextId = id + 1;
        }
        Expense stored = new Expense(id, expense.getName(), expense.getCategory(), expense.getAmount());
        expenses.add(stored);
    }

    @Override
    public void updateExpense(Expense expense) {
        int id = expense.getId();
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                expenses.set(i, expense);
                return;
            }
        }
    }

    @Override
    public void deleteExpense(int id) {
        expenses.removeIf(expense -> expense.getId() == id);
    }

    public final static List<Expense> DEFAULT_EXPENSES = List.of(
            new Expense("Groceries", Category.FOOD, 150.75),
            new Expense("Utilities", Category.UTILITIES, 80.50),
            new Expense("Gas", Category.TRANSPORT, 60.00));

    public static InMemoryDataSource getDefaultDataSource() {
        InMemoryDataSource dataSource = new InMemoryDataSource();
        for (Expense expense : DEFAULT_EXPENSES) {
            dataSource.addExpense(expense);
        }
        return dataSource;
    }
}

