import java.sql.*;


public class DataBaseWorker {
    private final String url = "jdbc:postgresql://localhost:5432/mak";
    private final String username = "postgres";
    private final String password = "postgres";

    public Connection GetConnector() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void InsertItem(Item item) {
        try (Connection conn = GetConnector();

             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO items (name, quantity, price, sale) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getQuantity());
            statement.setDouble(3, item.getPrice().getValue());
            statement.setDouble(4, item.getSale().getValue());

            statement.executeUpdate();
            System.out.println("Товар успешно добавлен.");
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    public Item selectItem(String name) {
        try (Connection conn = GetConnector();
             PreparedStatement statement = conn.prepareStatement(
                     "SELECT * FROM items WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet table = statement.executeQuery();

            if (table.next()) {
                Item item = new Item();
                item.setName(table.getString("name"));
                item.setQuantity(table.getInt("quantity"));
                item.setPrice(new Price(table.getDouble("price")));
                item.setSale(new Price(table.getDouble("sale")));
                return item;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выборке товара: " + e.getMessage());
        }
        return null;
    }

    public void updateItem(String name, int newQuantity) {
        try (Connection conn = GetConnector();
             PreparedStatement statement = conn.prepareStatement(
                     "UPDATE items SET quantity = ? WHERE name = ?")) {
            statement.setInt(1, newQuantity);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("Товар успешно обновлен.");
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении товара: " + e.getMessage());
        }
    }

    public void deleteItem(String name) {
        try (Connection conn = GetConnector();
             PreparedStatement statement = conn.prepareStatement(
                     "DELETE FROM items WHERE name = ?")) {
            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("Товар успешно удален.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении товара: " + e.getMessage());
        }
    }

    public ResultSet getAllItems() {
        try {
            Connection conn = GetConnector();
            Statement statement = conn.createStatement();
            return statement.executeQuery("SELECT * FROM items");
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка товаров: " + e.getMessage());
            return null;
        }
    }

    public double calculateTotalSales() {
        try (ResultSet resultSet = getAllItems()) {
            double totalSales = 0;
            while (resultSet.next()) {
                double price = resultSet.getDouble("price");
                double sale = resultSet.getDouble("sale");
                int quantity = resultSet.getInt("quantity");
                totalSales += (price - sale) * quantity;
            }
            return totalSales;
        } catch (SQLException e) {
            System.err.println("Ошибка при расчете общей выручки: " + e.getMessage());
            return -1; // Показать ошибку
        }
    }
}