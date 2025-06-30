import java.util.ArrayList;

public class Seller extends User {
    private int revenue = 0;

    public Seller(String name, BankAccount account, ArrayList<Book> ownbook, int revenue) {
        super(name, account, ownbook);
        this.revenue = revenue;
    }

    public int getRevenue() {
        return this.revenue;
    }

    public void sellByID(int ID, Book b) {
        this.setBookList(b);
        earning(b);
        System.out.printf("%s successfully sold the book whose code is %d.\n", getName(), ID);
    }

    public void sellByName(String name, Book b) {
        this.setBookList(b);
        earning(b);
        System.out.printf("%s successfully sold the book \"%s\".\n", getName(), name);
    }

    public void earning(Book b) {
        this.revenue += b.getPrice();
        getAccount().setBalance(getAccount().getBalance() + b.getPrice());
    }

    @Override
    public void setBookList(Book b) {
        getBookList().remove(b);
    }

    @Override
    public void promote() {
        System.out.printf("%s has plenty of books, so check them immediately!\n", getName());
    }
}