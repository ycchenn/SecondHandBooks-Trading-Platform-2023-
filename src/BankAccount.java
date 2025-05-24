
public class BankAccount {
	private int ID; 
	private int balance;

	public BankAccount(int ID, int balance) {
		this.ID = ID;
		this.balance = balance;
	}

	public int getID () {
		return this.ID;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
}