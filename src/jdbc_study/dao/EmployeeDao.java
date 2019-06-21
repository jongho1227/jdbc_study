package jdbc_study.dao;
/*
 * dao :  데이터베이스의 데이터에 접근하기 위한 객체.
 * 			데이터조작 메서드들
 * 
 * */
import java.sql.SQLException;
import java.util.List;

import jdbc_study.dto.Employee;

public interface EmployeeDao {
	List<Employee> selectEmployeeByAll() throws SQLException;
	Employee selectEmployeeByNo(Employee employee) throws SQLException;
	int insertEmployee(Employee employee) throws SQLException ;
	int deleteEmployee(Employee employee) throws SQLException;
	int updateEmployee(Employee employee) throws SQLException;
}
