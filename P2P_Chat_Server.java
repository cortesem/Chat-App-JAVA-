import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class P2P_Chat_Server {

	private final static int PORT = 444;
	private final static String HOST = "";

	public static ArrayList<Socket> connArray = new ArrayList<Socket>();
	public static ArrayList<String> users = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		try {
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Waiting for clients...");

			while (true) {
				Socket socket = server.accept();
				connArray.add(socket);

				System.out.println("Client connected from: " + socket.getLocalAddress().getHostName());

				AddUserName(socket);

				P2P_Chat_Server_Thread chat = new P2P_Chat_Server_Thread(socket);
				Thread T = new Thread(chat);
				T.start();
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void AddUserName(Socket s) throws IOException {
	
		Scanner input = new Scanner(s.getInputStream());
		String username = input.nextLine();
		users.add(username);

		for (int i = 0; i < connArray.size(); ++i) {
			Socket tmp = connArray.get(i);
			PrintWriter out = new PrintWriter(tmp.getOutputStream());
			out.println("#?!" + users);
			out.flush();
		}
	}
}