package cn.edu.bupt.sdmda.cls12;

import java.awt.Dimension;
import java.text.*;
import java.util.Date;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private JButton btnOpen;
	private JButton btnPlay1;
	private JTextField tfPath;
	private JButton btnProc;
	private JButton btnPlay2;
	private JButton btnResample;
	private JButton btnPlay3;
	private JTextField tfPara;
	private JTextField tfPara2;
	private JLabel lblTips;
	private JLabel lblTips2;
	private JButton btnFilter;
	private JButton btnPlay4;
	private JTextField tfPara3;
	private JTextArea procdiary;
	private JTextField tfPara4;
	private JLabel lblTips3;
	private JButton btnSave;
	private JButton btnSave2;
	private JButton btnSave3;
	Cls12 opened;
	Cls12 proced;
	Cls12 resampled;
	Cls12 filtered;
	
	public MainFrame(){
		initUI();
		initEventLister();
	}
	


	private void initEventLister() {
		// TODO Auto-generated method stub
		btnOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(btnOpen)){
					tfPath.setText(fc.getSelectedFile().getAbsolutePath());
					opened = new Cls12(fc.getSelectedFile().getAbsolutePath());
					
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[OPEN]"+"\r\n");
					
					
				}
				
			}
		});
		
		btnPlay1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null!=opened){
					opened.play();
					
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[PLAY]"+"\r\n");
				}
			}
		});
		btnSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JFileChooser fc = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION==fc.showSaveDialog(btnSave)){
					String filepath = fc.getSelectedFile().getAbsolutePath();
					proced.write(filepath);
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[SAVE]"+"\r\n");
				}
				
			}
			
		});
		
		btnProc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String v = tfPara.getText();
				float p;
				try{
				    p = Float.parseFloat(v);
				}catch(NumberFormatException ee){
					p = 1.0f;
				}
				proced = new Cls12(opened.ola(p),opened.getChannel(),opened.getBitDepth(),opened.getSampleRate());
				
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timefornow = date.format(new Date());
				procdiary.append("【"+timefornow+"】"+"     "+"[OLA PROCESSING]"+"\r\n");
			}
		});
		btnPlay2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null!=proced){
					proced.play();
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[PLAY]"+"\r\n");
				
				}
			}
		});
		
		btnResample.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String v = tfPara2.getText();
				float p;
				try{
				    p = Float.parseFloat(v);
				}catch(NumberFormatException ee){
					p = 1.0f;
				}
				resampled = new Cls12(opened.resample(p),opened.getChannel(),opened.getBitDepth(),opened.getSampleRate());
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timefornow = date.format(new Date());
				procdiary.append("【"+timefornow+"】"+"     "+"[RESAMPLE PROCESSING]"+"\r\n");
			}
		});
        btnPlay3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null!=resampled){
					resampled.play();
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[PLAY]"+"\r\n");
				}
			}
		});
        
        btnSave2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JFileChooser fc = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION==fc.showSaveDialog(btnSave)){
					String filepath = fc.getSelectedFile().getAbsolutePath();
					resampled.write(filepath);
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[SAVE]"+"\r\n");
				}
				
			}
			
		});
        
        btnFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String v1 = tfPara3.getText();
				String v2 = tfPara4.getText();
				//Pattern pattern0 = Pattern.compile("[;|]+");
				//String[] strs = pattern0.split(v1);
				Pattern pattern = Pattern.compile("[,|]+");
				String[] mat1 = pattern.split(v1);
				String[] mat2 = pattern.split(v2);
				
				float[] a = new float[mat1.length];
				float[] b = new float[mat2.length];
				for(int i=0;i<mat1.length;i++)
				{
				try{
				    a[i] = Float.parseFloat(mat1[i]);
				    System.out.println(a[i]);
				}catch(NumberFormatException ee){
					a[i] = 1.0f;
				}
				}
				for(int j=0;j<mat2.length;j++)
				{
				try{
				    b[j] = Float.parseFloat(mat2[j]);
				    System.out.println(b[j]);
				}catch(NumberFormatException ee){
					b[j] = 1.0f;
				}
				}
				float[] hlp = opened.filter(b, a);
				filtered = new Cls12(hlp,opened.getChannel(),opened.getBitDepth(),opened.getSampleRate());
				
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timefornow = date.format(new Date());
				procdiary.append("【"+timefornow+"】"+"     "+"[FILTER]"+"\r\n");
			}
		});
        btnPlay4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null!=filtered){
					filtered.play();
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[PLAY]"+"\r\n");
				}
			}
		});
        
        btnSave3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JFileChooser fc = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION==fc.showSaveDialog(btnSave)){
					String filepath = fc.getSelectedFile().getAbsolutePath();
					filtered.write(filepath);
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timefornow = date.format(new Date());
					procdiary.append("【"+timefornow+"】"+"     "+"[SAVE]"+"\r\n");
				}
				
			}
			
		});
  
	}
	

	private void initUI() {
		// TODO Auto-generated method stub
		JPanel panel1 = creatFirstPanel();
		JPanel panel2 = creatSecondPanel();
		JPanel panel3 = creatThirdPanel();
		JPanel panel4 = creatForthPanel();
		JPanel panel5 = creatFifthPanel();
		JPanel panel = (JPanel)getContentPane();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		panel.add(panel5);
		setTitle("Audio Process");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	private JPanel creatFifthPanel() {
		// TODO 自动生成的方法存根
		procdiary = new JTextArea();
		procdiary.setPreferredSize(new Dimension(800,600));
		procdiary.setLineWrap(true);//自动换行
		procdiary.setWrapStyleWord(true);
	    JScrollPane JSP = new JScrollPane(procdiary);
	    JSP.setPreferredSize(new Dimension(800,200));
		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));
		JSP.setVerticalScrollBarPolicy(  
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		
		ret.add(JSP);
		//ret.setVisible(true);
		return ret;
		
	}

	private JPanel creatForthPanel() {
		// TODO 自动生成的方法存根
		btnFilter = new JButton();
		btnFilter.setText("Filter");
		btnFilter.setPreferredSize(new Dimension(200,30));
		
		btnPlay4 = new JButton();
		btnPlay4.setText("Play");
		btnPlay4.setPreferredSize(new Dimension(100,30));
		
		tfPara3 = new JTextField();
		tfPara3.setPreferredSize(new Dimension(50,30));
		
		tfPara4 = new JTextField();
		tfPara4.setPreferredSize(new Dimension(50,30));
		
		lblTips3 = new JLabel();
		lblTips3.setText("输入滤波器系数a和b，用逗号相隔");
		
		btnSave3 = new JButton();
		btnSave3.setText("Save");
		btnSave3.setPreferredSize(new Dimension(100,30));
		
		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));
		
		ret.add(btnFilter);
		ret.add(btnPlay4);
		ret.add(tfPara3);
		ret.add(tfPara4);
		ret.add(lblTips3);
		ret.add(btnSave3);
		
		return ret;
	}

	private JPanel creatThirdPanel() {
		// TODO 自动生成的方法存根
		btnResample = new JButton();
		btnResample.setText("Resample Processing");
		btnResample.setPreferredSize(new Dimension(200,30));
		
		btnPlay3 = new JButton();
		btnPlay3.setText("Play");
		btnPlay3.setPreferredSize(new Dimension(100,30));
		
		tfPara2 = new JTextField();
		tfPara2.setPreferredSize(new Dimension(100,30));
		
		lblTips2 = new JLabel();
		lblTips2.setText("输入参数rate");
		
		btnSave2 = new JButton();
		btnSave2.setText("Save");
		btnSave2.setPreferredSize(new Dimension(100,30));
		
		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));
		
		ret.add(btnResample);
		ret.add(btnPlay3);
		ret.add(tfPara2);
		ret.add(lblTips2);
		ret.add(btnSave2);
		return ret;
	}

	private JPanel creatSecondPanel() {
		// TODO Auto-generated method stub
		btnProc = new JButton();
		btnProc.setText("OLA Processing");
		btnProc.setPreferredSize(new Dimension(200,30));
		
		btnPlay2 = new JButton();
		btnPlay2.setText("Play");
		btnPlay2.setPreferredSize(new Dimension(100,30));
		
		tfPara = new JTextField();
		tfPara.setPreferredSize(new Dimension(100,30));
		
		lblTips = new JLabel();
		lblTips.setText("输入参数rate");
		
		btnSave = new JButton();
		btnSave.setText("Save");
		btnSave.setPreferredSize(new Dimension(100,30));
		
		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));
		
		ret.add(btnProc);
		ret.add(btnPlay2);
		ret.add(tfPara);
		ret.add(lblTips);
		ret.add(btnSave);
		return ret;
	}

	private JPanel creatFirstPanel() {
		// TODO Auto-generated method stub
		btnOpen = new JButton(); 
		btnOpen.setText("open");
		btnOpen.setPreferredSize(new Dimension(200,30));
		
		btnPlay1 = new JButton();
		btnPlay1.setText("Play");
		btnPlay1.setPreferredSize(new Dimension(100,30));
		
		tfPath = new JTextField();
		tfPath.setPreferredSize(new Dimension(200,30));
		tfPath.setEditable(false);
		
		btnSave = new JButton();
		btnSave.setText("Save");
		btnSave.setPreferredSize(new Dimension(100,30));
		
		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret,BoxLayout.X_AXIS));
		ret.add(btnOpen);
		ret.add(btnPlay1);
		ret.add(tfPath);
		
		
		return ret;
	}
	

}
