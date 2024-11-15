import java.util.HashMap;
import java.util.Map;

/**
 * Bank class.
 * @author Darrel Tapilaha
 * @author Manases Villalobos
 * @version 1.0
 */

public class Bank {
    private Map<String, BankAccount> accounts;

    /**
     * Construct Bank class
     */
    public Bank()
    {
        accounts = new HashMap<>();
    }

    /**
     * Add account to accounts map method.
     * @param account = the incoming account
     */
    public void addAccount(final BankAccount account)
    {
        accounts.put(account.getAccountNumber(), account);
    }

    /**
     * Retrieve account method.
     * @param number = account number
     */
    public BankAccount retrieveAccount(final String number)
    {
        final BankAccount currentAccount;
        currentAccount = accounts.get(number);
        if(currentAccount == null){
            throw new IllegalArgumentException("Account not found");
        }

        return accounts.get(number);
    }

    /**
     * Calculate total Balance in USD method.
     * @return total balance in USD
     */
    public double totalBalanceUsd()
    {
        final double totalBalanceUSD;
        totalBalanceUSD = accounts.values()
                .stream()
                .mapToDouble(BankAccount::getBalanceUsd)
                .sum();

        return totalBalanceUSD;

    }
}
