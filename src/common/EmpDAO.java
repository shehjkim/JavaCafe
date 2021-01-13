package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpDAO {
	Connection conn = null;
	private PreparedStatement psmt;

	public EmpDAO() {
		try {
			String user = "hr";
			String pw = "hr";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

			System.out.println("Database에 연결되었습니다.\n");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
	}// end of 생성자
	
	
	//스케줄 입력
	public Schedule insertSchedule(Schedule sch) {
		String sql = "insert into calendar values(?,?,?,?)";
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, sch.getTitle());
			psmt.setString(2, sch.getStartDate());
			psmt.setString(3, sch.getEndDate());
			psmt.setString(4, sch.getUrl());
			int r = psmt.executeUpdate();
			System.out.println(r + "건이 입력되었씁니다.");
					
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sch;
	
	}

	//스케줄 목록
	public List<Schedule> getScheduleList() {
		String sql= "SELECT * FROM calendar";
		List<Schedule> list = new ArrayList<>();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				Schedule schedule = new Schedule();
				schedule.setTitle(rs.getString("title"));
				schedule.setStartDate(rs.getString("start_date"));
				schedule.setEndDate(rs.getString("end_date"));
				schedule.setUrl(rs.getString("url"));
				
				list.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
			
		}
	
		
//		
	public Map<String, Integer> getMemberByDept() {
		String sql = "select department_name, count(*) from employees e, departments d "
				+ "where e.department_id =d.department_id " + "group by department_name";
		Map<String, Integer> map = new HashMap<>();	
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery(); 		// 쿼리결과값을 rs에 담겠다
			while (rs.next()) { 						// 데이터 있는 건수만큼 반복
				
				map.put(rs.getString(1), rs.getInt(2)); // 1:첫번째컬럼 2:두번째컬럼
			
			} 											// ex> 1:Administration 2: 1

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public EmployeeVO insertEmp(EmployeeVO vo) {
		String sql1 = "SELECT employees_seq.nextval from dual";
		String sql2 = "SELECT * FROM emp_temp where employee_id=?";
		String sql = "insert into emp_temp(employee_id,first_name,last_name,email,hire_date,job_id,phone_number) values(?,?,?,?,sysdate,?,?)";
		int r = 0;
		String newSeq = null;
		EmployeeVO newVo = new EmployeeVO();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql1);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				newSeq = rs.getString(1);
			}
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, newSeq);
			psmt.setString(2, vo.getFirstName());
			psmt.setString(3, vo.getLastName());
			psmt.setString(4, vo.getEmail());
			psmt.setString(5, vo.getJobID());
			psmt.setString(6, vo.getPhoneNumber());
			r = psmt.executeUpdate();
			System.out.println(r + "건 입력 되었습니다.");

			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, newSeq);
			rs = psmt.executeQuery();
			if (rs.next()) {
				newVo.setEmail(rs.getString("email"));
				newVo.setEmployeeId(rs.getInt("employee_id"));
				newVo.setFirstName(rs.getString("first_Name"));
				newVo.setLastName(rs.getString("last_Name"));
				newVo.setJobID(rs.getString("job_ID"));
				newVo.setHireDate(rs.getString("hire_Date"));
				newVo.setPhoneNumber(rs.getString("phone_Number"));
				newVo.setSalary(rs.getInt("salary"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return newVo;

	}

//삭제
	public boolean deleteEmp(EmployeeVO vo) {
		String sql = "delete from emp_temp where employee_id = ?";
		int r = 0;
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, vo.getEmployeeId());
			r = psmt.executeUpdate();
			System.out.println(r + "건이 삭제되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r == 1 ? true : false;

	}

//수정	
	public EmployeeVO updateEmp(EmployeeVO vo) {
		String sql = "update emp_temp set first_name=?,last_name=?,email=?,hire_date=?,job_id=?,phone_number=? where employee_id=?";
		try {
			psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			psmt.setString(1, vo.getFirstName());
			psmt.setString(2, vo.getLastName());
			psmt.setString(3, vo.getEmail());
			psmt.setString(4, vo.getJobID());
			psmt.setString(5, vo.getPhoneNumber());
			int r = psmt.executeUpdate();
			System.out.println(r + "건이 수정되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

//목록	
	public List<EmployeeVO> getEmpList() {
		String sql = "select * from emp_temp";
		List<EmployeeVO> list = new ArrayList<>();

		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				EmployeeVO vo = new EmployeeVO();
				vo.setEmployeeId(rs.getInt("employee_id"));
				vo.setFirstName(rs.getString("first_name"));
				vo.setLastName(rs.getString("Last_name"));
				vo.setEmail(rs.getString("email"));
				vo.setPhoneNumber(rs.getString("phone_number"));
				vo.setHireDate(rs.getString("hire_date"));
				vo.setJobID(rs.getString("job_id"));
				vo.setSalary(rs.getInt("salary"));
				rs.getInt("employee_id");

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}// end of getEmpList()
}
