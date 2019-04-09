import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

import javax.imageio.ImageIO;

/**
 * This program is to run the P2PServer. The P2PServer first updates and informs
 * the directory server of what jpg it has. It then waits for any p2pclient to
 * connect to it and request a jpg
 * 
 * @author Andrew Benson (500745614), William Wong(500754922), Aravinth
 * 
 *
 */
public class P2PServer {

	static BufferedImage bimg;
	byte[] bytes;

	/**
	 * Main method for P2PServer
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		 // Send name of jpg to Directory servers via UDP
		 InformAndUpdate("pingu.jpg", 20170);
		 
		 //Do another inform and update for a second picture
		 InformAndUpdate("NOOT.jpg", 20170);
		 
		// Wait for Client to connect via TCP
		TCPServer(20171);
		TCPServer(20171);
	}

	/**
	 * Informs and updates the directory servers of what jpg it has via udp
	 * 
	 * @param jpg  the name of the jpg
	 * @param port the port number used to connect
	 * @throws IOException
	 */
	static void InformAndUpdate(String jpg, int port) throws IOException 
	{
		// Send name of jpg to Directory servers via UDP
		System.out.println("Informing Directory Server...");
		DatagramSocket clientSocket = new DatagramSocket();

		//change to ip of directory server
		InetAddress IPAddress = InetAddress.getByName("ENG201-05");
		
		//local host testing
		//InetAddress IPAddress = InetAddress.getByName("localhost");
		//System.out.println(IPAddress.getHostAddress());
		
		byte[] sendData = new byte[1024];

		// Type input
		String data = "1:" + jpg;
		sendData = data.getBytes();

		// Create Datagram with data, length, ip addr, port
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		System.out.println("Directory Server Informed");
		clientSocket.close();
	}

	/**
	 * Waits for client to connect and request jpg. P2P server then sends jpg to
	 * client
	 * 
	 * @param jpgList
	 * @param port
	 * @throws Exception
	 */
	static void TCPServer(int port) throws Exception {
		// Create server socket

		ServerSocket ss = new ServerSocket(port);
		// Wait for client to connect
		System.out.println("Waiting for client to connect...");

		Socket s = ss.accept();
		s.setSoTimeout(180000);

		System.out.println("Client Connected");

		// create input reader and print writer
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);

		while (true) 
		{
			try 
			{

				// Read what client is requesting
				String jpgName = bf.readLine();
				System.out.println("Client: " + jpgName);

				// Location of file on computer
				bimg = ImageIO.read(new File("H:\\CPS 706\\"+jpgName));
				ImageIO.write(bimg, "JPG", s.getOutputStream());
				ImageIO.write(bimg, "JPG", s.getOutputStream());
				System.out.println("Image sent!!!!");
				s.getOutputStream().flush();
			}
			//if socket is already closed
			catch (SocketException st) 
			{
				System.out.println("Socket Closed!");
				break;
			} 
			//if socket timed out
			catch (SocketTimeoutException st)
			{
				System.out.println("Socket timed out!");
				break;
			}
			// literally any other thing
			catch (Exception ex) 
			{
				System.out.println(ex);
				break;
			}

		}

		ss.close();
		System.out.println("Server Closed");

	}
}
