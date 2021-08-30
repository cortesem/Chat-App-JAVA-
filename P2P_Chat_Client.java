import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class P2P_Chat_Client implements Runnable {

	private Socket socket;
	private Scanner in;
	private Scanner send;
	private PrintWriter out;

	public P2P_Chat_Client(Socket s) {

		this.socket = s;
		this.send = new Scanner(System.in);
	}

	public void run() {

		try {
			try {
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream());
				out.flush();
				checkStream();
			}
			finally {
				socket.close();
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void disconnect() throws IOException {
		
		out.println(P2P_Chat_Client_UI.userName + " has disconnected.");
		out.flush();
		socket.close();
		JOptionPane.showMessageDialog(null, "You disconnected!");
		System.exit(0);
	}

	public void checkStream() {
		
		while (true) {
			receive();
		}
	}

	public void receive() {

		if (in.hasNext()) {
			String message = in.nextLine();

			if (message.contains("#?!")) {
				String tmp = message.substring(3);
				tmp = tmp.replace("[", "");
				tmp = tmp.replace("]", "");

				String[] currentUsers = tmp.split(", ");

				P2P_Chat_Client_UI.onlineList.setListData(currentUsers);
			}
			else {
				P2P_Chat_Client_UI.convArea.append(message + "\n");
			}
		}
	}

	public void send(String s) {

		out.println(P2P_Chat_Client_UI.userName + ": " + s);
		out.flush();
		P2P_Chat_Client_UI.messageField.setText("");
	}

}