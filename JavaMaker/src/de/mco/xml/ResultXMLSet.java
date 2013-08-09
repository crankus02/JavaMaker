package de.mco.xml;

import java.util.List;

import org.jdom2.Element;

/**
 *
 * @Author Marco Hoff
 */

public class ResultXMLSet {

	private int cursor = 0;
	private Element rootNode;
	private List<Element> mainChild;
	private boolean firstRun = true;

	public ResultXMLSet(Element rootNode, List<Element> mainChild) {
		this.rootNode = rootNode;
		this.mainChild = mainChild;
	}
	public int getInt(String label) {
		return Integer.parseInt(mainChild.get(cursor).getChildText(label));
	}
	public String getString(String label) {
		return mainChild.get(cursor).getChildText(label);
	}
	public long getLong(String label) {
		return Long.parseLong(mainChild.get(cursor).getChildText(label));
	}
	public boolean getBool(String label) {
		return Boolean.parseBoolean(mainChild.get(cursor).getChildText(label));
	}
	public boolean next() {
		if (firstRun == true) {
			firstRun = false;
			return true;
		} else {
			if (cursor == 0 && mainChild.iterator().hasNext()) {
				cursor++;
				return true;
			}
			if (mainChild.iterator().hasNext()
					&& cursor < (mainChild.size() - 1)) {
				cursor++;
				return true;
			} else
				firstRun = true;
			return false;
		}

	}
	public int getCount() {
		return this.mainChild.size();
	}
	public int getCursor() {
		return this.cursor;
	}
}
