package de.mco.sort;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;

import de.mco.actor.Actor;
import de.mco.skill.SkillLevelPair;
import de.mco.weapon.WeaponTyp;
import de.mco.xml.ResultXMLSet;
import de.mco.xml.XMLFileHandler;
import de.mco.xml.XMLMapper;
import de.mco.xml.XMLTemplate;

/**
 *
 * @Author Marco Hoff
 */

public class Sort {

	private int id = 0;
	private String name = "";
	private static String sortXMLPath = "./res/sort.xml";
	private static String sortSkillXMLPath = "./res/sortskill.xml";
	private static ResultXMLSet xmlSet = null;
	private static XMLTemplate sortTemplate = new XMLTemplate();
	private static String dataTyp[] = new String[]{"ID", "Name", "WeaponTyp"};
	private static String skillDataTyp[] = new String[]{"ClassID", "SkillID",
			"Level"};
	private List<WeaponTyp> weaponTyp = null;
	private List<SkillLevelPair> skillsToLearn = null;
	private static final Logger logger = Logger.getLogger("SortLogger");

	public static Sort getSortById(int id) {
		Document doc = XMLFileHandler.getXMLDoc(sortXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		for (int i = 0; i < mainChild.size(); i++) {
			List<Element> tempList = new ArrayList<Element>();
			if (mainChild.get(i).getChildText("ID").equals(String.valueOf(id))) {
				tempList.add(mainChild.get(i));
				if (tempList != null)
					xmlSet = new ResultXMLSet(rootElement, tempList);
				logger.info("Get Sort by ID");
				return sortTemplate.getOneObject(xmlSet, new SortMapper());
			}
		}
		logger.error("No Sort found, with this ID: "+id);
		return null;
	}

	public static List<SkillLevelPair> fillSkillList(int id) {
		Document doc = XMLFileHandler.getXMLDoc(sortSkillXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		xmlSet = new ResultXMLSet(rootElement, mainChild);
		logger.info("Fill SkillLevel list");
		return sortTemplate.getManyObjects(xmlSet, new SkillLevelPairMapper());
	}

	public static List<Sort> getAllSort() {
		Document doc = XMLFileHandler.getXMLDoc(sortXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		xmlSet = new ResultXMLSet(rootElement, mainChild);
		if(mainChild!=null&&mainChild.size()!=0){
			logger.info("Sort list created");
		return sortTemplate.getManyObjects(xmlSet, new SortMapper());}
		else{
			logger.info("No Sorts found in XML");
			return null;}

	}

	public static String[] getSortDataValue(Sort a) {
		return new String[]{String.valueOf(Sort.getLastId()), a.getName(),
				 a.getWeaponTyp().toString()};
	}
	public static void saveSort(Sort a) {
		sortTemplate.newXMLObject("Sort", dataTyp,
				getSortDataValue(a),
				sortXMLPath, XMLFileHandler.getXMLDoc(sortXMLPath));
		logger.info("Sort: "+a.getName()+" : "+a.getId()+" has been saved");
		for (int i = 0; i < a.getSkillsToLearn().size(); i++) {
			sortTemplate
					.newXMLObject(
							"Skill",
							skillDataTyp,
							new String[]{
									String.valueOf(a.getId()),
									String.valueOf(a.getSkillsToLearn().get(i).skillId),
									String.valueOf(a.getSkillsToLearn().get(i).skillLevel)},
							sortSkillXMLPath, XMLFileHandler
									.getXMLDoc(sortSkillXMLPath));
			logger.info("SkillLevel Number: "+i+" has been saved");
		}
	}
	public static void main(String[] args) {
		Sort s = new Sort();
		s.setId(0);
		s.setName("Name");
		SkillLevelPair t = new SkillLevelPair();
		t.skillId = 2;
		t.skillLevel = 1;
		List<SkillLevelPair> g = new ArrayList<SkillLevelPair>();
		g.add(t);
		s.setSkillsToLearn(g);
		Sort.saveSort(s);
		Sort l = Sort.getSortById(0);
		System.out.println("Skill id: " + l.getSkillsToLearn().get(5).skillId);
	}
	public static void updateSort(Sort a) {
		sortTemplate.updateXMLObject(
				"ID",
				String.valueOf(a.getId()),
				dataTyp,
				getSortDataValue(a), sortXMLPath,
				XMLFileHandler.getXMLDoc(sortXMLPath));
		logger.info("Sort: "+a.getName()+" : "+a.getId()+" has been updated");
	}
	public static void updateSortSkill(Sort a) {
		for (int i = 0; i < a.getSkillsToLearn().size(); i++) {
			sortTemplate
					.updateXMLObject(
							"ClassID",
							String.valueOf(a.getId()),
							skillDataTyp,
							new String[]{
									String.valueOf(a.getId()),
									String.valueOf(a.getSkillsToLearn().get(i).skillId),
									String.valueOf(a.getSkillsToLearn().get(i).skillLevel)},
							sortSkillXMLPath, XMLFileHandler
									.getXMLDoc(sortSkillXMLPath));
			logger.info("SkillLevel has been updates");
		}
	}

	public static void deleteSort(Sort a) {
		sortTemplate.deleteXMLObject("ID", String.valueOf(a.getId()),
				sortXMLPath, XMLFileHandler.getXMLDoc(sortXMLPath));
		sortTemplate.deleteXMLObject("ClassID", String.valueOf(a.getId()),
				sortSkillXMLPath, XMLFileHandler.getXMLDoc(sortSkillXMLPath));
		logger.info("Sort: "+a.getName()+" : "+a.getId() + " has been deleted");
	}

	public static int getLastId() {
		if(Sort.getAllSort()==null){
			return 0;
		}
		List<Sort> list = Sort.getAllSort();
		logger.info("Get Last Sort ID: "+ list.get(list.size() - 1).getId() + 1);
		return list.get(list.size() - 1).getId() + 1;
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

	private static final class SortMapper implements XMLMapper<Sort> {

		@Override
		public Sort mapXML(ResultXMLSet xmlSet) {
			Sort a = new Sort();
			a.setId(xmlSet.getInt("ID"));
			a.setName(xmlSet.getString("Name"));
			a.setSkillsToLearn(Sort.fillSkillList(a.getId()));
			//a.fillWeaponTyp(xmlSet.getString("WeaponTyp"));
			logger.info("Sort hast been mapped");
			return a;
		}

	}
	public void fillWeaponTyp(String s) {
		String temp[] = s.split("/");
		for (int i = 0; i < temp.length; i++) {
			this.weaponTyp.add(WeaponTyp.valueOf(temp[i]));
			logger.info(temp[i]+" added to WeaponTyplist");
		}

	}

	private static final class SkillLevelPairMapper
			implements
				XMLMapper<SkillLevelPair> {

		@Override
		public SkillLevelPair mapXML(ResultXMLSet xmlSet) {
			SkillLevelPair a = new SkillLevelPair();
			a.skillId = xmlSet.getInt("SkillID");
			a.skillLevel = xmlSet.getInt("Level");
			logger.info("SkillLevel has been mapped : "+a.skillId);
			return a;
		}

	}


	public List<SkillLevelPair> getSkillsToLearn() {
		return skillsToLearn;
	}

	public void setSkillsToLearn(List<SkillLevelPair> list) {
		this.skillsToLearn = list;
	}

	public List<WeaponTyp> getWeaponTyp() {
		return weaponTyp;
	}

	public void setWeaponTyp(List<WeaponTyp> weaponTyp) {
		this.weaponTyp = weaponTyp;
	}

	@Override
	public String toString(){
		return this.getId()+" : "+this.getName();
	}
}
