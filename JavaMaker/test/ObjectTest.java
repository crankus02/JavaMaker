import java.util.ArrayList;
import java.util.List;

import de.mco.actor.Actor;
import de.mco.skill.Skill;
import de.mco.skill.SkillLevelPair;
import de.mco.sort.Sort;
import de.mco.weapon.Weapon;
import de.mco.weapon.WeaponTyp;



/**
 *
 *@Author Marco Hoff
 */

public class ObjectTest {


	public static void main(String[] args) {
		Actor a = new Actor();
		Sort t = new Sort();
		Skill s = new Skill();
		Skill ss = new Skill();
		Weapon w = new Weapon();
		Weapon ww = new Weapon();
		List<SkillLevelPair> skill = new ArrayList<SkillLevelPair>();
		List<WeaponTyp> wList = new ArrayList<WeaponTyp>();
		SkillLevelPair pair = new SkillLevelPair();


		wList.add(WeaponTyp.Bow);
		wList.add(WeaponTyp.Pistol);

		s.setName("fire");
		ss.setName("light");
		s.setId(0);
		ss.setId(1);

		w.setName("Bow");
		ww.setName("Sword");
		w.setId(0);
		ww.setId(1);

		w.setWeaponTyp(WeaponTyp.Bow);
		ww.setWeaponTyp(WeaponTyp.Pistol);

		pair.skillId=s.getId();
		pair.skillLevel=1;
		skill.add(pair);
		pair = new SkillLevelPair();
		pair.skillId=ss.getId();
		pair.skillLevel=5;
		skill.add(pair);



		t.setSkillsToLearn(skill);
		t.setWeaponTyp(wList);
		t.setName("Ritter");
		t.setId(0);

		a.setName("Marco");
		a.setSort(t);
		a.setStartLevel(1);
		a.setEndLevel(99);
		a.setCurrentHP(100);
		a.setMaxHP(100);


		Actor.saveActor(a);
		Sort.saveSort(t);
		Skill.saveSkill(s);
		Skill.saveSkill(ss);
		Weapon.saveWeapon(w);
		Weapon.saveWeapon(ww);



	}
}
