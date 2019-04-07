import java.rmi.Remote;
import java.util.HashMap;
import java.util.List;

public interface Node<V> extends Remote {

	public Node<V> getNextNode();
	
	public Node<V> getPreviousNode();
	
	public void setNextNode(Node<V> next);
	
	public void setPreviousNode(Node<V> previous);
	
	public V get(String key);
	
	public void add(String ket, V value);
	
	public void remove(String key);
	
	public HashMap<String,V> getSubset();
	
	public String getKey();

	public int findKeyWithNodeIdentifier(String key);

	public int getIdentifier();
	
	
	
	
}
