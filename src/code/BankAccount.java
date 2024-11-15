/**
 * BankAccount class.
 * @author Darrel Tapilaha
 * @author Manases Villalobos
 * @version 1.0
 */

public class BankAccount {
    private final String accountNumber;
    private double balance;

    private static final int NOTHING = 0;

    /**
     * Construct BankAccount class.
     * @param accountNumber = account number
     * @param balance = balance
     */
    public BankAccount(final String accountNumber,
                       final int    balance)
    {
        validateAccountNumber(accountNumber);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    /*
     * method to validate account number
     * throws error if account number is empty or blank
     */
    private void validateAccountNumber(final String accountNumber)
    {
        if(accountNumber.isEmpty() || accountNumber.isBlank())
        {
            throw new IllegalArgumentException("Invalid account number: " + accountNumber);
        }
    }

    /**
     * Deposit money method.
     * @param deposit = amount to deposit
     */
    public void deposit(final double deposit)
    {
        if(deposit <= NOTHING){
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += deposit;
    }

    /**
     * Get balance in USD.
     * @return current balance
     */
    public double getBalanceUsd()
    {
        return this.balance;
    }

    /**
     * Withdraw balance.
     * @param amount = amount to withdraw
     */
    public void withdraw(final double amount)
    {
        if(amount < NOTHING){
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(amount > this.balance){
            throw new IllegalArgumentException("Insufficient funds");
        } else {
            this.balance -= amount;
        }
    }

    /**
     * Transfer to bank method.
     * @param targetAccount = Bank Account to transfer to
     * @param sourceAccount = Incoming account
     * @param amount = total amount to transfer
     */
    public void transferToBank(final BankAccount targetAccount,
                               final String      sourceAccount,
                               final double      amount )
    {
        final String currentAccountNumber;
        currentAccountNumber = targetAccount.getAccountNumber();
        if(currentAccountNumber.equals(sourceAccount)){
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        this.withdraw(amount);
        targetAccount.deposit(amount);
    }

    /**
     * Get account number.
     * @return current account number
     */
    public String getAccountNumber()
    {
        return accountNumber;
    }
}
