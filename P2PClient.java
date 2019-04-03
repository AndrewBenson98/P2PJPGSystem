import java.io.*;
import java.net.*;

/**
 * This program will request a jpg from directory server. Directory server will
 * reply with an ip address where client can find requested jpg. P2PClient then
 * connects to P2PServer via TCP and requests and receives jpg
 * 
 * @author Andrew Benon 500745614
 *
 */
public class P2PClient {

	/**
	 * Runs the Client program.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String jpgName = "DaveMasonDab.jpg";
		String IPAddress = RequestJpg(jpgName, 9877);

		ConnectToHost(IPAddress, jpgName, 9877);

	}

	/**
	 * Requests the jpg from directory servers
	 * @param jpgName the name of the requested jpg
	 * @param port the port number used to connect
	 * @return a String that is the IP Address of the P2PServer
	 * @throws Exception
	 */
	static String RequestJpg(String jpgName, int port) throws Exception {
		// Send request to directory servers for jpg via udp

		// create input stream
		// BufferedReader inFromUser = new BufferedReader(new
		// InputStreamReader(System.in));

		DatagramSocket clientSocket = new DatagramSocket();

		// IPAddress goes here
		InetAddress IPAddress = InetAddress.getByName("hostname");
		System.out.println(IPAddress);

		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];

		// Type input
		// String sentence = inFromUser.readLine();
		String data = "0:" + jpgName;
		sendData = data.getBytes();

		// Create Datagram with data, length, ip addr, port
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// Wait to receive a packet and store it
		clientSocket.receive(receivePacket);
		String hostIPAddress = new String(receivePacket.getData());

		// System.out.println("FROM SERVER:" + hostIPAddress);
		clientSocket.close();
		return hostIPAddress;
	}

	/**
	 * Connects to the P2PServer and requests jpg
	 * @param IPAddress the IP of the server
	 * @param jpgName the name of the requested jpg
	 * @param port the port number used to connect
	 * @throws Exception
	 */
	static void ConnectToHost(String IPAddress, String jpgName, int port) throws Exception {

		// create socket and send request to p2pServer
		Socket s = new Socket("localhost", port);
		// Create Print Writer
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		// Send server the name of requested jpg
		pr.println(jpgName);
		pr.flush();

		// Create stream reader and buffered reader
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		// Read what server is sending (get jpg from server)
		String str = bf.readLine();
		System.out.println("Server: " + str);
		s.close();
	}

}
