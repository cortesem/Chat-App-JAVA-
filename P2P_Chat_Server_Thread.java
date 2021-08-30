import java.io.*;
import java.net.*;
import java.util.Scanner;

public class P2P_Chat_Server_Thread implements Runnable {

	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	String message;

	public P2P_Chat_Server_Thread(Socket s) throws IOException {

		this.socket = s;
		this.input = new Scanner(this.socket.getInputStream());
		this.output = new PrintWriter(this.socket.getOutputStream());
	}

	public void checkConnection() throws IOException {

		if (!socket.isConnected()) {
			for (int i = 0; i < P2P_Chat_Server.connArray.size(); ++i) {
				if (P2P_Chat_Server.connArray.get(i) == socket) {
					P2P_Chat_Server.connArray.remove(i);
				}
			}

			for (int i = 0; i < P2P_Chat_Server.connArray.size(); ++i) {
				Socket tmp = P2P_Chat_Server.connArray.get(i);
				PrintWriter tmp_out = new PrintWriter(tmp.getOutputStream());
				tmp_out.println(tmp.getLocalAddress().getHostName() + " disconnected");
				tmp_out.flush();
				System.out.println(tmp.getLocalAddress().getHostName() + " disconnected");
			}
		}
	}

	public void run() {

		try {
			while (true) {
				checkConnection();

				if (input.hasNext()) {
					message = input.nextLine();
					System.out.println("Client said: " + message);

					for (int i = 0; i < P2P_Chat_Server.connArray.size(); ++i) {
						Socket tmp = P2P_Chat_Server.connArray.get(i);
						PrintWriter tmp_out = new PrintWriter(tmp.getOutputStream());
						tmp_out.println(message);
						tmp_out.flush();
						System.out.println("Sent to: " + tmp.getLocalAddress().getHostName());
					}
				}
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}
		finally {
			try {
				socket.close();
			}
			catch(IOException e) {
				System.out.println(e);
			}
		}
	}
}