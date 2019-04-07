
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PeerNodeVersion<E> implements Node<E>, DHT<E> {

	
	private Node<E> next;
	private Node<E> previous;
	
	private String nameOfNode;
	private String Ip;
	private int identifier;
	private String key;
	
	private HashMap<String, E> subset = new HashMap<>();
	private Map<Integer, Node<E>> circle = new LinkedHashMap<>();
	
	private int n;
	
	public PeerNodeVersion(String name, int identifier){
		this.nameOfNode = name;
		this.identifier = identifier;
		this.n = 4;
	}
	
	@Override
	public E getValue(int key) {
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public Node<E> getNextNode() {
		// TODO Auto-generated method stub
		return this.next;
	}
	@Override
	public Node<E> getPreviousNode() {
		// TODO Auto-generated method stub
		return this.previous;
	}
	@Override
	public void setNextNode(Node<E> next) {
		// TODO Auto-generated method stub
		this.next = next;
	}
	@Override
	public void setPreviousNode(Node<E> previous) {
		this.previous = previous;
		
	}
	@Override
	public int findKeyWithNodeIdentifier(String key) {
		if(keyBetween(key, this.identifier,this.next.getIdentifier())){
		return next.getIdentifier();
	}else{
		return this.next.findKeyWithNodeIdentifier(key);
	}

	}
	
	// helper function to see if key is between the identifeirs first and second
	public boolean keyBetween(String key, int first, int second) {
		int keyInt = Integer.parseInt(key); 
		if(first > second) {
			return ((keyInt > first) || (keyInt <= second));
		}else if(first < second)
			return ((keyInt > first) && (keyInt <= second));
		else
			return true;		
	}
	

	// check if key# is closer to next or itself.

	@Override
	public E get(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void add(String ket, E value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public HashMap<String, E> getSubset() {
		// TODO Auto-generated method stub
		return this.subset;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePair() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIdentifier() {
		// TODO Auto-generated method stub
		return this.identifier;
	}

	
	
}