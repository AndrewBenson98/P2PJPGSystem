import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;

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
		String IPAddress = RequestJpg(jpgName, 9882);
		
		ConnectToHost(IPAddress, jpgName, 9883);
		
		//Request another picture and connect to host again

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
		System.out.println("Requesting "+jpgName);
		DatagramSocket clientSocket = new DatagramSocket();

		// dirc server ip goes here
		InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
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
		System.out.println("Host IP Adress for "+jpgName +" is "+hostIPAddress);
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
		
		System.out.println("Connecting to P2P Server...");
		IPAddress = IPAddress.substring(1);//Trim off the / 
		// create socket and send request to p2pServer
		Socket s = new Socket(IPAddress, port);
		// Create Print Writer
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		// Send server the name of requested jpg
		pr.println(jpgName);
		pr.flush();
		System.out.println("Requesting "+jpgName);
		// Create stream reader and buffered reader
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		// Read what server is sending (get jpg from server)
		//String str = bf.readLine();
		//System.out.println("Server: " + str);
		
		
		//Get Jpg
		BufferedImage img=ImageIO.read(ImageIO.createImageInputStream(s.getInputStream()));
		System.out.println("Image received!!!!"); 
		//Location where file is to be saved
		File f = new File("D:\\Users\\Andrew\\Documents\\Ryerson\\CPS706 - Networks\\ProjectImage\\A123"+jpgName);
        ImageIO.write(img,"jpg",f);
		
		s.close();
		System.out.println("Client Closed");
	}

}
