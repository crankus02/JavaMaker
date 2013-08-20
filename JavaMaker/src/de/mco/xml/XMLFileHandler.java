package de.mco.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;







import de.mco.actor.Actor;


/**
 *
 * @Author Marco Hoff
 */

public abstract class XMLFileHandler {

	public static Document getXMLDoc(String path) {
		 SAXBuilder builder = new SAXBuilder();
		  File xmlFile = null;
		 Document doc = null;
			try {
				xmlFile=new File(path);
				if(!xmlFile.exists()){
					xmlFile.createNewFile();
				}
				doc = (Document) builder.build(xmlFile);
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}

		return doc;
	}


	public static void saveXMLDoc(String path, Document doc) {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(doc, new FileWriter(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
