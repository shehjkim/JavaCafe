package jcafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

	private Connection conn;

	public ProductDAO() {
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
	}// ProductDAO
	
	public ProductVO getProduct(ProductVO vo) {
		String sql="SELECT*FROM product WHERE item_no=?";
		ProductVO v = null;
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, v.getItemNo());
			ResultSet rs = psmt.executeQuery();
			if(rs.next()) {
				v = new ProductVO();
				v.setItemNo(rs.getString("item_no"));
				v.setItem(rs.getString("item"));
				v.setCategory(rs.getString("category"));
				v.setPrice(rs.getInt("price"));
				v.setLink(rs.getString("link"));
				v.setContent(rs.getString("content"));
				v.setLikeIt(rs.getInt("like_it"));
				v.setAlt(rs.getString("alt"));
				v.setImage(rs.getString("image"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return v;
		
	}
	
	
	
	
	

	public List<ProductVO> getProductList() {
		String sql = "select * from product order by 1";
		List<ProductVO> list = new ArrayList<>();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				ProductVO vo = new ProductVO();
				vo.setItemNo(rs.getString("item_no"));
				vo.setItem(rs.getString("item"));
				vo.setCategory(rs.getString("category"));
				vo.setPrice(rs.getInt("price"));
				vo.setLink(rs.getString("link"));
				vo.setContent(rs.getString("content"));
				vo.setLikeIt(rs.getInt("like_it"));
				vo.setAlt(rs.getString("alt"));
				vo.setImage(rs.getString("image"));
				
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
		
	}// getProductList()

	
	public ProductVO insertProduct(ProductVO vo) {
		String sql = "insert into product values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getItemNo());
			psmt.setString(2, vo.getItem());
			psmt.setString(3, vo.getCategory());
			psmt.setInt(4, vo.getPrice());
			psmt.setString(5, vo.getLink());
			psmt.setString(6, vo.getContent());
			psmt.setInt(7, vo.getLikeIt());
			psmt.setString(8, vo.getAlt());
			psmt.setString(9, vo.getImage());
			int r = psmt.executeUpdate();
			System.out.println(r + "건이 입력되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
		
	}
}
