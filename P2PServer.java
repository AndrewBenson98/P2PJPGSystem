import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;

/**
 * This program is to run the P2PServer. The P2PServer first updates and informs
 * the directory server of what jpg it has. It then waits for any p2pclient to
 * connect to it and request a jpg
 * 
 * @author Andrew Benon 500745614
 *
 */
public class P2PServer {
	
	
	 static BufferedImage bimg;
	    byte[] bytes;
	
	/**
	 * Main method for P2PServer
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String[] jpgList = { "DaveMasonDab.jpg" };
		// Send name of jpg to Directory servers via UDP
		//InformAndUpdate("DaveMasonDab.jpg", 9877);

		// Wait for Client to connect via TCP
		TCPServer(jpgList, 9878);
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
		//pr.println("I have:" + str);
		//pr.flush();
		
		
		//Get Jpg
	while(true) {
		
		 try
         {
			 //Location of file on computer
		bimg = ImageIO.read(new File("D:\\Users\\Andrew\\Documents\\Ryerson\\CPS706 - Networks\\ProjectImage\\DaveMasonDab.jpg"));
		ImageIO.write(bimg,"JPG",s.getOutputStream());
        System.out.println("Image sent!!!!");
         }
        catch(SocketException st)
        {
              System.out.println("Socket Closed!");
             break;
        }
        
	}
       
        ss.close();

	
	}
}
