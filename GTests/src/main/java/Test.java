import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Test {
    private static int port;
    private static String ip;
    public static void main(String[] args)  throws InterruptedException{
        //System.out.printf("This is a test\n");
        ip = "10.186.93.103";
        port = 9910;
        /*test1_1();
        test1_2();
        test1_3();
        Thread.sleep(4000);
        test2_1();
        test2_2();
        test2_3();
        Thread.sleep(1500);
        test3();
        Thread.sleep(1500);
        test4_1();
        test4_2();
        Thread.sleep(1500);
        test5();*/
        //testCommand("irettig@purdue.edu", "12345", "GET_GROUPMEMBERS;test group 1");
        test6();
    }

    //Adds user patel716
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
                String message = "REGISTER patel716@purdue.edu 12345 Ian Rettig";
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

    //Adds user rajadrian18
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
                String message = "REGISTER rajadrian18@gmail.com 12345 Adrian Raj";
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

    //Adds user adrianraj1818
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
                String message = "REGISTER adrianraj1818@gmail.com 12345 Ian Rettig";
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

    //logs in as user patel716
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
                String message = "LOGIN patel716@purdue.edu 12345";
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

    //logs in as user rajadrian18
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
                String message = "LOGIN rajadrian18@gmail.com 12345";
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

    //logs in as user adrianraj1818
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
                String message = "LOGIN adrianraj1818@gmail.com 12345";
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
                    String message2 = "CREATE_GROUP;adrianraj1818@gmail.com;test group 1";
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

    //add patel716 to test group 1
    static void test4_1() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "ADD_GROUP;patel716@purdue.edu;test group 1";
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

    //add rajadrian18 to test group 1
    static void test4_2() {
        Client c = new Client(ip, port);


        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "ADD_GROUP;rajadrian18@gmail.com;test group 1";
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

    //open 3 clients, send a group message
    static void test5() {
        sendGMessage("adrianraj1818@gmail.com","12345");
        receiveGMessage("patel716@purdue.edu", "12345");
        receiveGMessage("rajadrian18@gmail.com", "12345");
    }

    //sends message to test group 1
    static void sendGMessage(String username, String password) {
        Client c = new Client(ip, port);

        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED in send message from " + username + ": " + message + "\n");
                String token = message.split(";")[0];

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = "SEND_GROUPM;" + username + ";test group 1;This is a test message!" ;
                    c.send(message2);
                }
                else if (token.equals("RECEIVE_GROUPM")) {
                    System.out.print("Test: 5 Success! " + username + " sent a message\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 5: " + message + " for " + username + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN " + username + " " + password;
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

    //receives group messages in the passed in account. assumes account is already in test group 1
    static void receiveGMessage (String username, String password) {
        Client c = new Client(ip, port);

        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                //System.out.print("MESSAGE RECEIVED in receive message from " + username + ": " + message + "\n");
                String token = message.split(";")[0];

                if(message.equals("LOGIN SUCCESS")) {
                    //do nothing
                }
                else if (token.equals("RECEIVE_GROUPM")) {
                    System.out.print("Test: 5 Success! " + username + " received a message\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }
                else if (!message.equals("Welcome to the server!")) {
                    System.out.print("Failed test 5: " + message + " for " + username + "\n");
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN " + username + " " + password;
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

    static void testCommand(String username, String password, String command) {
        Client c = new Client(ip, port);

        c.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String message) {
                System.out.print("MESSAGE RECEIVED: " + message + "\n");

                if(message.equals("LOGIN SUCCESS")) {
                    String message2 = command;
                    c.send(message2);
                }

                else if (!message.equals("Welcome to the server!")) {
                    try {
                        c.disconnect();
                    } catch (java.io.IOException e) {
                        System.out.print(e.getMessage());
                    }
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                String message = "LOGIN " + username + " " + password;
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

    //test for leaving group. assumes that patel1716 is in a group
    static void test6() {
        try {
            //connect to the server
            Socket sock = new Socket(ip, port);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            //add interests, emergency contact, shared and unshared items
            String interest = "ADD_INTEREST;test group 1;patel1716@purdue.edu;Programming software.";
            String emergencyContact = "ADD_EMERGENCY;test group 1;patel1716@purdue.edu;911";
            String shared = "ADD_SHAREABLEPOSSESSION;test group 1; patel1716@purdue.edu;television";
            String unshared = "ADD_UNSHAREABLEPOSSESSION;test group 1;patel1716@purdue.edu;My bed";
            out.write(interest);
            out.write(emergencyContact);
            out.write(shared);
            out.write(unshared);
            shared = "ADD_SHAREABLEPOSSESSION;test group 1; patel1716@purdue.edu;playstation";
            unshared = "ADD_UNSHAREABLEPOSSESSION;test group 1;patel1716@purdue.edu;toothbrush";
            out.write(shared);
            out.write(unshared);

            //access firebase to show that patel1716 is in a group, show things

            //leave the group

            //access firebase again to show that patel1716 has left the group
            } catch (Exception e) {
            System.out.print("Error\n");
            e.printStackTrace();
        }
    }
}
