package de.mco.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		Node rootNode = xmlDoc.getFirstChild();
		Node mainNode = xmlDoc.createElement(appendChildName);
		rootNode.appendChild(mainNode);
		for (int i = 0; i < dataTyp.length; i++) {
			Element val = xmlDoc.createElement(dataTyp[i]);
			val.appendChild(xmlDoc.createTextNode(dataValue[i]));
			mainNode.appendChild(val);
		}
		XMLFileHandler.saveXMLDoc(xmlPath, xmlDoc);
	}

	public void updateXMLObject(String searchFilter, Node rootChild,
			String[] dataTyp, String[] dataValue, String xmlPath,
			Document xmlDoc) {
		NodeList list = rootChild.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getTextContent().contains(searchFilter)) {
				for (Node child; (child = node.getFirstChild()) != null; node
						.removeChild(child));
				if (dataTyp.length != 0) {
					for (int y = 0; y < dataTyp.length; y++) {
						Element val = xmlDoc.createElement(dataTyp[y]);
						val.appendChild(xmlDoc.createTextNode(dataValue[y]));
						node.appendChild(val);
					}
				}
			}
		}
		XMLFileHandler.saveXMLDoc(xmlPath, xmlDoc);
	}
	public static void main(String[] args) {
		//XMLTemplate t = new XMLTemplate();
		//Document doc = XMLFileHandler.getXMLDoc("./res/Actor.xml");
		// t.newXMLObject("Actor",new String []{"Vorname","Nachname"}, new
		// String[]{"Marcfo","Hoff"}, "./res/Actor.xml", doc);
		//t.updateXMLObject("Marco", doc.getFirstChild(), new String[]{"Vorname",
			//	"Nachname", "Name"}, new String[]{"Marco", "Hoff", "Marco Hoff"},
			//	"./res/Actor.xml", doc);
	}
}
