package example.bank;

/**
 * <p>This type of account does not allow you to
 * withdraw more money than the balance available in
 * the account. Make sure the withdraw() method checks
 * for this condition before actually deducting
 * amounts from the balance!</p>
 *
 * <p>Similarly, check to make sure that the deposit()
 * method does not deposit a negative amount of money.</p>
 * Created by alex on 8/9/16.
 */
public class BankAccount {
	private int balance;

	public BankAccount() {
		balance = 0;
	}

	public synchronized void deposit(int dollarsToDeposit) {
		if(dollarsToDeposit < 0) {
			throw new RuntimeException("Deposit amount is negative");
		}
		balance += dollarsToDeposit;
	}

	public synchronized int getBalance() {
		return balance;
	}

	public synchronized int withdraw(int amountToWithdraw) {
		int amountWithdrawn;
		if(amountToWithdraw > balance) {
			amountWithdrawn = balance;
		} else {
			amountWithdrawn = amountToWithdraw;
		}
		balance -= amountWithdrawn;
		return amountWithdrawn;
	}
}
