package com.example.listing;

import com.example.database.AccountRepository;
import com.example.database.db_classes.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountListing {
    private List<Account> accounts; // List to store accounts

    // Constructor initializes the accounts list
    public AccountListing() {
        loadAllAccounts();;
    }

    // Load all accounts from the database into the list
    public void loadAllAccounts() {
        List<Account> allAccounts = AccountRepository.getAllAccounts();
        this.accounts.clear(); // Clear the list before adding the new data
        this.accounts.addAll(allAccounts);
    }

    // Get an account by its ID
    public Account getAccountById(int idAccount) {
        // Search through the accounts list
        for (Account account : accounts) {
            if (account.getIdAccount() == idAccount) {
                return account;
            }
        }
        return null; // Return null if the account is not found
    }

    // Add a new account and update the list
    public boolean addAccount(String login, String password, String email, String phoneNumber) {
        boolean success = AccountRepository.addAccount(login, password, email, phoneNumber);
        if (success) {
            // After adding to the database, load the updated list
            loadAllAccounts();
        }
        return success;
    }

    // Get all accounts
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts); // Return a copy of the accounts list
    }

}
