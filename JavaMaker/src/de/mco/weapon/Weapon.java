package de.mco.weapon;

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

public class Weapon {
	private int id = 0;
	private String name = "";
	private static String weaponXMLPath = "./res/weapon.xml";
	private static ResultXMLSet xmlSet = null;
	private static XMLTemplate weaponTemplate = new XMLTemplate();
	private static String dataTyp[] = new String[]{"ID", "Name", "WeaponTyp"};
	private WeaponTyp weaponTyp = null;

	public static Weapon getWeaponById(int id) {
		Document doc = XMLFileHandler.getXMLDoc(weaponXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		for (int i = 0; i < mainChild.size(); i++) {
			List<Element> tempList = new ArrayList<Element>();
			if (mainChild.get(i).getChildText("ID").equals(String.valueOf(id))) {
				tempList.add(mainChild.get(i));
				if (tempList != null)
					xmlSet = new ResultXMLSet(rootElement, tempList);
				return weaponTemplate.getOneObject(xmlSet, new WeaponMapper());
			}
		}
		return null;

	}

	public static List<Weapon> getAllWeapon() {
		Document doc = XMLFileHandler.getXMLDoc(weaponXMLPath);
		Element rootElement = doc.getRootElement();
		List<Element> mainChild = rootElement.getChildren();
		xmlSet = new ResultXMLSet(rootElement, mainChild);
		return weaponTemplate.getManyObjects(xmlSet, new WeaponMapper());

	}
	public static String[] getWeaponDataValue(Weapon a) {
		return new String[]{String.valueOf(Weapon.getLastId()), a.getName(),
				a.getWeaponTyp().toString()};
	}
	public static void saveWeapon(Weapon a) {
		weaponTemplate.newXMLObject("Weapon", dataTyp, getWeaponDataValue(a),
				weaponXMLPath, XMLFileHandler.getXMLDoc(weaponXMLPath));
	}
	public static void updateWeapon(Weapon a) {
		weaponTemplate.updateXMLObject("ID", String.valueOf(a.getId()),
				dataTyp, getWeaponDataValue(a), weaponXMLPath,
				XMLFileHandler.getXMLDoc(weaponXMLPath));

	}
	public static void deleteWeapon(Weapon a) {
		weaponTemplate.deleteXMLObject("ID", String.valueOf(a.getId()),
				weaponXMLPath, XMLFileHandler.getXMLDoc(weaponXMLPath));
	}
	public static int getLastId() {
		List<Weapon> list = Weapon.getAllWeapon();
		return list.get(list.size() - 1).getId() + 1;
	}
	public static void main(String[] args) {

	}
	private static final class WeaponMapper implements XMLMapper<Weapon> {

		@Override
		public Weapon mapXML(ResultXMLSet xmlSet) {
			Weapon a = new Weapon();
			a.setId(xmlSet.getInt("ID"));
			a.setName(xmlSet.getString("Name"));
			a.setWeaponTyp(WeaponTyp.valueOf(xmlSet.getString("WeaponTyp")));
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

	public WeaponTyp getWeaponTyp() {
		return weaponTyp;
	}

	public void setWeaponTyp(WeaponTyp weaponTyp) {
		this.weaponTyp = weaponTyp;
	}
}
