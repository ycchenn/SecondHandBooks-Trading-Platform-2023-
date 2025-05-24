public class Book {
	private int ID, price;
	private String name;

	public Book(int ID, String name, int price) {
		this.ID = ID;
		this.name = name;
		this.price = price;
	}

	public Book() {
		// 創建一個 constructor 讓 Buyer 的買東西 method 使用
	}

	public int getID() {
		return this.ID;
	}

	public String getName() {
		return this.name;
	}

	public int getPrice() {
		return this.price;
	}
}