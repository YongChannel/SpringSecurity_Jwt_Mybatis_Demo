package kr.co.strato.demosite.common.model.dto;

import java.util.ArrayList;
import java.util.List;

public class GridData<T> {
    private Pager   pager;  //페이지 데이터
    private List<T> rows;   //검색 레코드

    public GridData() {
        this.pager = new Pager();
        this.rows = new ArrayList<T>();
    }

    public Pager getPager() {
        return pager;
    }
    public void setPager(Pager pager) {
        this.pager = pager;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
