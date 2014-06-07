/**
 * 
 */
package com.tallkids.picsorter.model;

/**
 * @author ott1982
 * Envelope for passing around object references
 */
public class Model<T>
{
	private T payload;
	
	/**
	 * 
	 */
	public Model(T obj) 
	{
		setObject(obj);
	}
	
	public T getObject()
	{
		return (T)payload;
	}
	
	public void setObject(T obj)
	{
		this.payload = obj;
	}
}
