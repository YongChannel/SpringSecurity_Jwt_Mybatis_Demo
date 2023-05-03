package kr.co.strato.demosite.common.model.dto;

public class GridSearchParam<T> extends SearchParam<T> {
    public static final String  intMaxRowsPerPage   = "2147483647";   //Integer.MAX_VALUE
    public static final int     initNumRowsPerPage  = 10;

    private int     rowsPerPage = initNumRowsPerPage;   //페이지 당 레코드 수
    private int     pageNo      = 1;                    //현재 페이지 번호
    private String  orderType;                          //정렬을 위한 필드명(id, name)
    private String  orderValue;                         //정렬을 위한 숫서값(ASC, DESC)

    public int getRowsPerPage() {
        return (rowsPerPage == 0) ? initNumRowsPerPage : rowsPerPage;
    }
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = (rowsPerPage == 0) ? initNumRowsPerPage : rowsPerPage;
    }
    public int getPageNo() {
        return (pageNo == 0) ? 1 : pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = (pageNo == 0) ? 1 : pageNo;
    }
    public int getBeginIndex() {
        return (pageNo - 1) * rowsPerPage;
    }
    public int getEndIndex() {
        return pageNo * rowsPerPage;
    }
    public int getEndIndex(int totalRow) {
        return totalRow - (pageNo - 1) * rowsPerPage;
    }
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public String getOrderValue() {
        return orderValue;
    }
    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }
}
