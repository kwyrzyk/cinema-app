package com.example.listing;

import com.example.database.AccountRepository;
import com.example.database.db_classes.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountListing {
    private List<Account> accounts; // List to store accounts

    // Constructor initializes the accounts list
    public AccountListing() {
        accounts = new ArrayList<>();
        //loadAllAccounts();
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

    public Account getAccountByLogin(String login) {
        // Search through the accounts list
        for (Account account : accounts) {
            if (account.getLogin().equals(login)) {
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

    public boolean changeLogin(int accountId, String newLogin) {
        // Call the repository method to update the login
        boolean success = AccountRepository.changeLogin(accountId, newLogin);
        if (success) {
            // Reload the updated list of accounts if the login change is successful
            loadAllAccounts();
        }
        return success;
    }

    public boolean changePassword(int accountId, String newPassword) {
        // Call the repository method to update the password
        boolean success = AccountRepository.changePassword(accountId, newPassword);
        if (success) {
            // Reload the updated list of accounts if the password change is successful
            loadAllAccounts();
        }
        return success;
    }

    public boolean changeEmail(int accountId, String newEmail) {
        // Call the repository method to update the email
        boolean success = AccountRepository.changeEmail(accountId, newEmail);
        if (success) {
            // Reload the updated list of accounts if the email change is successful
            loadAllAccounts();
        }
        return success;
    }
    
    public boolean changePhoneNumber(int accountId, String newPhoneNumber) {
        // Call the repository method to update the phone number
        boolean success = AccountRepository.changePhone(accountId, newPhoneNumber);
        if (success) {
            // Reload the updated list of accounts if the phone number change is successful
            loadAllAccounts();
        }
        return success;
    }
        
    public boolean addPoints(int accountId, int points) {
        // Call the repository method to add points
        boolean success = AccountRepository.addLoyaltyPoints(accountId, points);
        if (success) {
            // Reload the updated list of accounts if adding points was successful
            loadAllAccounts();
        }
        return success;
    }
    
    public boolean takePoints(int accountId, int points) {
        // Call the repository method to add points
        boolean success = AccountRepository.takeLoyaltyPoints(accountId, points);
        if (success) {
            // Reload the updated list of accounts if adding points was successful
            loadAllAccounts();
        }
        return success;
    }

    public boolean addBalance(int accountId, double balance) {
        // Call the repository method to add points
        boolean success = AccountRepository.addBalance(accountId, balance);
        if (success) {
            // Reload the updated list of accounts if adding points was successful
            loadAllAccounts();
        }
        return success;
    }
    
    public boolean takeBalance(int accountId, double balance) {
        // Call the repository method to add points
        boolean success = AccountRepository.takeBalance(accountId, balance);
        if (success) {
            // Reload the updated list of accounts if adding points was successful
            loadAllAccounts();
        }
        return success;
    }


    // Get all accounts
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts); // Return a copy of the accounts list
    }

}
