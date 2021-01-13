package common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "PutScheduleServlet", urlPatterns = { "/PutScheduleServlet" })
public class PutSheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public PutSheduleServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String title = request.getParameter("title");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String url = request.getParameter("url");
		
		Schedule sch = new Schedule();
		sch.setTitle(title);
		sch.setStartDate(start);
		sch.setEndDate(end);
		sch.setUrl(url);
		
		EmpDAO dao = new EmpDAO();
		dao.insertSchedule(sch);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
