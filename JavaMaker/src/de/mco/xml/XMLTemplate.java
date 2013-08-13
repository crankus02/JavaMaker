package de.mco.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @param <T>
 * @Author Marco Hoff
 */

public class XMLTemplate {

	public <T> List<T> getManyObjects(ResultXMLSet xmlSet, XMLMapper<T> xml) {
		List<T> a = new ArrayList<T>();
		while (xmlSet.next()) {

			a.add(xml.mapXML(xmlSet));
		}
		return a;

	}

	public <T> T getOneObject(ResultXMLSet xmlSet, XMLMapper<T> xml) {
		return xml.mapXML(xmlSet);
	}

	public void newXMLObject(String appendChildName, String[] dataTyp,
			String[] dataValue, String xmlPath, Document xmlDoc) {
		Element rootElement = xmlDoc.getRootElement();
		Element mainChild = new Element(appendChildName);
		rootElement.addContent(mainChild);
		for (int i = 0; i < dataTyp.length; i++) {
			Element val = new Element(dataTyp[i]);
			val.addContent(dataValue[i]);
			mainChild.addContent(val);
		}
		XMLFileHandler.saveXMLDoc(xmlPath, xmlDoc);
	}

	public void updateXMLObject(String searchFilter, String search,
			String[] dataTyp, String[] dataValue, String xmlPath,
			Document xmlDoc) {
		Element rootNode = xmlDoc.getRootElement();
		List<Element> list = rootNode.getChildren();
		for (int i = 0; i < list.size(); i++) {
			Element e = list.get(i);
			if (e.getChildText(searchFilter).equals(search)) {
				e.removeContent();
				for (int y = 0; y < dataTyp.length; y++) {
					Element temp = new Element(dataTyp[y]);
					temp.addContent(dataValue[y]);
					e.addContent(temp);
				}

			}
		}
		XMLFileHandler.saveXMLDoc(xmlPath, xmlDoc);
	}

	public void deleteXMLObject(String searchFilter, String search,
			String xmlPath, Document xmlDoc) {
		Element rootNode = xmlDoc.getRootElement();
		List<Element> list = rootNode.getChildren();
		for (int i = 0; i < list.size(); i++) {
			Element e = list.get(i);
			if (e.getChildText(searchFilter).contains(search)) {
				rootNode.removeContent(e);
			}

		}
		XMLFileHandler.saveXMLDoc(xmlPath, xmlDoc);
	}
	public static void main(String[] args) {
		// XMLTemplate t = new XMLTemplate();
		// Document doc = XMLFileHandler.getXMLDoc("./res/Actor.xml");
		// t.newXMLObject("Actor",new String []{"Vorname","Nachname"}, new
		// String[]{"Marcfo","Hoff"}, "./res/Actor.xml", doc);
		// t.updateXMLObject("Marco", doc.getFirstChild(), new
		// String[]{"Vorname",
		// "Nachname", "Name"}, new String[]{"Marco", "Hoff", "Marco Hoff"},
		// "./res/Actor.xml", doc);
	}
}
