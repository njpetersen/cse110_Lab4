package edu.ucsd.spendingtracker.repository;

import edu.ucsd.spendingtracker.datasource.IDataSource;
import edu.ucsd.spendingtracker.datasource.InMemoryDataSource;
import edu.ucsd.spendingtracker.model.Category;
import edu.ucsd.spendingtracker.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseRepositoryTest {
    private ExpenseRepository repo;
    private IDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new InMemoryDataSource();
        repo = new ExpenseRepository(dataSource);
    }

    @Test
    @DisplayName("Should start with an empty list")
    void testInitialEmpty() {
        assertEquals(0, repo.getExpenses().size(), "Repository should be empty initially.");
    }

    @Test
    @DisplayName("Adding an expense should increase the list size")
    void testAddExpense() {
        Expense coffee = new Expense("Coffee", Category.FOOD, 5.50);
        repo.addExpense(coffee);

        List<Expense> expenses = repo.getExpenses();
        assertEquals(1, expenses.size());
        assertEquals("Coffee", expenses.get(0).getName());
        assertEquals(5.50, expenses.get(0).getAmount());
    }

    @Test
    @DisplayName("Deleting an expense should remove it from the list")
    void testDeleteExpense() {
        repo.addExpense(new Expense("Rent", Category.OTHER, 1200.00));
        Expense toDelete = repo.getExpenses().get(0);
        int id = toDelete.getId();
        repo.deleteExpense(id);
        assertEquals(0, repo.getExpenses().size(), "List should be empty after deletion.");
    }

    @Test
    @DisplayName("Should handle multiple expenses correctly")
    void testMultipleExpenses() {
        repo.addExpense(new Expense("Gas", Category.TRANSPORT, 40.00));
        repo.addExpense(new Expense("Movie", Category.ENTERTAINMENT, 15.00));
        repo.addExpense(new Expense("Apples", Category.FOOD, 4.00));

        assertEquals(3, repo.getExpenses().size());
    }
}