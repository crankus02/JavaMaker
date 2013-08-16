package de.mco.skill;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import de.mco.xml.ResultXMLSet;
import de.mco.xml.XMLFileHandler;
import de.mco.xml.XMLMapper;
import de.mco.xml.XMLTemplate;

/**
 *
 * @Author Marco Hoff
 */

public class Skill {

	private int id = 0;
	private String name = "";
	private static String skillXMLPath = "./res/skill.xml";
	private static ResultXMLSet xmlSet = null;
	private static XMLTemplate skillTemplate = new XMLTemplate();
	private static String dataTyp[] = new String[]{"ID", "Name"};

	public static Skill getSkillById(int id) {
		Document doc = XMLFileHandler.getXMLDoc(skillXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		for (int i = 0; i < mainChild.size(); i++) {
			List<Element> tempList = new ArrayList<Element>();
			if (mainChild.get(i).getChildText("ID").equals(String.valueOf(id))) {
				tempList.add(mainChild.get(i));
				if (tempList != null)
					xmlSet = new ResultXMLSet(rootElement, tempList);
				return skillTemplate.getOneObject(xmlSet, new SkillMapper());
			}
		}
		return null;

	}

	public static List<Skill> getAllSkill() {
		Document doc = XMLFileHandler.getXMLDoc(skillXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		xmlSet = new ResultXMLSet(rootElement, mainChild);
		return skillTemplate.getManyObjects(xmlSet, new SkillMapper());

	}
	public static String[] getSkillDataValue(Skill a) {
		return new String[]{String.valueOf(Skill.getLastId()), a.getName()};
	}
	public static void saveSkill(Skill a) {
		skillTemplate.newXMLObject("Skill", dataTyp,
				getSkillDataValue(a),
				skillXMLPath, XMLFileHandler.getXMLDoc(skillXMLPath));
	}
	public static void updateSkill(Skill a) {
		skillTemplate.updateXMLObject("ID", String.valueOf(a.getId()), dataTyp,
				getSkillDataValue(a),
				skillXMLPath, XMLFileHandler.getXMLDoc(skillXMLPath));

	}
	public static void deleteSkill(Skill a) {
		skillTemplate.deleteXMLObject("ID", String.valueOf(a.getId()),
				skillXMLPath, XMLFileHandler.getXMLDoc(skillXMLPath));
	}
	public static int getLastId() {
		List<Skill> list = Skill.getAllSkill();

		return list.get(list.size() - 1).getId() + 1;
	}
	public static void main(String[] args) {

	}
	private static final class SkillMapper implements XMLMapper<Skill> {

		@Override
		public Skill mapXML(ResultXMLSet xmlSet) {
			Skill a = new Skill();
			a.setId(xmlSet.getInt("ID"));
			a.setName(xmlSet.getString("Name"));

			return a;
		}

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
}
