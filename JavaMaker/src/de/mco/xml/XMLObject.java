
package de.mco.xml;

import java.util.List;

/**
 *
 *@Author Marco Hoff
 */

public interface XMLObject <T>{

	public T getByID(T o);
	public String [] getDataValue(T o);
	public List<T> getAll();
	public T save(T o);
	public T update(T o);
	public int getLastID();
	public void delete(T o);

}
