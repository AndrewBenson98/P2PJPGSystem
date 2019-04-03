import java.io.*;
import java.net.*;

/**
 * This program is to run the P2PServer. The P2PServer first updates and informs
 * the directory server of what jpg it has. It then waits for any p2pclient to
 * connect to it and request a jpg
 * 
 * @author Andrew Benon 500745614
 *
 */
public class P2PServer {
	/**
	 * Main method for P2PServer
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String[] jpgList = { "DaveMasonDab.jpg" };
		// Send name of jpg to Directory servers via UDP
		InformAndUpdate("DaveMasonDab.jpg", 9877);

		// Wait for Client to connect via TCP
		TCPServer(jpgList, 9877);
	}

	/**
	 * Informs and updates the directory servers of what jpg it has via udp
	 * @param jpg the name of the jpg
	 * @param port the port number used to connect
	 * @throws IOException
	 */
	static void InformAndUpdate(String jpg, int port) throws IOException {
		// Send name of jpg to Directory servers via UDP

		// BufferedReader inFromUser = new BufferedReader(new
		// InputStreamReader(System.in));

		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress IPAddress = InetAddress.getByName("localhost");
		System.out.println(IPAddress);

		byte[] sendData = new byte[1024];

		// Type input
		// String sentence = inFromUser.readLine();
		String data = "1:" + jpg;
		sendData = data.getBytes();

		// Create Datagram with data, length, ip addr, port
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

		clientSocket.close();
	}

	/**
	 * Waits for client to connect and request jpg. P2P server then sends jpg to client
	 * @param jpgList 
	 * @param port
	 * @throws Exception
	 */
	static void TCPServer(String[] jpgList, int port) throws Exception {
		// Create server socket
		ServerSocket ss = new ServerSocket(port);
		// Wait for client to connect
		System.out.println("Waiting for client to connect...");
		Socket s = ss.accept();

		System.out.println("Client Connected");

		// create input reader and print writer
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		// Read what client is requesting
		String str = bf.readLine();
		System.out.println("Client: " + str);
		// Send jpb with that name
		pr.println("I have:" + str);
		pr.flush();
			
		ss.close();

	}

}
