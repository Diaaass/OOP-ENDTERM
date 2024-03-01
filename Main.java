import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Price price = new Price(5600);
            Price sale = new Price(2000);
            Item newItem = new Item("cookies", 60, price, sale);

            DataBaseWorker DBworker = new DataBaseWorker();


            DBworker.InsertItem(newItem);


        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}