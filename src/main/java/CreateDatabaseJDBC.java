import java.sql.*;


public class CreateDatabaseJDBC {

    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String DB_URL = ""; // our DB
    public static final String USER = ""; // our USER DB
    public static final String PASS = ""; // PASS

    public static final String sqlCreateDatabase = "CREATE DATABASE mytestbd";

    public static Statement statement = null;
    public static PreparedStatement preparedStatement = null;
    public static Connection connection = null;

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connection...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS); //подключаемся к общей бд
            System.out.println("Statement");
            statement = connection.createStatement();


            System.out.println("CREATE DB mytestbd");
            try {
                statement.executeUpdate(sqlCreateDatabase);
            } catch (SQLException e) {
                System.out.println("DROP DATABASE");
                statement.executeUpdate("DROP DATABASE mytestbd");
                statement.executeUpdate(sqlCreateDatabase);
            }


            System.out.println("Create TABLE"); //создаем табличку с auto increment id (SERIAL in postgrsql)
            String sqlCreateTable = "CREATE TABLE Cats (id SERIAL, name VARCHAR (20), age int, eat VARCHAR(20))";
            statement.executeUpdate(sqlCreateTable);

            System.out.println("INSERT new cats");

            Cat c1 = new Cat("Barsik", 1, "fish"); //добавляем новых котов
            Cat c2 = new Cat("Murzik", 3, "meat");
            Cat c3 = new Cat("Meow", 1, "royal canin");
            insertNewCat(c1);
            insertNewCat(c2);
            insertNewCat(c3);

            String getAll = "SELECT * FROM Cats";
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " " + String.valueOf(resultSet.getInt("age")) + " " + resultSet.getString("eat"));
            }

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
//            statement.executeUpdate("DROP DATABASE mytestbd");
            ex.printStackTrace();
        }
    }


    // PreparedStatement для вставки подготовленных запросов
    public static void insertNewCat(Cat cat) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO Cats(name,age,eat) VALUES(?,?,?)"); // вместо ? будем брать значения из объектов
            preparedStatement.setString(1, cat.getName());
            preparedStatement.setInt(2, cat.getAge());
            preparedStatement.setString(3, cat.getEat());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}

// наши объекты коты
class Cat {
    private String name;
    private int age;
    private String eat;

    public Cat(String name, int age, String eat) {
        this.name = name;
        this.age = age;
        this.eat = eat;
    }

    public String getEat() {
        return eat;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


}
