package tech.flycat.ds.solution;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author <a href="mailto:me@flycat.tech">Bryon Zen</a>
 * @since 2025/1/15
 */
@SpringBootTest(classes = {Application.class})
public class BaseTest {
    @BeforeEach
    public void before() {
        System.out.println("==============开始执行=============================");
        printDatabase();
        System.out.println("==============开始执行=============================");
    }

    @AfterEach
    public void after() {
        System.out.println("==============执行结束=============================");
        printDatabase();
        System.out.println("==============开始执行=============================");
    }

    private static void printDatabase() {
        String jdbcUrl = "jdbc:h2:mem:testdb0;MODE=MYSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false"; // 替换为你的 H2 数据库 URL
        String user = "root";
        String password = "test";

        printTableData(jdbcUrl, user, password);

        jdbcUrl = "jdbc:h2:mem:testdb1;MODE=MYSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false"; // 替换为你的 H2 数据库 URL
        user = "root";
        password = "test";

        printTableData(jdbcUrl, user, password);

        jdbcUrl = "jdbc:h2:mem:testdb2;MODE=MYSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false"; // 替换为你的 H2 数据库 URL
        user = "root";
        password = "test";

        printTableData(jdbcUrl, user, password);
    }

    private static void printTableData(String jdbcUrl, String user, String password) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password)) {
            // 获取数据库元数据
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            // 遍历所有表
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // 查询表数据
                String query = "SELECT * FROM " + tableName;
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int columnCount = resultSetMetaData.getColumnCount();

                    // 打印列名
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(resultSetMetaData.getColumnName(i) + "\t");
                    }
                    System.out.println();

                    // 打印数据
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(resultSet.getString(i) + "\t");
                        }
                        System.out.println();
                    }
                } catch (SQLException e) {
                    System.err.println("Error querying table " + tableName + ": " + e.getMessage());
                }
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
