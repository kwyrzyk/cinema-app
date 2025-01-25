package com.example.listing;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.db_classes.Account;
import com.example.exceptions.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

public class AccountListing {
    private List<Account> accounts;
    private DatabaseManager databaseManager;

    /**
     * Constructor initializes the accounts list and database manager.
     * @param dbManager the database manager
     */
    public AccountListing(DatabaseManager dbManager) {
        accounts = new ArrayList<>();
        this.databaseManager = dbManager;
    }

    /**
     * Load all accounts from the database into the list.
     */
    public void loadAllAccounts() {
        try {
            List<Account> allAccounts = AccountRepository.getAllAccounts(databaseManager.getConnection());
            this.accounts.clear();
            this.accounts.addAll(allAccounts);
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    /**
     * Get an account by its ID.
     * @param idAccount the account ID
     * @return the account with the specified ID, or null if not found
     */
    public Account getAccountById(int idAccount) {
        for (Account account : accounts) {
            if (account.getIdAccount() == idAccount) {
                return account;
            }
        }
        return null;
    }

    /**
     * Update an account by its ID.
     * @param accountId the account ID
     * @return true if the account was updated, false otherwise
     */
    public boolean updateAccount(int accountId) {
        for (int i = 0; i < this.accounts.size(); ++i) {
            if (accounts.get(i).getIdAccount() == accountId) {
                try {
                    Account acc = AccountRepository.getAccountById(accountId, databaseManager.getConnection());
                    accounts.set(i, acc);
                    return true;
                } catch (Exception e) {
                    ErrorHandler.handle(e);
                }
            }
        }
        return false;
    }

    /**
     * Get an account by its login.
     * @param login the account login
     * @return the account with the specified login, or null if not found
     */
    public Account getAccountByLogin(String login) {
        for (Account account : accounts) {
            if (account.getLogin().equals(login)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Add a new account and update the list.
     * @param login the account login
     * @param password the account password
     * @param email the account email
     * @param phoneNumber the account phone number
     * @return true if the account was added, false otherwise
     */
    public boolean addAccount(String login, String password, String email, String phoneNumber) {
        boolean success = false;
        try {
            success = AccountRepository.addAccount(login, password, email, phoneNumber, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }

        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Change the login of an account.
     * @param accountId the account ID
     * @param newLogin the new login
     * @return true if the login was changed, false otherwise
     */
    public boolean changeLogin(int accountId, String newLogin) {
        boolean success = false;
        try {
            success = AccountRepository.changeLogin(accountId, newLogin, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Change the password of an account.
     * @param accountId the account ID
     * @param newPassword the new password
     * @return true if the password was changed, false otherwise
     */
    public boolean changePassword(int accountId, String newPassword) {
        boolean success = false;
        try {
            success = AccountRepository.changePassword(accountId, newPassword, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Change the email of an account.
     * @param accountId the account ID
     * @param newEmail the new email
     * @return true if the email was changed, false otherwise
     */
    public boolean changeEmail(int accountId, String newEmail) {
        boolean success = false;
        try {
            success = AccountRepository.changeEmail(accountId, newEmail, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Change the phone number of an account.
     * @param accountId the account ID
     * @param newPhoneNumber the new phone number
     * @return true if the phone number was changed, false otherwise
     */
    public boolean changePhoneNumber(int accountId, String newPhoneNumber) {
        boolean success = false;
        try {
            success = AccountRepository.changePhone(accountId, newPhoneNumber, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Add loyalty points to an account.
     * @param accountId the account ID
     * @param points the number of points to add
     * @return true if the points were added, false otherwise
     */
    public boolean addPoints(int accountId, int points) {
        boolean success = AccountRepository.addLoyaltyPoints(accountId, points, databaseManager.getConnection());
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Take loyalty points from an account.
     * @param accountId the account ID
     * @param points the number of points to take
     * @return true if the points were taken, false otherwise
     */
    public boolean takePoints(int accountId, int points) {
        boolean success = false;
        try {
            success = AccountRepository.takeLoyaltyPoints(accountId, points, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Add balance to an account.
     * @param accountId the account ID
     * @param balance the amount of balance to add
     * @return true if the balance was added, false otherwise
     */
    public boolean addBalance(int accountId, double balance) {
        boolean success = false;
        try {
            success = AccountRepository.addBalance(accountId, balance, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Take balance from an account.
     * @param accountId the account ID
     * @param balance the amount of balance to take
     * @return true if the balance was taken, false otherwise
     */
    public boolean takeBalance(int accountId, double balance) {
        boolean success = false;
        try {
            success = AccountRepository.takeBalance(accountId, balance, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        if (success) {
            loadAllAccounts();
        }
        return success;
    }

    /**
     * Get all accounts.
     * @return a list of all accounts
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
}
