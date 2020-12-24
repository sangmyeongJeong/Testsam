package mypkg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mypkg.bean.Member;
import mypkg.bean.Order;
import mypkg.bean.Product;
import mypkg.shopping.ShoppingInfo;

public class MallDao extends SuperDao {
	public List<ShoppingInfo> GetShoppingInfo(String id) {
		List<ShoppingInfo> lists = new ArrayList<ShoppingInfo>() ;
		
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		
		String sql = " select * from shoppinginfos " ;
		sql += " where mid = ? " ;
		
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1, id);
			rs = pstmt.executeQuery() ;
			
			while(rs.next()){
				ShoppingInfo shop = new ShoppingInfo(); 
				
				shop.setImage(rs.getString("image"));
				shop.setMid(rs.getString("mid"));
				shop.setPname(rs.getString("pname"));
				shop.setPnum(rs.getInt("pnum"));
				shop.setPoint(rs.getInt("point"));
				shop.setPrice(rs.getInt("price"));
				shop.setQty(rs.getInt("qty"));				
				
				lists.add(shop) ;
			}			
			
		}  catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return lists;
	}
	
	public void InsertCartData(Member mem, Map<Integer, Integer> maplists) {
		// mem 객체는 로그인한 고객의 정보입니다.
		// maplists 객체는 카트에 담겨 있는 나의 쇼핑 정보입니다.
		
		Connection conn = null ;
		PreparedStatement pstmt = null ;		
		
		String sql = "" ;
		
		int cnt = -999999 ;
		
		try {
			conn = super.getConnection() ;
			conn.setAutoCommit(false);
			
			// 1. 장바구니 테이블에 남아 있는 회원의 행(row)을 모두 제거합니다.
			sql = " delete from shoppinginfos " ;
			sql += " where mid = ? " ;
			
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1, mem.getId());
			
			cnt = pstmt.executeUpdate();
			if(pstmt != null){pstmt.close();}
			
			// 2.반복문을 사용하여 테이블에 인서트 합니다.
			Set<Integer> keylist =  maplists.keySet() ;
			System.out.println("상품 개수 : " + keylist.size());
			
			for(Integer pnum : keylist) {
				sql = " insert into shoppinginfos(mid, pnum, pname, qty, price, image, point)" ;
				sql += " values(?, ?, ?, ?, ?, ?, ?)" ;
				pstmt = conn.prepareStatement(sql) ;
				
				int qty = maplists.get(pnum) ;
				
				ProductDao pdao = new ProductDao();
				Product bean = pdao.SelectDataByPk(pnum) ;
				
				pstmt.setString(1, mem.getId());
				pstmt.setInt(2, pnum);
				pstmt.setString(3, bean.getName());
				pstmt.setInt(4, qty);
				pstmt.setInt(5, bean.getPrice());
				pstmt.setString(6, bean.getImage());
				pstmt.setInt(7, bean.getPoint());
				
				cnt = pstmt.executeUpdate();
				if(pstmt != null){pstmt.close();}
			}
			
			conn.commit();
			System.out.println("장바구니 테이블에 저장 성공"); 
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
			e.printStackTrace(); 
		} finally {
			try {				
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}		

	public void Calculate(Member mem, Map<Integer, Integer> maplists, int totalPoint) {
		// mem 객체는 고객 정보이고, maplists 객체는 구매한 상품 리스트 입니다
		// totalPoint는 회원에게 적립할 마일리지 포인트 금액입니다.
		
		// 고객의 장바구니에 대한 결재를 진행합니다.
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		
		String sql = "" ;
		int cnt = -999999 ;
		int maxnum = -999999 ; // 방금 추가된 송장 번호
		
		try {
			conn = super.getConnection() ;
			conn.setAutoCommit(false);
			
			// 1. 주문(orders) 테이블에 추가합니다.
			sql = " insert into orders(oid, mid, orderdate, remark)" ;
			sql += " values(seqoid.nextval, ?, sysdate, ?) " ; 			
			pstmt = conn.prepareStatement(sql) ;			
			pstmt.setString(1, mem.getId()); // 구매자			
			pstmt.setString(2, " "); // 코멘트			
			cnt = pstmt.executeUpdate() ;
			if(pstmt !=null){pstmt.close();}
			
			// 2. 방금 추가된 송장 번호를 읽어 옵니다.
			sql = " select max(oid) as maxnum from orders " ;			
			pstmt = conn.prepareStatement(sql) ;			
			rs = pstmt.executeQuery() ;
			if(rs.next()) {
				maxnum = rs.getInt("maxnum") ; 
			}
			if(pstmt !=null){pstmt.close();}
			System.out.println("신규 송장 번호 : " + maxnum); 		
			
			Set<Integer> keylist = maplists.keySet() ;
			System.out.println("상품 개수 : " + keylist.size()); 
			
			// 반복문을 사용하여 
			for(Integer pnum : keylist){
				// 3. 주문 상세(orderdetails) 테이블에 추가합니다.
				
				// orders의 oid와 orderdetails의 oid는 동일한 값입니다.
				// 송장 번호가 참조 무결성 제약 조건에 의하여 연결이 되어 있습니다.
				
				sql = " insert into orderdetails(odid, oid, pnum, qty, remark)" ;
				sql += " values(seqodid.nextval, ?, ?, ?, ?) " ;				
				pstmt = conn.prepareStatement(sql) ;				
				int qty = maplists.get(pnum) ;				
				pstmt.setInt(1, maxnum); // 신규로 생성된 송장 번호 
				pstmt.setInt(2, pnum); // 해당 상품 번호
				pstmt.setInt(3, qty); // 구매한 수량
				pstmt.setString(4, " "); // 비고
				
				cnt = pstmt.executeUpdate() ;
				if(pstmt !=null){pstmt.close();}
				
				// 4. 해당 상품 번호(pnum)를 이용하여 재고 수량(stock)을 감소시킵니다. 
				sql = " update products set stock = stock - ? " ;				
				sql += " where num = ? " ;				
				pstmt = conn.prepareStatement(sql) ;				
				pstmt.setInt(1, qty);				
				pstmt.setInt(2, pnum);				
				cnt = pstmt.executeUpdate() ;
				if(pstmt !=null){pstmt.close();}
			}

			// 5. 구매자에 대한 마일리지 적립 포인트를 누적시켜 줍니다.			
			sql = " update members set mpoint = mpoint + ? " ;
			sql += " where id = ? " ; 			
			pstmt = conn.prepareStatement(sql) ;			
			pstmt.setInt(1, totalPoint);			
			pstmt.setString(2, mem.getId());			
			cnt = pstmt.executeUpdate() ;
			if(pstmt !=null){pstmt.close();}
			
			conn.commit(); 
			
			System.out.println("계산 완료");
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
			e.printStackTrace(); 
		} finally {
			try {
				if(rs != null){rs.close();}
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public List<Order> OrderMall(String id) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;	
		
		String sql = "select * from orders " ;
		sql += " where mid = ? order by orderdate desc  " ;
		
		List<Order> lists = new ArrayList<Order>() ;
		
		try {
			conn = super.getConnection() ;	
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString( 1, id ); 
			rs = pstmt.executeQuery() ; 
			while ( rs.next()) {
				Order bean = new Order() ; 
				bean.setMid( rs.getString("mid"));				
				bean.setOid( rs.getInt("oid"));				
				bean.setOrderdate( String.valueOf( rs.getDate("orderdate")));
				bean.setRemark(rs.getString("remark"));		
				
				lists.add( bean ) ; 
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally{
			try {
				if( rs != null){ rs.close(); } 
				if( pstmt != null){ pstmt.close(); } 
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		} 		
		return lists  ;
	}

	public Order SelectDataByPk(int oid) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;	
		
		String sql = "select * from orders " ;  
		sql += " where oid = ?" ; 
	
		Order bean = null ;
		try {
			conn = super.getConnection() ;		
			pstmt = this.conn.prepareStatement(sql) ;			
			pstmt.setInt( 1, oid   ); 
			rs = pstmt.executeQuery() ;			
			if ( rs.next() ) {
				bean = new Order() ; 
				bean.setMid( rs.getString("mid") );
				bean.setOid( rs.getInt("oid") );
				bean.setOrderdate( String.valueOf( rs.getDate("orderdate") )); 	
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally{
			try {
				if( rs != null){ rs.close(); } 
				if( pstmt != null){ pstmt.close(); } 
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		} 		
		return bean  ;
	}



}