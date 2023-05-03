package kr.co.strato.demosite.common.model.dto;

public class SearchParam<T> {
    private String  searchType;
    private String  searchKeyword;
    private String  startDate;
    private String  endDate;
    private T       criteria;

    public String getSearchType() {
        return searchType;
    }
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    public String getSearchKeyword() {
        return searchKeyword;
    }
    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        endDate = endDate + "23:59:59";
        this.endDate = endDate;
    }
    public T getCriteria() {
        return criteria;
    }
    public void setCriteria(T criteria) {
        this.criteria = criteria;
    }
}
