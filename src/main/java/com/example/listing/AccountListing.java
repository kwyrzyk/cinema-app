package com.example.listing;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.db_classes.Account;
import com.example.exceptions.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

public class AccountListing {
    private List<Account> accounts; // List to store accounts
    private DatabaseManager databaseManager;

    // Constructor initializes the accounts list
    public AccountListing(DatabaseManager dbManager) {
        accounts = new ArrayList<>();
        this.databaseManager = dbManager;
        //loadAllAccounts();
    }

    // Load all accounts from the database into the list
    public void loadAllAccounts() {
        try{
            List<Account> allAccounts = AccountRepository.getAllAccounts(databaseManager.getConnection());
            this.accounts.clear(); // Clear the list before adding the new data
            this.accounts.addAll(allAccounts);
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
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


    public boolean updateAccount(int accountId){
        for(int i = 0; i < this.accounts.size() ; ++i){
            if(accounts.get(i).getIdAccount() == accountId){
                try{
                Account acc = AccountRepository.getAccountById(accountId, databaseManager.getConnection());
                accounts.set(i, acc);
                return true;
                }catch (Exception e){
                    ErrorHandler.handle(e);
                }
            }
        }
        return false;
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
        boolean success = false;
        try{
            success = AccountRepository.addAccount(login, password, email, phoneNumber, databaseManager.getConnection());
        }catch( Exception e){
            ErrorHandler.handle(e);
        }
        
        if (success) {
            // After adding to the database, load the updated list
            loadAllAccounts();
        }
        return success;
    }

    public boolean changeLogin(int accountId, String newLogin) {
        boolean success = false;
        try{
            success = AccountRepository.changeLogin(accountId, newLogin, databaseManager.getConnection());
        }catch (Exception e){
            ErrorHandler.handle(e);
        }
        if (success) {
            // Reload the updated list of accounts if the login change is successful
            loadAllAccounts();
        }
        return success;
    }

    public boolean changePassword(int accountId, String newPassword) {
        boolean success = false;
        // Call the repository method to update the password
        try{
            success = AccountRepository.changePassword(accountId, newPassword, databaseManager.getConnection());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
        if (success) {
            // Reload the updated list of accounts if the password change is successful
            loadAllAccounts();
        }
        return success;
    }

    public boolean changeEmail(int accountId, String newEmail) {
        // Call the repository method to update the email
        boolean success = false;
        try{
            success = AccountRepository.changeEmail(accountId, newEmail, databaseManager.getConnection());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
        if (success) {
            // Reload the updated list of accounts if the email change is successful
            loadAllAccounts();
        }
        return success;
    }
    
    public boolean changePhoneNumber(int accountId, String newPhoneNumber) {
        // Call the repository method to update the phone number
        boolean success = false;
        try{
        success = AccountRepository.changePhone(accountId, newPhoneNumber, databaseManager.getConnection());
        }catch (Exception e){
            ErrorHandler.handle(e);
        }
        if (success) {
            // Reload the updated list of accounts if the phone number change is successful
            loadAllAccounts();
        }
        return success;
    }
        
    public boolean addPoints(int accountId, int points) {
        // Call the repository method to add points
        boolean success = AccountRepository.addLoyaltyPoints(accountId, points, databaseManager.getConnection());
        if (success) {
            // Reload the updated list of accounts if adding points was successful
            loadAllAccounts();
        }
        return success;
    }
    
    public boolean takePoints(int accountId, int points) {
        // Call the repository method to add points
        boolean success = false;
        try{
            success = AccountRepository.takeLoyaltyPoints(accountId, points, databaseManager.getConnection());
        }catch (Exception e){
            ErrorHandler.handle(e);
        }
        if (success) {
            // Reload the updated list of accounts if adding points was successful
            loadAllAccounts();
        }
        return success;
    }

    public boolean addBalance(int accountId, double balance) {
        // Call the repository method to add points
        boolean success = false;
        try{
            success = AccountRepository.addBalance(accountId, balance, databaseManager.getConnection());
        }catch (Exception e){
            ErrorHandler.handle(e);
        }
        if (success) {
            // Reload the updated list of accounts if adding points was successful
            loadAllAccounts();
        }
        return success;
    }
    
    public boolean takeBalance(int accountId, double balance) {
        // Call the repository method to add points
        boolean success = false;
        try{
        success = AccountRepository.takeBalance(accountId, balance, databaseManager.getConnection());
        }catch(Exception e){
            ErrorHandler.handle(e);
        }
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
