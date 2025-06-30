import java.util.ArrayList;

public class Buyer extends User {
    int expense = 0;

    public Buyer(String name, BankAccount account, ArrayList<Book> ownbook, int expense) {
        super(name, account, ownbook);
        this.expense = expense;
    }

    public int getExpense() {
        return this.expense;
    }

    public void buyByID(int ID, Seller s) {
        Boolean exist = false;
        Book b = new Book(); 
        for(Book o: s.getBookList()) {
            if(o.getID() == ID) {
                b = o;
                exist = true;
                break;
            }
        }

        if(!exist) {
            System.out.printf("There's no book whose code is %d in %s's store.\n", ID, s.getName());
            return;
        }

        if(getAccount().getBalance() >= b.getPrice()) {
            System.out.printf("%s successfully bought the book whose code is %d from %s.\n", getName(), ID, s.getName());
            expense(b);
            this.setBookList(b);
            s.sellByID(ID, b);
        } else {
            System.out.println("Insufficient Balance.");
        }
    }

    public void buyByName(String name, Seller s) {
        Boolean exist = false;
        Book b = new Book();
        for(Book o: s.getBookList()) {
            if(o.getName().equals(name)) {
                b = o;
                exist = true;
                break;
            }
        }

        if(!exist) {
            System.out.printf("There's no book \"%s\" in %s's store.\n", name, s.getName());
            return;
        }

        if(getAccount().getBalance() >= b.getPrice()) {
            System.out.printf("%s successfully bought the book \"%s\" from %s.\n", getName(), name, s.getName());
            expense(b);
            this.setBookList(b);
            s.sellByName(name, b);
        } else {
            System.out.println("Insufficient Balance.");
        }
    }

    public void expense(Book b) {
        this.expense += b.getPrice();
        getAccount().setBalance(getAccount().getBalance() - b.getPrice());
    }

    @Override
    public void setBookList(Book b) {
        getBookList().add(b);
    }

    @Override
    public void promote() {
        System.out.printf("%s is look for some books, so contact he/she ASAP if you're willing to sell!\n", getName());
    }
}