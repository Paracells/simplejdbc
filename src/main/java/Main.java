import java.sql.*;

public class Main {

    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String DB_URL = "jdbc:postgresql://...";
    public static final String USER = " ";
    public static final String PASS = " ";

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER); // инициализируем драйвер бд (мавен)
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: not loading JDBC_DRIVER");
        }

        System.out.println("Connection to database");
        try {


            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); // подключаемся к бд
            System.out.println("Create statement...");
            Statement statement = connection.createStatement(); // создаем стэйтмент, туда запихнем наш запрос

            // language=SQL
            String sql = "SELECT * FROM Employees"; //наш запрос, получаем всю таблицу
            ResultSet resultSet = statement.executeQuery(sql);

            printResult(resultSet);

            // language=SQL
            String sqlInsert = "INSERT INTO Employees (id, age, first, last) VALUES (104,2,'Kot','Barsik')"; //добавляем нового работника
          /*  statement.executeUpdate(sqlInsert);
            statement.executeUpdate(sqlInsert);
            statement.executeUpdate(sqlInsert);*/

            String sqlDelete = "DELETE FROM Employees WHERE id=104"; // отправляем в отпуск
            statement.executeUpdate(sqlDelete);


            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // выводим всю базу
    public static void printResult(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("FirstName: " + resultSet.getString("first"));
                System.out.println("LastName: " + resultSet.getString("last"));
                System.out.println("------------");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


}
