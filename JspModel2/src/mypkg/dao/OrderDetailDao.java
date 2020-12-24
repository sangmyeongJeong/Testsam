package mypkg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mypkg.bean.Product;

public class OrderDetailDao extends SuperDao {

	public int UpdateRemark(Product bean) {
		// 상품 번호 pnum에 해당하는 모든 행의 컬럼 remark에 상품 이름으로 수정합니다.
		String sql = " update orderdetails set ";
		sql += " remark = ? " ;
		sql += " where pnum = ? " ; 
		
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		int cnt = -999999 ;

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			pstmt.setString(1, bean.getName());
			pstmt.setInt(2, bean.getNum());	
			
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
}