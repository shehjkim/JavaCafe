package common;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/getMemberByDept")
public class GetEmpByDept extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> list;
       

    public GetEmpByDept() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		EmpDAO dao = new EmpDAO();
		Map<String, Integer> map = dao.getMemberByDept();		//String,int형태의 값
		Set<String> keySet = map.keySet();
		int cnt = 1;
		
		String json ="[";
//		json = "[{\"Administration\",1}, {\"Accounting\" ,2}]";
		for(String key : keySet) {
			System.out.println(key);
			json +="{";
			json +="\"" + key + "\":";
			json += map.get(key);
			json +="}";
			if(map.size() != cnt ++) 
				json += ",";
			
		}
		json +="]";
		response.getWriter().append(json);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
