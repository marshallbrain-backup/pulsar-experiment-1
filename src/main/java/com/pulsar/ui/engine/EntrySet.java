package ui.engine;

public class EntrySet<K, V> {
	
	K key;
	V value;
	
	public EntrySet(K k, V v) {
		key = k;
		value = v;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}

}
