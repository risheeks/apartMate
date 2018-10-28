import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Test {
    private static int port;
    private static String ip;
    public static void main(String[] args)  throws InterruptedException{
        ip = "10.0.0.16";
        port = 9910;
        test1_1();
        test1_2();
        test1_3();
        Thread.sleep(1500);
        test2_1();
        test2_2();
        test2_3();
        Thread.sleep(1500);
        test3();
        Thread.sleep(1500);
        test4_1();
        test4_2();
    }

    //Adds user irettig
    static void test1_1() {
        //System.out.print("Test 1_1\n");

        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("REGISTER SUCCESS")) {
                    System.out.print("Test 1_1: Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 1_1: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "REGISTER irettig@purdue.edu 12345 Ian Rettig";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();


    }

    //Adds user raj5
    static void test1_2() {
        //System.out.print("Test 1_2\n");

        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("REGISTER SUCCESS")) {
                    System.out.print("Test 1_2: Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 1_2: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "REGISTER raj5@purdue.edu 12345 Adrian Raj";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();


    }

    //Adds user corruptsoul13
    static void test1_3() {
        //System.out.print("Test 1_3\n");

        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("REGISTER SUCCESS")) {
                    System.out.print("Test: 1_3 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 1_3: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "REGISTER corruptsoul13@gmail.com 12345 Ian Rettig";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();


    }

    //logs in as user irettig
    static void test2_1() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    System.out.print("Test: 2_1 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 2_1: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN irettig@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //logs in as user raj5
    static void test2_2() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    System.out.print("Test: 2_2 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 2_2: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN raj5@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //logs in as user corruptsoul13
    static void test2_3() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    System.out.print("Test: 2_3 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 2_3: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN corruptsoul13@gmail.com 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //create group test group 1
    static void test3() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "CREATE_GROUP;irettig@purdue.edu;test group 1";
                    c.send(message2);
                }
                else if (message.equals("CREATE_GROUP SUCCESS")) {
                    System.out.print("Test: 3 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (message.equals("INVALID_COMMAND")) {
                    System.out.print("Fix this Adrian\n");
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 3: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN irettig@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //add raj5 to test group 1
    static void test4_1() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "ADD_GROUP;raj5@purdue.edu;test group 1";
                    c.send(message2);
                }
                else if (message.equals("ADD_GROUP SUCCESS")) {
                    System.out.print("Test: 4_1 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (message.equals("INVALID_COMMAND")) {
                    System.out.print("Fix this Adrian\n");
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 4_1: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN irettig@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }

    //add corruptsoul13 to test group 1
    static void test4_2() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "ADD_GROUP;corruptsoul13@gmail.com;test group 1";
                    c.send(message2);
                }
                else if (message.equals("ADD_GROUP SUCCESS")) {
                    System.out.print("Test: 4_2 Success\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (message.equals("INVALID_COMMAND")) {
                    System.out.print("Fix this Adrian\n");
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 4_2: " + message + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN irettig@purdue.edu 12345";
                c.send(message);

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        c.connect();
    }
}
