package homework9;

class Account {
	private int balance;

	public Account(int balance) {
		this.balance = balance;
	}

	synchronized public void deposit(int allowance, int count) {

		while (balance >= 2000) {
			System.out.println("媽媽看到餘額2000以上,暫停存款");
			try {
				notify();
				System.out.println("雄大被媽媽告知已經帳戶有錢了");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		balance += allowance;
		System.out.println("媽媽存了" + allowance + "帳戶共有" + balance + "-" + count);
	}

	synchronized public void withdraw(int allowance, int count) {

		while (balance < allowance) {
			System.out.println("雄大看到帳戶沒錢,暫停提款");
			try {
				notify();
				System.out.println("媽媽被熊大要求存款-1");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		balance -= allowance;
		System.out.println("雄大領了" + allowance + "帳戶供有" + balance + "-" + count);

	}

}

class Son extends Thread {
	Account account;

	public Son(Account account) {
		this.account = account;
	}

	public void run() {
		for (int i = 1; i <= 10; i++) {
			account.withdraw(2000, i);
		}
	}
}

class Mother extends Thread {
	Account account;

	public Mother(Account account) {
		this.account = account;
	}

	public void run() {
		for (int i = 0; i <= 10; i++) {
			account.deposit(3000, i);
		}
	}
}

public class TestAllowance {
	public static void main(String[] args) {
		Account account = new Account(0);
		Mother mother = new Mother(account);
		Son son = new Son(account);

		mother.start();
		son.start();
	}
}
