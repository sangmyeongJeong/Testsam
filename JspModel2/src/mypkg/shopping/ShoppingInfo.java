package mypkg.shopping;

public class ShoppingInfo {
	
	// mid 컬럼은 ShoppingInfos 테이블에 저장할 때만 사용하도록 합니다.
	
	private String mid ; // 구매자 아이디
	
	
	private int pnum ; // 상품 번호
	private String pname ; // 상품 이름
	private int qty ; // 구매 수량
	private int price ; // 단가
	private String image; // 상품 이미지 이름
	private int point ; // 적립할 포인트
	//private int amount ;
		
	public ShoppingInfo() {
	
	}

	// toString() 메소드 구현
	@Override
	public String toString() {
		return "ShoppingInfo [mid=" + mid + ", pnum=" + pnum + ", pname=" + pname + ", qty=" + qty + ", price=" + price
				+ ", image=" + image + ", point=" + point + "]";
	}
	
	// getter, setter() 메소드 구현 	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	
	public int getPnum() {
		return pnum;
	}
	
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}	
}