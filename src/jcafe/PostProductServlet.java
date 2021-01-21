package jcafe;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/postProduct")
public class PostProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostProductServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html charset=uft-8"); // 한글깨짐 해결

		String itemNo = request.getParameter("itemNo");
		String item = request.getParameter("item");
		String category = request.getParameter("category");
		String price = request.getParameter("price");
		String link = request.getParameter("link");
		String content = request.getParameter("content");
		String likeit = request.getParameter("likeIt");
		String alt = request.getParameter("alt");
		String image = request.getParameter("image");
		// 파라미터 입력, product.html의 name값과 일치해야함

		ProductVO vo = new ProductVO();
		vo.setItemNo(itemNo);
		vo.setItem(item);
		vo.setCategory(category);
		vo.setPrice(Integer.parseInt(price));
		vo.setLink(link);
		vo.setContent(content);
		vo.setLikeIt(Integer.parseInt(likeit));
		vo.setAlt(alt);
		vo.setImage(image);
System.out.println("1 "+vo);
		ProductDAO dao = new ProductDAO();
		dao.insertProduct(vo);
//tip : int방식의 값일 경우 integer.paseInt 사용하여 입력
		
		String script = "<script>location.href='jcafe/cafeList.html'</script>";
		response.getWriter().append(script);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
