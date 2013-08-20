package de.mco.actor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;

import de.mco.sort.Sort;
import de.mco.xml.ResultXMLSet;
import de.mco.xml.XMLFileHandler;
import de.mco.xml.XMLMapper;
import de.mco.xml.XMLTemplate;

/**
 *
 * @Author Marco Hoff
 */

public class Actor {

	private int id = 0;
	private String name = "";
	private Sort sort = null;
	private int startLevel = 0;
	private int endLevel = 0;
	private int currentHP = 0;
	private int maxHP = 0;

	private static ResultXMLSet xmlSet = null;
	private static String actorXMLPath = "./res/actor.xml";
	private static XMLTemplate actorTemplate = new XMLTemplate();
	private static String dataTyp[] = new String[]{"ID", "Name", "Sort",
			"StartLevel", "EndLevel", "CurrentHP", "MaxHP"};
	private BufferedImage charGrafik = null;
	private BufferedImage faceGrafik = null;

	private static final Logger logger = Logger.getLogger("ActorLogger");



	public static Actor getActorById(int id) {
		Document doc = XMLFileHandler.getXMLDoc(actorXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();

		for (int i = 0; i < mainChild.size(); i++) {
			List<Element> tempList = new ArrayList<Element>();
			if (mainChild.get(i).getChildText("ID").equals(String.valueOf(id))) {
				tempList.add(mainChild.get(i));
				if (tempList != null)
					xmlSet = new ResultXMLSet(rootElement, tempList);
				logger.info("Actor has been founded");
				return actorTemplate.getOneObject(xmlSet, new ActorMapper());
			}
		}
		logger.error("No Actor found, with this ID: "+id);
		return null;
	}

	public static List<Actor> getAllActor() {

		Document doc = XMLFileHandler.getXMLDoc(actorXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		xmlSet = new ResultXMLSet(rootElement, mainChild);
		if(mainChild!=null&&mainChild.size()!=0){
			logger.info("Actor List created");
		return actorTemplate.getManyObjects(xmlSet, new ActorMapper());}
		else{
			logger.info("No Actor in XML");
			return null;}

	}
	public static String [] getActorDataValue(Actor a){
		return new String[]{String.valueOf(Actor.getLastId()), a.getName(),
				String.valueOf(a.getSort().getId()),
				String.valueOf(a.getStartLevel()),
				String.valueOf(a.getEndLevel()),
				String.valueOf(a.getCurrentHP()),
				String.valueOf(a.getMaxHP()),

		};

	}
	public static void saveActor(Actor a) {
		actorTemplate.newXMLObject(
				"Actor",
				dataTyp,
				getActorDataValue(a), actorXMLPath,
				XMLFileHandler.getXMLDoc(actorXMLPath));
		logger.info("Actor has been saved: "+a.getName()+" : "+a.getId());
	}
	public static void updateActor(Actor a) {
		actorTemplate.updateXMLObject(
				"ID",
				String.valueOf(a.getId()),
				dataTyp,
				getActorDataValue(a), actorXMLPath,
				XMLFileHandler.getXMLDoc(actorXMLPath));
		logger.info("Actor has been updated: "+a.getName()+" : "+a.getId());
	}
	public static void deleteActor(Actor a) {
		actorTemplate.deleteXMLObject("ID", String.valueOf(a.getId()),
				actorXMLPath, XMLFileHandler.getXMLDoc(actorXMLPath));
		logger.info("Actor has been deleted: "+a.getName()+" : "+a.getId());
	}
	public static int getLastId() {
		if(Actor.getAllActor()==null){
			return 0;
		}
		List<Actor> list = Actor.getAllActor();
		logger.info("Last Actor ID IS: "+list.get(list.size() - 1).getId() + 1);
		return list.get(list.size() - 1).getId() + 1;
	}
	public static void main(String[] args) {
		Actor a = new Actor();
		a.setName("IDTEST");
		Actor.saveActor(a);
		System.out.println(Actor.getActorById(0).getName());
		/*
		 * a.setName("change 2"); Actor.updateActor(a);
		 * System.out.println(Actor.getActorById(0).getName());
		 * Actor.deleteActor(a);
		 * System.out.println(Actor.getActorById(0).getName());
		 */
	}
	private static final class ActorMapper implements XMLMapper<Actor> {

		@Override
		public Actor mapXML(ResultXMLSet xmlSet) {
			Actor a = new Actor();
			a.setId(xmlSet.getInt("ID"));
			a.setName(xmlSet.getString("Name"));
			a.setSort(Sort.getSortById(xmlSet.getInt("Sort")));
			a.setStartLevel(xmlSet.getInt("StartLevel"));
			a.setEndLevel(xmlSet.getInt("EndLevel"));
			a.setCurrentHP(xmlSet.getInt("CurrentHP"));
			a.setMaxHP(xmlSet.getInt("MaxHP"));
			logger.info("Actor successfully mapped");
			return a;
		}

	}

	@Override
	public String toString(){
		return this.getId()+" : "+this.getName();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public int getEndLevel() {
		return endLevel;
	}

	public void setEndLevel(int endLevel) {
		this.endLevel = endLevel;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public BufferedImage getCharGrafik() {
		return charGrafik;
	}

	public void setCharGrafik(BufferedImage charGrafik) {
		this.charGrafik = charGrafik;
	}

	public BufferedImage getFaceGrafik() {
		return faceGrafik;
	}

	public void setFaceGrafik(BufferedImage faceGrafik) {
		this.faceGrafik = faceGrafik;
	}
}
