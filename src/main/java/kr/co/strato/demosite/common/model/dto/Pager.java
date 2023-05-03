package kr.co.strato.demosite.common.model.dto;

import com.google.common.primitives.Ints;

public class Pager {
    private int     pageNo;         //현재 페이지 번호
    private int     rowsPerPage;    //요청한 페이지 당 목록 개수
    private int     totalPage;      //전체 페이지 수
    private long    totalRow;       //전체 레코드 수

    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getRowsPerPage() {
        return rowsPerPage;
    }
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(long totalRow) {
        long totalPage = (totalRow % rowsPerPage == 0) ? totalRow / rowsPerPage : totalRow / rowsPerPage + 1;
        this.totalPage = Ints.checkedCast(totalPage);
    }
    public long getTotalRow() {
        return totalRow;
    }
    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
        this.setTotalPage(totalRow);
    }
}
