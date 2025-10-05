package ru.yaxus.inojava;

import org.apache.commons.lang.StringUtils;
import org.flywaydb.core.Flyway;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class App {

    record topOrders(String customer_name, String product_name, int qty, Timestamp ts, String order_status){};

    public static void main(String[] args) throws IOException {
        Properties conf = PropertiesLoader.loadProperties();
        String db_url = conf.getProperty("db_url");
        String db_schema = conf.getProperty("db_schema");
        String db_user = conf.getProperty("db_user");
        String db_pass = conf.getProperty("db_pass");

//        Миграция через плагин (очистка, baseline)
//        var flyway = Flyway.configure()
//                .dataSource(db_url, db_user, db_pass)
//                .schemas(db_schema)
//                .locations("classpath:db/migration")
//                .load();
//        flyway.migrate();

        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pass)) {
            conn.setAutoCommit(false);
            conn.setSchema(db_schema);
            // 1.
            System.out.println("1. Вставка нового товара и покупателя (PreparedStatement)");
            Integer newIdCustomer = addCustomer(conn, "Вася","Федоров","322233","mail_vasya@karasya.ru");
            Integer newIdProduct = addProduct(conn, "Перфоратор","",22500,5, "Электроинструменты");
            // 2.
            System.out.println("2. Создание заказа для покупателя.");
            Integer newIdOrder = addOrder(conn, newIdCustomer, newIdProduct, 1);
            // 3.
            System.out.println("3. Чтение и вывод последних 5 заказов с JOIN на товары и покупателей.");
            String rowFormat = "| %-10s | %-15s | %-8s | %-30s | %-10s |\n";
            System.out.printf(rowFormat
                    , StringUtils.center("Клиент", 10)
                    , StringUtils.center("Продукт", 15)
                    , StringUtils.center("Кол-во", 8)
                    , StringUtils.center("Время", 30)
                    , StringUtils.center("Статус", 10));
            selectTopOrders(conn)
                    .forEach(s -> System.out.printf(rowFormat, s.customer_name, s.product_name
                            , s.qty, s.ts.toLocalDateTime(), s.order_status));
            // 4.
            System.out.println("4. Обновление цены товара и количества на складе.");
            var st = conn.prepareStatement("update product set cost=cost*1.1, qty=qty-1 where id=?");
            st.setInt(1, newIdProduct);
            st.executeUpdate();
            System.out.printf("Продукт с ID %s обновлен\n", newIdProduct);

            // 5.
            System.out.println("5. Удаление тестовых записей.");
            delRow(conn, "order", newIdOrder);
            System.out.printf("Заказ с ID %s удален\n", newIdOrder);
            delRow(conn, "customer", newIdCustomer);
            System.out.printf("Клиент с ID %s удален\n", newIdCustomer);
            delRow(conn, "product", newIdProduct);
            System.out.printf("Продукт с ID %s удален\n", newIdProduct);

            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static List<topOrders> selectTopOrders(Connection conn) throws SQLException {
        var ret = new ArrayList<topOrders>();
        var qr = "select c.\"name\" customer_name, p.\"name\" product_name\n" +
                 "     , o.qty, o.ts AT TIME ZONE 'UTC' timest, os.\"name\" order_status\n" +
                 "  from \"order\" o\n" +
                 "       join customer c on c.id = o.customer_id\n" +
                 "       join product p on p.id = o.product_id\n" +
                 "       join order_status os on os.id = o.order_status_id\n" +
                 " order by o.ts desc\n" +
                 " limit 5";
        var st = conn.createStatement();
        var rs = st.executeQuery(qr);

        while (rs.next()) {
            var row = new topOrders(rs.getString(1)
                    , rs.getString(2)
                    , rs.getInt(3)
                    , rs.getTimestamp(4)
                    , rs.getString(5)
            );
            ret.add(row);
        }
        rs.close();
        st.close();
        return ret;
    }

    public static void delRow(Connection conn, String table, Integer id) throws SQLException {
        var stDelProd = conn.prepareStatement("delete from \""+ table +"\" where id=?");
        stDelProd.setInt(1, id);
        stDelProd.executeUpdate();
    }

    public static Integer addCustomer(Connection conn, String name, String lastname, String tel, String email) throws SQLException {
        var qr = "insert into customer (name, lastname, tel, email) values (?,?,?,?)";
        var pst = conn.prepareStatement(qr, PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setString(1, name);
        pst.setString(2, lastname);
        pst.setString(3, tel);
        pst.setString(4, email);
        return insertStmExec(pst, name);
    }

    public static Integer addProduct(Connection conn, String name, String descr, float cost, int qty, String cat) throws SQLException {
        var qr = "insert into product (name, descr, cost, qty, cat) values (?,?,?,?,?)";
        var pst = conn.prepareStatement(qr, PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setString(1, name);
        pst.setString(2, descr);
        pst.setFloat(3, cost);
        pst.setInt(4, qty);
        pst.setString(5, cat);
        return insertStmExec(pst, name);
    }

    public static Integer addOrder(Connection conn, int customer_id, int product_id, int qty) throws SQLException {
        var qr = "insert into \"order\" (customer_id, product_id, qty, order_status_id) values (?,?,?,?)";
        var pst = conn.prepareStatement(qr, PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setInt(1, customer_id);
        pst.setInt(2, product_id);
        pst.setInt(3, qty);
        pst.setInt(4, 1); // Заказ создан
        // Уменьшить кол-во товара
        var st = conn.prepareStatement("update product set qty = qty - ? where id = ?");
        st.setInt(1, qty);
        st.setInt(2, product_id);
        st.executeUpdate();
        return insertStmExec(pst, "Customer id(" + customer_id + ")");
    }

    public static Integer insertStmExec(PreparedStatement pst, String name) throws SQLException {
        Integer ret = null;
        int affectedRows = pst.executeUpdate();
        if (affectedRows > 0) {
            var rs = pst.getGeneratedKeys();
            if (rs.next()) {
                ret = rs.getInt(1);
                System.out.println("Inserted record ID for " + name + ": " + ret);
            }
        } else {
            System.out.println("No rows affected for " + name + ".");
        }
        return ret;
    }
}
