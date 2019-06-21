package jdbc_study;

import java.awt.EventQueue;

import jdbc_study.ui.ErpManagementUI;

public class ErpMain {

	public static void main(String[] args) {
		System.out.println("첫 번째 수정");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErpManagementUI frame = new ErpManagementUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
