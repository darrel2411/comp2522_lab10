import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
public class BankApplicationTests {
    private Bank bank1;
    private Bank bank2;

    private BankAccount account1;
    private BankAccount account2;
    @BeforeEach
    void setUp() {
        bank1 = new Bank();
        bank2 = new Bank();
//        bank3 = new Bank();
//        bank4 = new Bank();
        account1 = new BankAccount("12345", 1000);
        account2 = new BankAccount("67890", 500);
//        account3 = new BankAccount("    ", 1000);
//        account4 = new BankAccount("", 1000);
        bank1.addAccount(account1);
        bank2.addAccount(account2);
//        bank3.addAccount(account3);
//        bank4.addAccount(account4);
    }
    @Test
    void depositIncreasesBalanceAndVerify() {
        account1.deposit(200);
        assertEquals(1200, account1.getBalanceUsd());
        account2.deposit(300);
        assertEquals(800, account2.getBalanceUsd());
    }
    @Test
    void withdrawDecreasesBalanceAndVerify() {
        account1.withdraw(200);
        assertEquals(800, account1.getBalanceUsd());
        account2.withdraw(100);
        assertEquals(400, account2.getBalanceUsd());
    }
    @Test
    void cannotWithdrawMoreThanBalanceAndHandleException() {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class, () -> account1.withdraw(1200));
        assertEquals("Insufficient funds", exception1.getMessage());
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class, () -> account2.withdraw(600));
        assertEquals("Insufficient funds", exception2.getMessage());
    }
    @Test
    void addingAndRetrievingAccountFromBank() {
        BankAccount newAccount = new BankAccount("54321", 100);
        bank2.addAccount(newAccount);
        assertEquals(newAccount, bank2.retrieveAccount("54321"));
        BankAccount newAccount2 = new BankAccount("11111", 300);
        bank1.addAccount(newAccount2);
        assertEquals(newAccount2, bank1.retrieveAccount("11111"));
    }
    @Test
    void transferBetweenBankAccountsAndVerifyBalances() {
        account1.transferToBank(account2, "12345", 200);
        assertEquals(800, account1.getBalanceUsd());
        assertEquals(700, account2.getBalanceUsd());
        account2.transferToBank(account1, "67890", 100);
        assertEquals(900, account1.getBalanceUsd());
        assertEquals(600, account2.getBalanceUsd());
    }
    @Test
    void totalBalanceCalculationForBanks() {
        assertEquals(1000, bank1.totalBalanceUsd());
        assertEquals(500, bank2.totalBalanceUsd());
        bank1.addAccount(new BankAccount("33333", 200));
        assertEquals(1200, bank1.totalBalanceUsd());
    }
    @Test
    void handlingInvalidAccountRetrieval() {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class, () ->
                        bank1.retrieveAccount("99999"));
        assertEquals("Account not found", exception1.getMessage());
        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class, () ->
                        bank2.retrieveAccount("00000"));
        assertEquals("Account not found", exception2.getMessage());
    }
    // Additional tests can include:
    // - Checking the initial balance correctness.
    // - Handling invalid operations.
    // - Summing balances from multiple accounts in a single bank.

    @Test
    void depositZeroAmountHandleException() {
        final IllegalArgumentException exception1;
        exception1 = assertThrows(IllegalArgumentException.class, () -> account1.deposit(0));
        assertEquals("Deposit amount must be positive", exception1.getMessage());

        final IllegalArgumentException exception2;
        exception2 = assertThrows(IllegalArgumentException.class, () -> account2.deposit(0));
        assertEquals("Deposit amount must be positive", exception1.getMessage());
    }

    @Test
    void depositNegativeAmountHandleException() {
        final IllegalArgumentException exception1;
        exception1 = assertThrows(IllegalArgumentException.class, () -> account1.deposit(-100));
        assertEquals("Deposit amount must be positive", exception1.getMessage());

        final IllegalArgumentException exception2;
        exception2 = assertThrows(IllegalArgumentException.class, () -> account2.deposit(-1500));
        assertEquals("Deposit amount must be positive", exception1.getMessage());
    }

    @Test
    void withdrawNegativeAmountHandleException() {
        final IllegalArgumentException exception1;
        exception1 = assertThrows(IllegalArgumentException.class, () -> account1.withdraw(-100));
        assertEquals("Amount must be positive", exception1.getMessage());

        final IllegalArgumentException exception2;
        exception2 = assertThrows(IllegalArgumentException.class, () -> account2.withdraw(-1500));
        assertEquals("Amount must be positive", exception1.getMessage());
    }

    @Test
    void retrieveNonexistentAccountThrowsException(){
        final IllegalArgumentException exception1;
        exception1 = assertThrows(IllegalArgumentException.class, () -> bank1.retrieveAccount("blablabla"));
        assertEquals("Account not found", exception1.getMessage());

        final IllegalArgumentException exception2;
        exception2 = assertThrows(IllegalArgumentException.class, () -> bank2.retrieveAccount("-------"));
        assertEquals("Account not found", exception2.getMessage());
    }

    @Test
    void transferToSameAccountThrowsException() {
        final IllegalArgumentException exception1;
        exception1 = assertThrows(IllegalArgumentException.class, () -> account1.transferToBank(account1, "12345", 100));
        assertEquals("Cannot transfer to the same account", exception1.getMessage());
    }

    @Test
    void testBadAccountNumberThrowsException()
    {
        badAccountNumber("");
        badAccountNumber("    ");
    }

    void badAccountNumber(final String accountNumber)
    {
        final IllegalArgumentException ex;
        ex = assertThrows(IllegalArgumentException.class, () -> new BankAccount(accountNumber, 2000));
        assertEquals("Invalid account number: " + accountNumber, ex.getMessage());
    }

}
