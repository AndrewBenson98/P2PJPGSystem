import java.io.*;
import java.net.*;
import java.util.Hashtable;

/**
 * This program runs the directory server. It has a main loop that accepts
 * requests for jpgs from P2PClients and Inform/Update packets from P2PServer.
 * When a client requests a jpg, it returns the ip address of p2pserver that has
 * the jpg. When it receives a packet to inform/update, it updates a hashtable
 * that contains the names of jpgs and their associated ip addresses
 * 
 * @author Andrew Benson (500745614), William Wong(500754922), Aravinth
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
		DatagramSocket serverSocket = new DatagramSocket(20170);
		Hashtable<String, String> contentList = new Hashtable<String, String>();

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		while (true) {

			// Create space for received datagram
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			// Wait to receive packet
			serverSocket.receive(receivePacket);
			String data = new String(receivePacket.getData());

			// Get the IP address of the packet you just received
			InetAddress IPAddress = receivePacket.getAddress();
			System.out.println(IPAddress.getHostAddress());

			// Get the port from packet
			int port = receivePacket.getPort();

			// Determine if the data is a request or an Inform/Update
			// If the data starts with 0 then request. If 1 then update
			// Format of data should be e.g. 0:jpg.jpg WITH colon :
			char type = data.charAt(0);
			String jpgName = data.substring(2).trim();
			//contentList.put(jpgName, IPAddress.getHostName());// idk if this is
																// what i should
																// do said
																// "ARA".
			System.out.println("Host Name: " + IPAddress.getHostName());

			if (type == '0') {
				// Seach Hashtable for the value of jpgName and set sendData to
				// be the value of that jpg's IPAddress
				System.out.println("Getting.... "+contentList.get(jpgName) );
				sendData = contentList.get(jpgName).getBytes();
				System.out.println("Type: Request \t Finding Data for "
						+ jpgName);

				// Send the ip address back to p2pclient
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);

			} else if (type == '1') {
				// update the hashtable with jpgName and IPAddress
				contentList.put(jpgName, IPAddress.getHostName());
				System.out.println("Type: Inform/Update \t Data being saved ");
				System.out.println(contentList.toString());
			} else {
				System.out.println("Bad Data");
				sendData = "Incorrect Data Format".getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}

		}
	}

	/**
	 * to determine which of the 4 Servor ID
	 * 
	 * @param stringKey
	 * @return
	 */
	private static int hashKeyStringToInt(String stringKey) {
		int sumOfChar = 0;
		char[] charArray = stringKey.toCharArray();
		int asciiTemp = 0;
		int y;

		for (int i = 0; i < charArray.length; i++) {
			asciiTemp = (int) charArray[i];
			sumOfChar = sumOfChar + asciiTemp;
		}

		y = sumOfChar % 4;
		return 0;
	}

}
