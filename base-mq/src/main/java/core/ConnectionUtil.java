package core;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class ConnectionUtil {

    private static final String RABBIT_HOST = "localhost";

    private static final String RABBIT_USERNAME = "guest";

    private static final String RABBIT_PASSWORD = "guest";

    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection == null) {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(RABBIT_HOST);
            connectionFactory.setUsername(RABBIT_USERNAME);
            connectionFactory.setPassword(RABBIT_PASSWORD);
            try {
                connection = connectionFactory.newConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
//
//public class ConnectionUtil {
//
//    private static final String RABBIT_HOST = "localhost";
//
//    private static final String RABBIT_USERNAME = "guest";
//
//    private static final String RABBIT_PASSWORD = "guest";
//
//    private static Connection connection = null;
//
//    public static Connection getConnection() {
//        if(connection == null) {
//            ConnectionFactory connectionFactory = new ConnectionFactory();
//            connectionFactory.setHost(RABBIT_HOST);
//            connectionFactory.setUsername(RABBIT_USERNAME);
//            connectionFactory.setPassword(RABBIT_PASSWORD);
//            try {
//                connection = connectionFactory.newConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return connection;
//    }
//
//}
//
//public class ConnectionUtil {
//
//    private static final String RABBIT_HOST = "localhost";
//
//    private static final String RABBIT_USERNAME = "guest";
//
//    private static final String RABBIT_PASSWORD = "guest";
//
//    private static Connection connection = null;
//
//    public static Connection getConnection() {
//        if(connection == null) {
//            ConnectionFactory connectionFactory = new ConnectionFactory();
//            connectionFactory.setHost(RABBIT_HOST);
//            connectionFactory.setUsername(RABBIT_USERNAME);
//            connectionFactory.setPassword(RABBIT_PASSWORD);
//            try {
//                connection = connectionFactory.newConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return connection;
//    }
//
//}
//
//public class ConnectionUtil {
//
//    private static final String RABBIT_HOST = "localhost";
//
//    private static final String RABBIT_USERNAME = "guest";
//
//    private static final String RABBIT_PASSWORD = "guest";
//
//    private static Connection connection = null;
//
//    public static Connection getConnection() {
//        if(connection == null) {
//            ConnectionFactory connectionFactory = new ConnectionFactory();
//            connectionFactory.setHost(RABBIT_HOST);
//            connectionFactory.setUsername(RABBIT_USERNAME);
//            connectionFactory.setPassword(RABBIT_PASSWORD);
//            try {
//                connection = connectionFactory.newConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return connection;
//    }
//
//}
