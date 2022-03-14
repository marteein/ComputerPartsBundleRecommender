import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.Component;
import java.awt.Insets;

public class Main {

	public static JFrame frmRecommendationFinder;
	public static List<Set<String>> itemsetList = new ArrayList<>();
	public static Queue<String> productsSelected = new LinkedList<>();
	public static Stack<String> productsSelected1 = new Stack<>();
	public static ArrayList<String> bundle = new ArrayList<>();
	public static String bundleArr[] = new String[bundle.size()];
	

	public static void main(String[] args) throws Exception {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmRecommendationFinder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    public static ArrayList<String> get() throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM solditems ORDER BY id ASC LIMIT 1");
			ResultSet result = statement.executeQuery();
			
			ArrayList<String> array = new ArrayList<String>();
			while(result.next()){
				System.out.print(result.getString("first"));
				System.out.print(", ");
				System.out.print(result.getString("second"));
				System.out.print(", ");
				System.out.print(result.getString("third"));
				System.out.print(", ");
				System.out.print(result.getString("fourth"));
				System.out.print(", ");
				System.out.print(result.getString("fifth"));
				System.out.print(", ");
				System.out.print(result.getString("sixth"));
				System.out.print(", ");
				System.out.print(result.getString("seventh"));
				System.out.println(" ");
				
				array.add(result.getString("seventh"));
			}
			System.out.println("All records have been selected");
			return array;
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	public static void pre() throws Exception{
		String buy = new String();
		buy = String.join(",", productsSelected1);
		if(!buy.isEmpty()){
			try{
				Connection con = getConnection();
				PreparedStatement posted = con.prepareStatement("INSERT INTO solditems(purchased) VALUES('"+buy+"')");
				posted.executeUpdate();
			}
			catch(Exception e){
				System.out.println("");
			}
			finally{
				JOptionPane.showMessageDialog(frmRecommendationFinder, "Data Inserted in Databse");
			}
		}
		else{
			
			}
	}
	
	public static void mid() throws Exception{
		AprioriFrequentItemsetGenerator<String> generator = new AprioriFrequentItemsetGenerator<>();
        
        Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("SELECT * FROM solditems");
		PreparedStatement statement2 = con.prepareStatement("SELECT * FROM items");
		ResultSet result = statement.executeQuery();
		ResultSet result2 = statement2.executeQuery();
		
		while(result2.next()){
			System.out.println("["+result2.getInt("id")+"] "+result2.getString("products"));
		}
		
		
		PreparedStatement statement3 = con.prepareStatement("SELECT * FROM solditems ORDER BY id DESC LIMIT 1");
		ResultSet result3 = statement3.executeQuery();
		while(result.next()){
			String purchasedAll[] = result.getString("purchased").split(",");
			//********SORT for prelim
			SortOperations.bubbleSortString(purchasedAll);
			itemsetList.add(new HashSet<>(Arrays.asList(purchasedAll)));
			productsSelected = new LinkedList<>(Arrays.asList(purchasedAll));
        }
	}
	public static void post() throws Exception{
		
		
		AprioriFrequentItemsetGenerator<String> generator = new AprioriFrequentItemsetGenerator<>();
        
        Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("SELECT * FROM solditems");
		PreparedStatement statement2 = con.prepareStatement("SELECT * FROM items");
		ResultSet result = statement.executeQuery();
		ResultSet result2 = statement2.executeQuery();
		
		while(result2.next()){
			System.out.println("["+result2.getInt("id")+"] "+result2.getString("products"));
		}
		
		
		PreparedStatement statement3 = con.prepareStatement("SELECT * FROM solditems ORDER BY id DESC LIMIT 1");
		ResultSet result3 = statement3.executeQuery();
		while(result.next()){
			String purchasedAll[] = result.getString("purchased").split(",");
			itemsetList.add(new HashSet<>(Arrays.asList(purchasedAll)));
			productsSelected = new LinkedList<>(Arrays.asList(purchasedAll));
        }
		

        FrequentItemsetData<String> data = generator.generate(itemsetList, 0.2);
        int i = 1;

        
        
        String compare[] = new String[itemsetList.size()];;
        Object[] compare2 = new String[itemsetList.size()];
        if(result3.first()){
        	compare = result3.getString("purchased").split(",");
        }
        bundle = new ArrayList<>();
        for (Set<String> itemset : data.getFrequentItemsetList()) {
        	for(int count = 0;count<compare.length;count++){
        		if(itemset.contains(compare[count])&&itemset.size()>1){
        			bundle.add(compare[count]+": "+itemset);
        		}
        	}
        	System.out.printf("%2d: %9s, support: %1.1f\n", i++, itemset, data.getSupport(itemset)); 
        }
        System.out.println("");
        bundleArr = new String[bundle.size()];
        for(int a = 0;a<bundle.size();a++){
        	bundleArr[a] = bundle.get(a);
        }
        }
	
    public static void createTable() throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS solditems(id int NOT NULL AUTO_INCREMENT, first varchar(255), "
					+ "second varchar(255), third varchar(255), fourth varchar(255), fifth varchar(255), sixth varchar(255), seventh varchar(255), PRIMARY KEY(id))");
			create.executeUpdate();
		}
		catch(Exception e){
			System.out.println(e);
		}
		finally{
			System.out.println("Table Created, Function Complete");
		}
		
		
	}
	
	public static Connection getConnection() throws Exception{
		try{
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/compshop?useTimezone=true&serverTimezone=UTC";
			String username = "root";
			String password = "";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return con;
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		
		return null;
	}
	
	public Main() {
		initialize();
	}
	
	private void initialize() {
		frmRecommendationFinder = new JFrame();
		frmRecommendationFinder.setTitle("Recommendation Finder");
		frmRecommendationFinder.getContentPane().setBackground(SystemColor.menu);
		frmRecommendationFinder.setSize(new Dimension(1200, 680));
		frmRecommendationFinder.getContentPane().setLayout(null);
		frmRecommendationFinder.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		frmRecommendationFinder.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JTextArea selectedProductsArea = new JTextArea();
		selectedProductsArea.setEditable(false);
		selectedProductsArea.setBounds(233, 69, 289, 293);
		frmRecommendationFinder.getContentPane().add(selectedProductsArea);
		
		JTextArea recommedationArea = new JTextArea();
		recommedationArea.setWrapStyleWord(true);
		recommedationArea.setLineWrap(true);
		recommedationArea.setEditable(false);
		recommedationArea.setBounds(537, 69, 626, 447);
		frmRecommendationFinder.getContentPane().add(recommedationArea);
		
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblProducts.setBounds(26, 16, 120, 44);
		frmRecommendationFinder.getContentPane().add(lblProducts);
		
		JButton btnMotherboard = new JButton("Motherboard");
		btnMotherboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "motherboards";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnMotherboard.setBounds(15, 69, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnMotherboard);
		
		JButton btnProcessor = new JButton("Processor");
		btnProcessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "processors";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnProcessor.setBounds(15, 143, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnProcessor);
		
		JButton btnRam = new JButton("Video Card");
		btnRam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "video cards";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnRam.setBounds(15, 181, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnRam);
		
		JButton btnPowerSupplyUnit = new JButton("PSU");
		btnPowerSupplyUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "power supply unit";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnPowerSupplyUnit.setBounds(15, 220, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnPowerSupplyUnit);
		
		JButton btnSoundCard = new JButton("Sound Card");
		btnSoundCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "sound cards";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnSoundCard.setBounds(15, 373, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnSoundCard);
		
		JButton btnComputerCase = new JButton("Computer Case");
		btnComputerCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "computer cases";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnComputerCase.setBounds(15, 257, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnComputerCase);
		
		JButton btnHardDrive = new JButton("Hard Drive");
		btnHardDrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "hard drives";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnHardDrive.setBounds(15, 296, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnHardDrive);
		
		JButton btnNetworkAdapters = new JButton("Network Adapters");
		btnNetworkAdapters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "netword adapters";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnNetworkAdapters.setBounds(15, 334, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnNetworkAdapters);
		
		JButton btnOpticalDrive = new JButton("Optical Drives");
		btnOpticalDrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "optical drives";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnOpticalDrive.setBounds(15, 410, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnOpticalDrive);
		
		JButton btnMonitor = new JButton("Monitor");
		btnMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "monitor";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnMonitor.setBounds(15, 449, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnMonitor);
		
		JButton btnOs = new JButton("OS");
		btnOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "operating system";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnOs.setBounds(15, 487, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnOs);
		
		JButton btnMouse = new JButton("Mouse");
		btnMouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "mouse";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnMouse.setBounds(15, 526, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnMouse);
		
		JButton btnKeyboard = new JButton("Keyboard");
		btnKeyboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "keyboard";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnKeyboard.setBounds(15, 563, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnKeyboard);
		
		JButton btnWires = new JButton("Wires");
		btnWires.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "wires";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnWires.setBounds(15, 602, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnWires);
		
		JLabel lblSelectedProducts = new JLabel("Customer's Cart");
		lblSelectedProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedProducts.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblSelectedProducts.setBounds(263, 16, 227, 44);
		frmRecommendationFinder.getContentPane().add(lblSelectedProducts);
		
		JToggleButton storeDB = new JToggleButton("Store in Database");
		storeDB.setBounds(283, 410, 182, 29);
		frmRecommendationFinder.getContentPane().add(storeDB);
		
		JToggleButton getRec = new JToggleButton("Get Recomendations");
		getRec.setBounds(283, 448, 182, 29);
		frmRecommendationFinder.getContentPane().add(getRec);
		
		JButton btnNewButton = new JButton("Get latest");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mid();
				} catch (Exception evt) {}
				while(!productsSelected.isEmpty()){
					String text = productsSelected.poll();
					selectedProductsArea.append(text+"\n");
				}
				
			}
		});
		btnNewButton.setBounds(318, 371, 115, 29);
		frmRecommendationFinder.getContentPane().add(btnNewButton);
		
		JButton btnProcess = new JButton("PROCESS");
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedProductsArea.setText("");
				
				if(storeDB.isSelected() && getRec.isSelected()){
					try {
						pre();
						post();
						//-----MIDTERM SORT------//
						SortOperations.selectionSortString(bundleArr);
					} catch (Exception evt) {
						System.out.println("Error in data");
					}
					for(int i=0;i<bundleArr.length;i++){
						recommedationArea.append(bundleArr[i]+"\n\n");
					}
					SortOperations.printArrayString(bundleArr);
				}
				else if(storeDB.isSelected()){
					try{pre();}
					catch(Exception evt){}
				}
				else if(getRec.isSelected()){
					try{
						post();
						//-----MIDTERM SORT------//
						SortOperations.selectionSortString(bundleArr);
					}
					catch(Exception evt){}
					
					for(int i=0;i<bundleArr.length;i++){
						recommedationArea.append(bundleArr[i]+"\n\n");
					}
				}
				else{
					JOptionPane.showMessageDialog(frmRecommendationFinder, "Please select if you want to Store in Database or Get Recommendation or both");
				}
			}
		});
		btnProcess.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnProcess.setBounds(283, 505, 182, 59);
		frmRecommendationFinder.getContentPane().add(btnProcess);
		
		JLabel lblRecommendations = new JLabel("Bundles to offer");
		lblRecommendations.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecommendations.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblRecommendations.setBounds(656, 16, 380, 44);
		frmRecommendationFinder.getContentPane().add(lblRecommendations);
		
		JButton btnRefresh = new JButton("Reset");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productsSelected1 = new Stack<>();
				selectedProductsArea.setText("");
				recommedationArea.setText("");
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnRefresh.setBounds(788, 532, 138, 59);
		frmRecommendationFinder.getContentPane().add(btnRefresh);
		
		JButton btnRam_1 = new JButton("RAM");
		btnRam_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "RAM";
				productsSelected1.add(text);
				selectedProductsArea.append(text+"\n");
			}
		});
		btnRam_1.setBounds(15, 108, 143, 29);
		frmRecommendationFinder.getContentPane().add(btnRam_1);
		
		
	}
}