package problem.pattern;

public class EagerSingleton {
	private volatile static EagerSingleton uniqueInstance = new EagerSingleton();
	
	private EagerSingleton() {
		
	}
	
	public static EagerSingleton getInstance() {
		return uniqueInstance;
	}
}
