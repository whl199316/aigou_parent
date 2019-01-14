package cn.itsource.aigou.query;

public class BaseQuery {
    //关键字
    private  String keyword;
    //当前页
    private Integer page;
    //每页显示的条数
    private  Integer rows;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
