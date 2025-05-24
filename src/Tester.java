import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) {
		// 創建四個帳戶
		BankAccount account1 = new BankAccount(112309408, 2000);
		BankAccount account2 = new BankAccount(112206061, 600);
		BankAccount account3 = new BankAccount(112101035, 500);
		BankAccount account4 = new BankAccount(112507012, 0);

		// 創建七本書
		Book book1 = new Book(1094307, "Statistics for Bussiness", 350);
		Book book2 = new Book(7618216, "Economics principles", 200);
		Book book3 = new Book(5440580, "Calculas", 175);
		Book book4 = new Book(2497113, "Financial Accounting", 398);
		Book book5 = new Book(8673129, "Welcome to Java!", 400);
		Book book6 = new Book(1906917, "The applications of AI", 350);
		Book book7 = new Book(7531664, "Learning Japanese in three months", 270);

		// 創建四個書單
		ArrayList<Book> ownBook1 = new ArrayList<Book>();
		ArrayList<Book> ownBook2 = new ArrayList<Book>();
		ArrayList<Book> ownBook3 = new ArrayList<Book>();
		ArrayList<Book> ownBook4 = new ArrayList<Book>();

		// 往書單 3 與 4 加書，1 與 2 預設空值 ( 因假設買家一開始沒書 )
		ownBook3.add(book1);
		ownBook3.add(book3);
		ownBook3.add(book4);
		ownBook3.add(book6);
		ownBook4.add(book2);
		ownBook4.add(book5);
		ownBook4.add(book7);

		// 創建兩個 buyers 與兩個 sellers
		Buyer buyer1 = new Buyer("Merry", account1, ownBook1, 0);
		Buyer buyer2 = new Buyer("Rachel", account2, ownBook2, 0);
		Seller seller1 = new Seller("Steven", account3, ownBook3, 0);
		Seller seller2 = new Seller("Tom", account4, ownBook4, 0);

		// 建立 users 的 ArrayList 以儲存全部的 buyers 與 sellers
		ArrayList<User> users = new ArrayList<User>();
		users.add(buyer1);
		users.add(buyer2);
		users.add(seller1);
		users.add(seller2);

		// 為自己宣傳
		System.out.println("[Promotion]");
		buyer1.promote();
		seller2.promote();

		// 交易開始
		System.out.println("\nTrade1:");
		buyer1.buyByID(1094307, seller2); // 購買失敗1: 賣家本來就沒有書 ( ID 判斷 )
		System.out.println("\nTrade2:");
		buyer1.buyByID(1094307, seller1); // 購買成功 (用 ID )
		System.out.println("\nTrade3:");
		buyer2.buyByName("Welcome to Java!", seller2); // 購買成功 (用 name )
		System.out.println("\nTrade4:");
		buyer2.buyByID(1906917, seller1); // 購買失敗2: 買家餘額不足
		System.out.println("\nTrade5:");
		buyer1.buyByName("Accounting", seller1); // 購買失敗3: 書名 name 錯誤
		System.out.println("\nTrade6:");
		buyer1.buyByID(7531664, seller2); // 購買成功 (用 ID )
		System.out.println("\nTrade7:");
		buyer1.buyByID(8673129, seller2); // 購買失敗 (賣家已賣給 buyer2 )

		// 所有 user 取得資料
		System.out.print("\n[Information of users]");
		for(User u: users) {
			System.out.println("\n" + u.getInfo());
		}
	}
}
