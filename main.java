package cn.edu.bupt.sdmda.cls12;

import java.awt.EventQueue;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Cls12 cls12 = new Cls12("cbg.aiff");
//		Cls12 olaed = new Cls12(cls12.ola(0.5f),cls12.getChannel(),cls12.getBitDepth(),cls12.getSampleRate());
//		olaed.write("olaed.aiff");
//		olaed.play();
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MainFrame mf = new MainFrame();
				mf.setVisible(true);
			}
		});



	}

}
