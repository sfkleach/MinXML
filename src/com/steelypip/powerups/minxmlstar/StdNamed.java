package com.steelypip.powerups.minxmlstar;

public class StdNamed< T > {
	
	private String name;
	private T value;
	
	public StdNamed( String name, T value ) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() { return this.name; }
	public void setName( String x ) { this.name = x; }
	public T getValue() { return this.value; }
}
