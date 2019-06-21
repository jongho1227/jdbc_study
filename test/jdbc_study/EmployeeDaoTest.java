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


@FixMethodOrder(MethodSorters.NAME_ASCENDING) //실행순서 고정
public class EmployeeDaoTest {
	static final Logger log = LogManager.getLogger();
	static EmployeeDao dao;
	
	@BeforeClass // 테스트하기 전 제일 먼저 호출
	public static void setUpBeforeClass() throws Exception {
		log.trace("setUpBeforeClass()");
		dao = new EmployeeDaoImpl();
	}
	
	private byte[] getImage(String fileName) {
		byte[] pic = null;
							//현재 실행되는 파일경로                            파일구부자(윈도우의경우\ 리눅스는/
		String imgPath = System.getProperty("user.dir") + System.getProperty("file.separator")+"images"+System.getProperty("file.separator")+fileName; // 불러올 이미지경로
		
		File imgFile = new File(imgPath); // 위 경로의 이미지를 파일로 저장.
		
		try(InputStream is = new FileInputStream(imgFile);){ // 파일의 내용을 읽어냄.
			
			//파일의 크기를 구함
			pic = new byte[is.available()];
			is.read(pic); //is의 내용을 pic에 읽음
			
			
		} catch (FileNotFoundException e) {
			System.out.println("해당 파일을 찾을 수 없음");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}
	
	private File getPicFile(Employee e) throws FileNotFoundException, IOException {
		File file = null;
		
		file = new File(System.getProperty("user.dir")+"\\pics\\"+e.getEmpName()+".jpg"); //파일 객체 생성
		
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(e.getPic()); // fos가 file에 e의 이미지를 넣음.
		}
		
		return file;
	}

	@Test
	public void test01DeleteEmployee() throws SQLException { //삭제 테스트
		
		Employee newEmp = new Employee(1004);
		int res = dao.deleteEmployee(newEmp);
		Assert.assertEquals(1, res);
	}
	
	
	@Test // 테스트
	public void test02InsertEmployee() throws SQLException { // 삽입 테스트
		log.trace("testInsertEmployee()");
		Employee newEmp = new Employee(1004, "연우", "사원", new Employee(1003), 1500000, new Department(1), getImage("img2.jpg"));
		
		int res = dao.insertEmployee(newEmp); // 인서트에 성공하면 1(들어간행의수)이 리턴됨. 디비에서 한명 추가하면 1행이 추가되니까.
		
		log.trace("res = " + res);
		Assert.assertEquals(1, res);
	}
	
	
	@Test
	public void test03UpdateEmployee() throws SQLException { // 수정 테스트
		Employee newEmp = new Employee(1004, "연우", "대리", new Employee(1007), 2000000, new Department(1), getImage("img5.jpg"));
		
		int res = dao.updateEmployee(newEmp);
		Assert.assertEquals(1, res);
	}
	
	
	@Test
	public void test04selectEmployeeByNo() throws SQLException { // 번호로 검색
		Employee newEmp = new Employee(1004);
		
		Employee res = dao.selectEmployeeByNo(newEmp);
		Assert.assertNotEquals(null, res);
		
		log.trace(res);
	}
	
	
	@Test
	public void test05SelectEmployeeByAll() throws SQLException, FileNotFoundException, IOException { // 모든 사원 검색
		log.trace("testSelectEmployeeByAll()");
		List<Employee> list = dao.selectEmployeeByAll();
		Assert.assertNotEquals(0, list.size()); // 테스트는 여기까지하면 끝
		
		log.trace(list);
		
		//디비의 이미지파일을 불러옴
		File imgFile = null;
		for(Employee e : list) {
			if(e.getPic()!=null) {// 이미지가 있다면
				imgFile = getPicFile(e);
				log.trace(imgFile.getAbsolutePath());
			}
		}
		
	}
	
	
	

}

/*
 * @AfterClass // 테스트가 다 끝나면 호출
 *  public static void tearDownAfterClass() throws
 * Exception { log.trace("tearDownAfterClass()"); }
 * 
 * @Before // 테스트가 호출되기전에 호출 
 * public void setUp() throws Exception {
 * log.trace("setUp()"); }
 * 
 * @After// 테스트가 끝나면 호출됨 
 * public void tearDown() throws Exception {
 * log.trace("tearDown()");
 }*/