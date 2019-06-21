package jdbc_study.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc_study.dao.EmployeeDao;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;
import jdbc_study.jdbc.MySQLjdbcUtil;

public class EmployeeDaoImpl implements EmployeeDao {
	static final Logger log = LogManager.getLogger(); // 로그찍는용도
	
	
	@Override
	public List<Employee> selectEmployeeByAll() throws SQLException {
		
		String sql = "select empno, empname, title, manager, salary, dno, pic from employee";
		
		List<Employee> lists = new ArrayList<Employee>();
		
		try(Connection conn = MySQLjdbcUtil.getConnection(); // 디비연결
				PreparedStatement pstmt = conn.prepareStatement(sql); // 쿼리문을 던지는용도?
				ResultSet rs = pstmt.executeQuery()){ //커서와 같은 기능. 순서대로 하나씩 검색한다.
				
				log.trace(pstmt); // 찍어봐야 sql문이 제대로 들어갔나 알수있다.
				
			while(rs.next()) {
				lists.add(getEmployee(rs));
			}
		}
		return lists;
	}

	
	private Employee getEmployee(ResultSet rs) throws SQLException {
		return new Employee(rs.getInt("empno"),rs.getString("empname"), rs.getString("title"), 
							new Employee(rs.getInt("manager")), rs.getInt("salary"), new Department(rs.getInt("dno")), rs.getBytes("pic"));
	}

	
	
	@Override
	public Employee selectEmployeeByNo(Employee employee) throws SQLException {
		String sql = "SELECT empno, empname, title, manager, salary, dno, pic FROM employee where empno = ?";
		
		Employee emp = null;
		try(Connection conn = MySQLjdbcUtil.getConnection(); // 디비에 연결
				PreparedStatement pstmt = conn.prepareStatement(sql)){ // 인서트문 ? 부분에 해당 값을 입력?
			
			pstmt.setInt(1, employee.getEmpNo());
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				emp = getEmployee(rs);
			}
			return emp;
		}
		
	}

	
	
	@Override
	public int insertEmployee(Employee employee) throws SQLException {
		log.trace("insertEmployee()");
		
		String sql = "insert into employee (empno, empname, title, manager, salary, dno, pic) values(?, ?, ?, ?, ?, ?, ?)";
		
		
		//try리소스문 자동 close문 호출
		
		try(Connection conn = MySQLjdbcUtil.getConnection(); // 디비에 연결
				PreparedStatement pstmt = conn.prepareStatement(sql)){ // 인서트문 ? 부분에 해당 값을 입력?
			
			pstmt.setInt(1, employee.getEmpNo());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setString(3, employee.getTitle());
			pstmt.setInt(4, employee.getManager().getEmpNo());
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getDno().getDeptNo());
			pstmt.setBytes(7,employee.getPic());
			
			log.trace(pstmt);
			
			return pstmt.executeUpdate(); //쿼리적용
		}
	
	}

	@Override
	public int deleteEmployee(Employee employee) throws SQLException {
		
		String sql = "DELETE FROM employee WHERE empno=?";
		
		try(Connection conn = MySQLjdbcUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){// 디비에 연결해서 sql에 해당하는 쿼리를 넣어라.
			pstmt.setInt(1, employee.getEmpNo());
			return pstmt.executeUpdate();
		}
		
	}

	@Override
	public int updateEmployee(Employee employee) throws SQLException {
		String sql = "UPDATE employee SET empname=?, title=?, manager=?, salary=?, dno=?, pic=? WHERE empno=?";
		
		try(Connection conn = MySQLjdbcUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){ // 디비에 연결해서 sql에 해당하는 쿼리를 넣어라.
			pstmt.setString(1, employee.getEmpName());
			pstmt.setString(2, employee.getTitle());
			pstmt.setInt(3, employee.getManager().getEmpNo());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setInt(5, employee.getDno().getDeptNo());
			pstmt.setBytes(6, employee.getPic());
			pstmt.setInt(7, employee.getEmpNo());
			
			return pstmt.executeUpdate();
		}
		
		
	}

}
