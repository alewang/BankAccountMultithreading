package example.bank;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alex on 8/9/16.
 */
@RunWith(JUnit4.class)
public class WithdrawDepositTest {
	private final static long ONE_MINUTE_MILLIS = 60 * 1000;
	private final static int TRANSFER_AMOUNT = 200;

	@Test
	public void withdrawAndDeposit() throws InterruptedException {
		BankAccount myAccount = new BankAccount();
		myAccount.deposit(10000);

		final int startingAcctBalance = myAccount.getBalance();

		final AtomicInteger moneyInWallet = new AtomicInteger(0);
		final long endTime = System.currentTimeMillis() + ONE_MINUTE_MILLIS;

		Thread withdrawer = new Thread() {
			@Override
			public void run() {
				while(System.currentTimeMillis() < endTime) {
					int withdrawnAmt = myAccount.withdraw(TRANSFER_AMOUNT);
					moneyInWallet.addAndGet(withdrawnAmt);
				}
			}
		};

		Thread depositor = new Thread() {
			@Override
			public void run() {
				while(System.currentTimeMillis() < endTime) {
					moneyInWallet.addAndGet(-TRANSFER_AMOUNT);
					myAccount.deposit(TRANSFER_AMOUNT);
				}
			}
		};

		withdrawer.start();
		depositor.start();

		withdrawer.join();
		depositor.join();

		Assert.assertEquals(myAccount.getBalance(), startingAcctBalance);
	}
}
