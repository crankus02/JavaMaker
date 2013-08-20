package de.mco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import de.mco.gui.function.ActorDatabaseFunction;
import de.mco.gui.specials.GrafikButton;

/**
 *
 * @Author Marco Hoff
 */

public class ActorDatabaseGUI extends JPanel {

	private JLabel mainTitle = null;
	private JLabel secondTitle = null;
	private JList mainList = null;
	private JLabel nameLabel = null;
	private JTextField nameTF = null;
	private JLabel sortLabel = null;
	private JComboBox sortCombobox = null;
	private JLabel startLevel = null;
	private JLabel endLevel = null;
	private JSpinner startLevelSpinner = null;
	private JSpinner endLevelSpinner = null;
	private JLabel charGrafikLabel = null;
	private JLabel faceGrafikLabel = null;
	private JPanel listPanel = null;
	private JScrollPane scrollPane = null;
	private JPanel namePanel = null;
	private JPanel grafikPanel = null;
	private JPanel attributePanel = null;
	private JPanel weaponPanel = null;
	private ActorDatabaseFunction actorFunction = null;
	private JButton save = null;
	private JButton accept = null;
	private JButton cancel = null;
	private JPanel centerPanel = null;
	private SpinnerModel spinnerModel = null;



	public void initComponents() {


		//MainPanel inits
		this.setLayout(new BorderLayout());
		try {
			UIManager
					.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}


		//Component inits
		spinnerModel = new SpinnerNumberModel(0,0,99,1);
		actorFunction = new ActorDatabaseFunction();
		mainTitle = new JLabel("Actor");
		secondTitle = new JLabel("Actorlist:");
		mainList = new JList(actorFunction.getActorList());
		nameLabel = new JLabel("Name: ");
		nameTF = new JTextField();
		sortLabel = new JLabel("Sort: ");
		sortCombobox = actorFunction.getSortList();
		startLevel = new JLabel("Start Level:");
		endLevel = new JLabel("End Level:");
		startLevelSpinner = new JSpinner(spinnerModel);
		spinnerModel = new SpinnerNumberModel(1,0,99,1);
		endLevelSpinner = new JSpinner(spinnerModel);
		charGrafikLabel = new JLabel("Char Grafik: ");
		faceGrafikLabel = new JLabel("Face Grafik: ");
		listPanel = new JPanel();
		scrollPane = new JScrollPane(mainList);
		namePanel = new JPanel();
		grafikPanel = new JPanel();
		attributePanel = new JPanel();
		weaponPanel = new JPanel();
		centerPanel = new JPanel();
		save = new JButton("Save");
		accept = new JButton("Accept");
		cancel = new JButton("Cancel");

		//JLabel font
		mainTitle.setFont(new Font("Verdana",Font.CENTER_BASELINE,18));
		secondTitle.setFont(new Font("Verdana",Font.CENTER_BASELINE,14));

		//Components Aditionalsettings
		listPanel.setLayout(new BorderLayout());
		namePanel.setLayout(null);
		grafikPanel.setLayout(null);
		mainTitle.setHorizontalAlignment(mainTitle.CENTER);
		centerPanel.setLayout(null);
		namePanel.setBorder(new LineBorder(Color.lightGray, 1, true));
		grafikPanel.setBorder(new LineBorder(Color.lightGray, 1, true));
		attributePanel.setBorder(new LineBorder(Color.lightGray, 1, true));
		weaponPanel.setBorder(new LineBorder(Color.lightGray, 1, true));
		namePanel.setBounds(10,18,200,180);
		nameLabel.setBounds(10, 0, 170, 30);
		nameTF.setBounds(10, 25, 170, 30);
		sortLabel.setBounds(10,50,170,30);
		sortCombobox.setBounds(10,75,170,30);
		startLevel.setBounds(10, 100, 100, 30);
		endLevel.setBounds(107, 100, 100, 30);
		startLevelSpinner.setBounds(3, 125, 100, 30);
		endLevelSpinner.setBounds(100, 125, 100, 30);
		grafikPanel.setBounds(10,210,200,230);
		charGrafikLabel.setBounds(10, 10, 100, 30);


		//Component Adds
		listPanel.add(secondTitle,BorderLayout.NORTH);
		listPanel.add(scrollPane,BorderLayout.CENTER);

		centerPanel.add(namePanel);
		centerPanel.add(grafikPanel);
		centerPanel.add(attributePanel);
		centerPanel.add(weaponPanel);


		namePanel.add(nameLabel);
		namePanel.add(nameTF);
		namePanel.add(sortLabel);
		namePanel.add(sortCombobox);
		namePanel.add(startLevel);
		namePanel.add(endLevel);
		namePanel.add(startLevelSpinner);
		namePanel.add(endLevelSpinner);

		grafikPanel.add(charGrafikLabel);


		//MainPanel Adds
		this.add(listPanel,BorderLayout.WEST);
		this.add(centerPanel,BorderLayout.CENTER);
		this.add(mainTitle,BorderLayout.NORTH);


	}

	public ActorDatabaseGUI() {
		initComponents();

	}
	public static void main(String[] args) {
		JFrame t = new JFrame("a");
		t.setSize(600, 600);
		t.setDefaultCloseOperation(t.EXIT_ON_CLOSE);
		t.add(new ActorDatabaseGUI());
		t.setVisible(true);

	}

}
