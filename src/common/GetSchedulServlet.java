package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "GetScheduleServlet", urlPatterns = { "/getSchedule" })
public class GetSchedulServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetSchedulServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); //한글깨짐 해결
		response.setContentType("text/html charset=uft-8");

		
		EmpDAO dao = new EmpDAO();
		List<Schedule> list = dao.getScheduleList();
		PrintWriter out = response.getWriter();
		String json="[";
		int cnt = 1;
		for(Schedule sc : list) {
			json +="{";
			json += "\"title\":\"" + sc.getTitle() + "\"";
			json += ",\"start\":\"" + sc.getStartDate() + "\"";
			json += ",\"end\":\"" + sc.getEndDate() + "\"";
			json += ",\"url\":\"" + sc.getUrl() + "\"";			
			json +="}";
			if(list.size() != cnt++)
				json += ",";
		}
		json += "]";
		out.print(json);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
