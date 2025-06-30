import java.util.ArrayList;

public class User {
    private String name;
    private BankAccount account;
    private ArrayList<Book> bookList  = new ArrayList<Book>();

    public User(String name, BankAccount account, ArrayList<Book> bookList) {
        this.name = name;
        this.account = account;
        this.bookList = bookList;
    }

    public String getName() {
        return this.name;
    }

    public BankAccount getAccount() {
        return this.account;
    }

    public ArrayList<Book> getBookList() {
        return this.bookList;
    }

    public String getInfo() {
        String s = "";
        int counter = 0;
        for(Book b: this.bookList) {
            counter++;
            s += "[" + b.getID() + "_" + b.getName() + "]";
            if(!(this.bookList.size() - counter == 0)) {
                s+=", ";
            }
        }
        return "Name: " + name + "\nAccount Balance: " + account.getBalance() + "\nBookList: " + s;
    }

    public void setBookList(Book b) {
        // Leave it here blank for overriding
    }

    public void promote() {
        // Leave it here blank for overriding
    }
}