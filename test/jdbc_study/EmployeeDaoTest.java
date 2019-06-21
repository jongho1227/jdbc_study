package jdbc_study;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_study.dao.EmployeeDao;
import jdbc_study.daoimpl.EmployeeDaoImpl;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;


@FixMethodOrder(MethodSorters.NAME_ASCENDING) //������� ����
public class EmployeeDaoTest {
	static final Logger log = LogManager.getLogger();
	static EmployeeDao dao;
	
	@BeforeClass // �׽�Ʈ�ϱ� �� ���� ���� ȣ��
	public static void setUpBeforeClass() throws Exception {
		log.trace("setUpBeforeClass()");
		dao = new EmployeeDaoImpl();
	}
	
	private byte[] getImage(String fileName) {
		byte[] pic = null;
							//���� ����Ǵ� ���ϰ��                            ���ϱ�����(�������ǰ��\ ��������/
		String imgPath = System.getProperty("user.dir") + System.getProperty("file.separator")+"images"+System.getProperty("file.separator")+fileName; // �ҷ��� �̹������
		
		File imgFile = new File(imgPath); // �� ����� �̹����� ���Ϸ� ����.
		
		try(InputStream is = new FileInputStream(imgFile);){ // ������ ������ �о.
			
			//������ ũ�⸦ ����
			pic = new byte[is.available()];
			is.read(pic); //is�� ������ pic�� ����
			
			
		} catch (FileNotFoundException e) {
			System.out.println("�ش� ������ ã�� �� ����");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}
	
	private File getPicFile(Employee e) throws FileNotFoundException, IOException {
		File file = null;
		
		file = new File(System.getProperty("user.dir")+"\\pics\\"+e.getEmpName()+".jpg"); //���� ��ü ����
		
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(e.getPic()); // fos�� file�� e�� �̹����� ����.
		}
		
		return file;
	}

	@Test
	public void test01DeleteEmployee() throws SQLException { //���� �׽�Ʈ
		
		Employee newEmp = new Employee(1004);
		int res = dao.deleteEmployee(newEmp);
		Assert.assertEquals(1, res);
	}
	
	
	@Test // �׽�Ʈ
	public void test02InsertEmployee() throws SQLException { // ���� �׽�Ʈ
		log.trace("testInsertEmployee()");
		Employee newEmp = new Employee(1004, "����", "���", new Employee(1003), 1500000, new Department(1), getImage("img2.jpg"));
		
		int res = dao.insertEmployee(newEmp); // �μ�Ʈ�� �����ϸ� 1(�����Ǽ�)�� ���ϵ�. ��񿡼� �Ѹ� �߰��ϸ� 1���� �߰��Ǵϱ�.
		
		log.trace("res = " + res);
		Assert.assertEquals(1, res);
	}
	
	
	@Test
	public void test03UpdateEmployee() throws SQLException { // ���� �׽�Ʈ
		Employee newEmp = new Employee(1004, "����", "�븮", new Employee(1007), 2000000, new Department(1), getImage("img5.jpg"));
		
		int res = dao.updateEmployee(newEmp);
		Assert.assertEquals(1, res);
	}
	
	
	@Test
	public void test04selectEmployeeByNo() throws SQLException { // ��ȣ�� �˻�
		Employee newEmp = new Employee(1004);
		
		Employee res = dao.selectEmployeeByNo(newEmp);
		Assert.assertNotEquals(null, res);
		
		log.trace(res);
	}
	
	
	@Test
	public void test05SelectEmployeeByAll() throws SQLException, FileNotFoundException, IOException { // ��� ��� �˻�
		log.trace("testSelectEmployeeByAll()");
		List<Employee> list = dao.selectEmployeeByAll();
		Assert.assertNotEquals(0, list.size()); // �׽�Ʈ�� ��������ϸ� ��
		
		log.trace(list);
		
		//����� �̹��������� �ҷ���
		File imgFile = null;
		for(Employee e : list) {
			if(e.getPic()!=null) {// �̹����� �ִٸ�
				imgFile = getPicFile(e);
				log.trace(imgFile.getAbsolutePath());
			}
		}
		
	}
	
	
	

}

/*
 * @AfterClass // �׽�Ʈ�� �� ������ ȣ��
 *  public static void tearDownAfterClass() throws
 * Exception { log.trace("tearDownAfterClass()"); }
 * 
 * @Before // �׽�Ʈ�� ȣ��Ǳ����� ȣ�� 
 * public void setUp() throws Exception {
 * log.trace("setUp()"); }
 * 
 * @After// �׽�Ʈ�� ������ ȣ��� 
 * public void tearDown() throws Exception {
 * log.trace("tearDown()");
 }*/