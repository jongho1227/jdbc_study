package jdbc_study;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_study.dao.DepartmentDao;
import jdbc_study.dao.DeptEmpTransactionDao;
import jdbc_study.dao.EmployeeDao;
import jdbc_study.daoimpl.DepartmentDaoImpl;
import jdbc_study.daoimpl.DeptEmpTransactionDaoImpl;
import jdbc_study.daoimpl.EmployeeDaoImpl;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;


@FixMethodOrder(MethodSorters.NAME_ASCENDING) // ���ĺ������� ����
public class DeptEmpTransactionDaoTest {
	static final Logger log = LogManager.getLogger();
	
	static DeptEmpTransactionDao dao;
	static DepartmentDao dao1;
	static EmployeeDao dao2;
	
	@BeforeClass//�׽�Ʈ �� �� ���� ȣ��
	public static void setUpBeforeClass() throws Exception {
		System.out.println();
		log.trace("start DeptEmpTransactionDaoTest");
		dao = new DeptEmpTransactionDaoImpl();
	}

	@AfterClass//�׽�Ʈ ���� �� ȣ��
	public static void tearDownAfterClass() throws Exception {
		System.out.println();
		log.trace("stop DeptEmpTransactionDaoTest");
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		System.out.println();
	}
	
	@Test
	public void test1transactionInsertEmployeeAndDepartment() {
		log.trace("Department fail");
		Department dept = new Department(1, "����", 1);
		Employee emp = new Employee(1005, "�̴ٺ�", "����", new Employee(1007), 1500000, dept);
		
		int res = dao.trInsertEmpAndDept(emp, dept);
		log.trace("res = "+ res);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test2transactionInsertEmployeeAndDepartment() {
		log.trace("Employee fail");
		Department dept = new Department(5, "������", 1);
		Employee emp = new Employee(1004, "����", "����", new Employee(1007), 1500000, dept);
		
		int res = dao.trInsertEmpAndDept(emp, dept);
		log.trace("res = "+ res);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test3transactionInsertEmployeeAndDepartment() throws SQLException {
		
		log.trace("Success");
		Department dept = new Department(5, "������", 1);
		Employee emp = new Employee(1005, "�̴ٺ�", "����", new Employee(1007), 1500000, dept);
		
		int res = dao.trInsertEmpAndDept(emp, dept);
		log.trace("res = "+ res);
		Assert.assertEquals(2, res);
		
		dao1 = new DepartmentDaoImpl();
		dao2 = new EmployeeDaoImpl();
		dao2.deleteEmployee(emp);
		dao1.deleteDepartment(dept);
		
		
			
	
	}

}
