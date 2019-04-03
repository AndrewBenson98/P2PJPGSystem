import java.io.*;
import java.net.*;

/**
 * This program runs the directory server. It has a main loop that accepts
 * requests for jpgs from P2PClients and Inform/Update packets from P2PServer.
 * When a client requests a jpg, it returns the ip address of p2pserver that has
 * the jpg. When it receives a packet to inform/update, it updates a hashtable
 * that contains the names of jpgs and their associated ip addresses
 * 
 * @author Andrew Benon 500745614
 *
 */
public class DirectoryServer {

	/**
	 * Runs the main loop for the directory server
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// Create Socket
		DatagramSocket serverSocket = new DatagramSocket(9877);

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		// while true, Server reads in UDP packets it receives and sends the same
		// message back in capital letters
		while (true) {
			// Create space for received datagram
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			// Wait to receive packet
			serverSocket.receive(receivePacket);

			String data = new String(receivePacket.getData());

			// Get the IP address of the packet you just received
			InetAddress IPAddress = receivePacket.getAddress();
			// Get the port from packet
			int port = receivePacket.getPort();

			// Determine if the data is a request or an Inform/Update
			// If the data starts with 0 then request. If 1 then update
			// Format of data should be e.g. 0:jpg.jpg WITH colon :
			char type = data.charAt(0);
			String jpgName = data.substring(2);

			if (type == '0') {
				// Seach Hashtable for the value of jpgName and set sendData to be the value of
				// that jpg's IPAddress

				System.out.println("Tpye: Request \t Finding Data for " + jpgName);

				// sendData = (Get IP Address).getBytes();
				// Send the ip address back to p2pclient
				sendData = "127.357.362.12".getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);

			} else if (type == '1') {
				// update the hashtable with jpgName and IPAddress

				System.out.println("Tpye: Inform/Update \t Data being saved ");

			} else {
				System.out.println("Bad Data");
				sendData = "Incorrect Data Format".getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}

		}
	}

}
