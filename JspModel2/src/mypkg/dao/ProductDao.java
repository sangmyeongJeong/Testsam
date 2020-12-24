package mypkg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mypkg.bean.Product;

public class ProductDao extends SuperDao{

	public int InsertData(Product bean) {
		// 해당 Bean 객체를 사용하여 상품을 등록합니다.
		System.out.println( this.getClass() + " : 상품을 등록합니다." ); 
		String sql = " insert into products(num, name, company, image, stock, price, category, contents, point, inputdate) " ;
		sql += " values(seqprod.nextval, ?, ?, ?, ?, ?, ?, ?, ?, to_date(?, 'yyyy/MM/dd')) " ;
		
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		
		int cnt = -999999 ;
		try {
			conn = super.getConnection() ;
			conn.setAutoCommit( false );
			pstmt = conn.prepareStatement(sql) ;
			
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getCompany());
			pstmt.setString(3, bean.getImage());
			pstmt.setInt(4, bean.getStock());
			pstmt.setInt(5, bean.getPrice());
			pstmt.setString(6, bean.getCategory());
			pstmt.setString(7, bean.getContents());
			pstmt.setInt(8, bean.getPoint());
			pstmt.setString(9, bean.getInputdate());
			
			cnt = pstmt.executeUpdate() ; 
			conn.commit(); 
		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if( pstmt != null ){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}
	
	public List<Product> SelectDataList(int beginRow, int endRow, String mode, String keyword) {
		// 페이징 처리와 필드 검색을 통한 상품 목록을 구해줍니다.
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		String sql = " select num, name, company, image, stock, price, category, contents, point, inputdate " ;
		sql += " from  " ;
		sql += " ( " ;
		sql += " select num, name, company, image, stock, price, category, contents, point, inputdate, " ;
		sql += " rank() over(order by num desc) as ranking " ;
		sql += " from products " ;
		
		if(mode.equalsIgnoreCase("all") == false) {
			sql += " where " + mode + " like '" + keyword + "'" ;	
		}
		
		sql += " )  " ;
		sql += " where ranking between ? and ? " ;
		
		List<Product> lists = new ArrayList<Product>();

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			// placeholder
			pstmt.setInt(1, beginRow) ;
			pstmt.setInt(2, endRow) ; 
			
			rs = pstmt.executeQuery() ;			
			while( rs.next() ){
				Product bean = new Product();
							
				bean.setCategory(rs.getString("category"));
				bean.setCompany(rs.getString("company"));
				bean.setContents(rs.getString("contents"));
				bean.setImage(rs.getString("image"));				
				bean.setName(rs.getString("name"));
				
				// 날짜 형식
				bean.setInputdate(String.valueOf(rs.getDate("inputdate")));
				
				// 숫자 형식
				bean.setNum(rs.getInt("num"));
				bean.setPoint(rs.getInt("point"));
				bean.setPrice(rs.getInt("price"));
				bean.setStock(rs.getInt("stock"));
				
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			int cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if(rs != null){ rs.close(); }
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		
		return lists ;
	}	
	

	public int SelectTotalCount(String mode, String keyword) {
		// 해당 검색 모드(상품명, 제조 회사, 카테고리)에 충족하는 항목들의 갯수를 구해줍니다. 
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;		
		
		String sql = " select count(*) as cnt from products " ;
		
		if(mode.equalsIgnoreCase("all") == false) {
			sql += " where " + mode + " like '" + keyword + "'" ;	
		}		
		
		int cnt = -999999 ;

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			
			// placeholder 

			rs = pstmt.executeQuery() ; 
			
			if ( rs.next() ) { 
				cnt = rs.getInt("cnt");
			}
			
		} catch (SQLException e) {			
			SQLException err = (SQLException)e ;			
			cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if( rs != null){ rs.close(); } 
				if( pstmt != null){ pstmt.close(); } 
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		} 		
		return cnt  ; 
	}


	public Product SelectDataByPk(int num) {
		Product bean = null ;
		
		String sql = "select * " ;
		sql += " from products " ; 
		sql += " where num = ? " ;
		
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			pstmt.setInt( 1, num   ); 
						
			rs = pstmt.executeQuery() ;
			
			while(rs.next()) {
				bean = new Product() ;
				
				bean.setNum( rs.getInt("num") );
				bean.setName( rs.getString("name") ); 
				bean.setCompany( rs.getString("company") );				
				bean.setImage( rs.getString("image") );		
				bean.setStock( rs.getInt("stock") );
				bean.setPrice( rs.getInt("price") );
				bean.setCategory( rs.getString("category") );
				bean.setContents( rs.getString("contents") );
				bean.setPoint( rs.getInt("point") );
				bean.setInputdate( String.valueOf( rs.getDate("inputdate"))) ;
			}
			
			System.out.println("ok");
		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				if(rs != null) {rs.close();}
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return bean  ;
	}


	public int DeleteData(int num) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		int cnt = -999999 ;

		try {
			conn = super.getConnection() ;
			
			// 해당 상품 번호에 대한 orderdetails.remark 컬럼을 수정합니다.		
			String sql = " update orderdetails set remark = ?  " ;
			sql += " where pnum = ?" ; 			
			pstmt = conn.prepareStatement(sql) ;
			
			Product bean = this.SelectDataByPk(num) ;
			
			String imsi = "상품 " + bean.getName() + "이(가) 삭제되었습니다." ;
			
			pstmt.setString(1, imsi); // 저장 하고자하는 문자열
			pstmt.setInt(2, num); // 상품 번호
			cnt = pstmt.executeUpdate() ; 
			pstmt.close();						
			
			// 해당 상품을 삭제합니다.
			sql = " delete from products  " ;
			sql += " where num = ?" ;
			
			pstmt = conn.prepareStatement(sql) ;
			
			pstmt.setInt(1, num); // 상품 번호
			cnt = pstmt.executeUpdate() ; 
			
			conn.commit(); 

		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}

	public int UpdateData(Product bean) {
		System.out.println(bean.toString() ); 

		Connection conn = null ;
		PreparedStatement pstmt = null ;

		String sql = " update products set " ;
		sql += " name=?, company=?, image=?, stock=?, price=?, " ;
		sql += " category=?, contents=?, point=?, inputdate=to_date(?, 'yyyy/MM/dd')  " ; 
		sql += " where num = ? " ;

		int cnt = -999999 ;
		try {
			conn = super.getConnection() ;
			conn.setAutoCommit(false );
			pstmt = conn.prepareStatement(sql) ;
			
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getCompany());
			pstmt.setString(3, bean.getImage());
			pstmt.setInt(4, bean.getStock());
			pstmt.setInt(5, bean.getPrice());
			pstmt.setString(6, bean.getCategory());
			pstmt.setString(7, bean.getContents());
			pstmt.setInt(8, bean.getPoint());
			pstmt.setString(9, bean.getInputdate());			
			pstmt.setInt(10, bean.getNum() );
			
			cnt = pstmt.executeUpdate() ; 
			conn.commit(); 
		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if(pstmt != null ){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}


}