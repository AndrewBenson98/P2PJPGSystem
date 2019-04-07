import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PeerNodeTest {
	
	private int[] array;
	private PeerNodeVersion<Object> peer1;
	private PeerNodeVersion<Object> peer2;
	private PeerNodeVersion<Object> peer3;
	private PeerNodeVersion<Object> peer4;
	private PeerNodeVersion<Object> peer5;
	private PeerNodeVersion<Object> peer6;
	private PeerNodeVersion<Object> peer7;
	private PeerNodeVersion<Object> peer8;
	
	
	@Before
	public void runBeforeEachTest(){
	int[] array = {1,3,4,5,8,10,12,14};
	peer1 = new PeerNodeVersion<Object>("peer1", array[0]);
	peer2 = new PeerNodeVersion<Object>("peer2", array[1]);
	peer3 = new PeerNodeVersion<Object>("peer3", array[2]);
	peer4 = new PeerNodeVersion<Object>("peer4", array[3]);
	peer5 = new PeerNodeVersion<Object>("peer5", array[4]);
	peer6 = new PeerNodeVersion<Object>("peer6", array[5]);
	peer7 = new PeerNodeVersion<Object>("peer7", array[6]);
	peer8 = new PeerNodeVersion<Object>("peer8", array[7]);
	
	peer1.setNextNode(peer2);
	peer2.setNextNode(peer3);
	peer3.setNextNode(peer4);
	peer4.setNextNode(peer5);
	peer5.setNextNode(peer6);
	peer6.setNextNode(peer7);
	peer7.setNextNode(peer8);
	peer8.setNextNode(peer1);
	
	}
	
	@After
	public void runAfterEachTest(){
		peer1.setNextNode(null);
		peer2.setNextNode(null);
		peer3.setNextNode(null);
		peer4.setNextNode(null);
		peer5.setNextNode(null);
		peer6.setNextNode(null);
		peer7.setNextNode(null);
		peer8.setNextNode(null);
		peer1 = null;
		peer2 = null;
		peer3 = null;
		peer4 = null;
		peer5 = null;
		peer6 = null;
		peer7 = null;
		peer8 = null;
	}
	
	
	@Test
	public void testFindClosestNode1() {
		String keyToFind = "13";
		assertEquals(14, peer4.findKeyWithNodeIdentifier(keyToFind));
		
	}
	
	@Test
	public void testFindClosestNode2() {
		String keyToFind = "14";
		assertEquals(14, peer1.findKeyWithNodeIdentifier(keyToFind));
		
	}
	
	@Test
	public void testFindClosestNode3() {
		String keyToFind = "15";
		assertEquals(1, peer3.findKeyWithNodeIdentifier(keyToFind));
		
	}

	@Test
	public void testBetween1(){
		assertTrue(peer4.keyBetween("13", 11, 14));
		
	}
	@Test
	public void testBetween2(){
		assertTrue(peer4.keyBetween("15", 14, 1));
		
	}
	

}
