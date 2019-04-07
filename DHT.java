
public interface DHT<V> {

	public V getValue(int key);
	
	public void add(String key, V element);
	
	public void removePair();
	
	
}
