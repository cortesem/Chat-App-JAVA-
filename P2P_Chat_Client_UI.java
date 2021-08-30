import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class P2P_Chat_Client_UI {

	private static final int PORT = 444;
	private static final String HOST_NAME = "10.0.0.7";
	private static P2P_Chat_Client client;
	private static JButton aboutBtn;
	private static JButton connectBtn;
	private static JButton disconnectBtn;
	private static JButton helpBtn;
	private static JButton sendBtn;
	private static JButton enterButton;
	private static JLabel messageLbl;
	private static JLabel convLbl;
	private static JLabel onlineLbl;
	private static JLabel loginAsLbl;
	private static JLabel loginAsBoxLbl;
	private static JLabel userNameLbl;
	private static JPanel loginPanel;
	private static JScrollPane convPane;
	private static JScrollPane onlinePane;

	public static String userName = "Annon";
	public static JFrame main;
	public static JFrame loginFrame;
	public static JTextField messageField;
	public static JTextField usernameField;
	public static JTextArea convArea;
	public static JList onlineList;

	public P2P_Chat_Client_UI() {

		userName = "Annon";
		main = new JFrame();
		mainWindowSetup();
		initialize();
	}

	public static void main(String[] args) {

		userName = "Annon";
		main = new JFrame();
		mainWindowSetup();
		initialize();
	}

	public static void mainWindowSetup() {

		main.setTitle(userName + "'s Chat Client");
		main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		main.setLocation(220, 180);
		main.setResizable(false);
		configMain();
		main.setVisible(true);
	}

	public static void configMain() {

		main.setBackground(new Color(255, 255, 255));
		main.setSize(515, 320);
		main.getContentPane().setLayout(null);

		// Send Message Button
		sendBtn = new JButton();
		sendBtn.setBackground(new Color(0, 0, 255));
		sendBtn.setForeground(new Color(255, 255, 255));
		sendBtn.setText("Send");
		main.getContentPane().add(sendBtn);
		sendBtn.setBounds(250, 40, 81, 25);
		sendBtn.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (!messageField.getText().equals("")) {
						client.send(messageField.getText());
						messageField.requestFocus();
					}
				}
			}
		);

		// Disconnect Button
		disconnectBtn = new JButton();
		disconnectBtn.setBackground(new Color(0, 0, 255));
		disconnectBtn.setForeground(new Color(255, 255, 255));
		disconnectBtn.setText("Disconnect");
		main.getContentPane().add(disconnectBtn);
		disconnectBtn.setBounds(10, 40, 110, 25);
		disconnectBtn.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						client.disconnect();
					}
					catch (Exception c) {
						c.printStackTrace();
					}
				}
			}
		);

		// Connect Button
		connectBtn = new JButton();
		connectBtn.setBackground(new Color(0, 0, 255));
		connectBtn.setForeground(new Color(255, 255, 255));
		connectBtn.setText("Connect");
		main.getContentPane().add(connectBtn);
		connectBtn.setBounds(130, 40, 110, 25);
		connectBtn.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					configLogInWindow();
				}
		});

		// Help Button
		helpBtn = new JButton();
		helpBtn.setBackground(new Color(0, 0, 255));
		helpBtn.setForeground(new Color(255, 255, 255));
		helpBtn.setText("Help");
		main.getContentPane().add(helpBtn);
		helpBtn.setBounds(420, 40, 70, 25);

		// About Button
		aboutBtn = new JButton();
		aboutBtn.setBackground(new Color(0, 0, 255));
		aboutBtn.setForeground(new Color(255, 255, 255));
		aboutBtn.setText("About");
		main.getContentPane().add(aboutBtn);
		aboutBtn.setBounds(340, 40, 75, 25);

		// Message Label
		messageLbl = new JLabel("Message: ");
		messageLbl.setText("Message:");
		main.getContentPane().add(messageLbl);
		messageLbl.setBounds(10, 10, 60, 20);

		// Message Field
		messageField = new JTextField(20);
		messageField.setForeground(new Color(0, 0, 255));
		messageField.requestFocus();
		main.getContentPane().add(messageField);
		messageField.setBounds(70, 4, 260, 30);

		// Conversation Label
		convLbl = new JLabel();
		convLbl.setHorizontalAlignment(SwingConstants.CENTER);
		convLbl.setText("Conversation");
		main.getContentPane().add(convLbl);
		convLbl.setBounds(100, 70, 140, 16);

		// Conversation Area
		convArea = new JTextArea();
		convArea.setColumns(20);
		convArea.setFont(new java.awt.Font("Tahoma", 0, 12));
		convArea.setForeground(new Color(0, 0, 255));
		convArea.setLineWrap(true);
		convArea.setRows(5);
		convArea.setEditable(false);

		// Converstation Scroll Pane
		convPane = new JScrollPane();
		convPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		convPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		convPane.setViewportView(convArea);
		main.getContentPane().add(convPane);
		convPane.setBounds(10, 90, 330, 180);

		// Online Label
		onlineLbl = new JLabel();
		onlineLbl.setHorizontalAlignment(SwingConstants.CENTER);
		onlineLbl.setText("Currently Online");
		onlineLbl.setToolTipText("");
		main.getContentPane().add(onlineLbl);
		onlineLbl.setBounds(350, 70, 130, 16);

		// Online List
		onlineList = new JList<String>();
		onlineList.setForeground(new Color(0, 0, 255));

		// Online Scroll Pane
		onlinePane = new JScrollPane();
		onlinePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		onlinePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		onlinePane.setViewportView(onlineList);
		main.getContentPane().add(onlinePane);
		onlinePane.setBounds(350, 90, 130, 180);

		// Logged In As Label
		loginAsLbl = new JLabel();
		loginAsLbl.setFont(new java.awt.Font("Tahoma", 0, 12));
		loginAsLbl.setText("Currently Logged in as");
		main.getContentPane().add(loginAsLbl);
		loginAsLbl.setBounds(348, 0, 140, 15);

		// Logged In As Box Label
		loginAsBoxLbl = new JLabel();
		loginAsBoxLbl.setHorizontalAlignment(SwingConstants.CENTER);
		loginAsBoxLbl.setFont(new java.awt.Font("Tahoma", 0, 12));
		loginAsBoxLbl.setForeground(new Color(255, 0, 0));
		loginAsBoxLbl.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		main.getContentPane().add(loginAsBoxLbl);
		loginAsBoxLbl.setBounds(340, 17, 150, 20);
	}

	public static void initialize() {
		sendBtn.setEnabled(false);
		disconnectBtn.setEnabled(false);
		connectBtn.setEnabled(true);
	}

	public static void connect() {

		try {
			Socket socket = new Socket(HOST_NAME, PORT);
			System.out.println("You connected to: " + HOST_NAME);

			client = new P2P_Chat_Client(socket);

			// Add name to online list
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(userName);
			out.flush();

			Thread T = new Thread(client);
			T.start();
		}
		catch (Exception e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Server not responding.");
			System.exit(0);
		}
	}

	public static void configLogInWindow() {

		loginFrame = new JFrame();
		userNameLbl = new JLabel("Enter a user name: ");
		usernameField = new JTextField(20);
		enterButton = new JButton("Enter");

		loginFrame.setTitle("Please enter your name.");
		loginFrame.setSize(400, 100);
		loginFrame.setLocation(250, 200);
		loginFrame.setResizable(false);
		loginPanel = new JPanel();
		loginPanel.add(userNameLbl);
		loginPanel.add(usernameField);
		loginPanel.add(enterButton);
		loginFrame.add(loginPanel);
		loginFrame.setVisible(true);

		enterButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!usernameField.getText().equals("")) {
						userName = usernameField.getText();
						loginAsBoxLbl.setText(userName);
						main.setTitle(userName + "'s chat client");
						loginFrame.setVisible(false);
						sendBtn.setEnabled(true);
						disconnectBtn.setEnabled(true);
						connectBtn.setEnabled(false);
						connect();
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter a name.");
					}
				}
			}
		);

	}

}