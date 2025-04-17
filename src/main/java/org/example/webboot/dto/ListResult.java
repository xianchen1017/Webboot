package org.example.webboot.dto;

import java.util.List;

public class ListResult<T> {
    private List<T> list;  // 分页数据列表
    private int total;     // 总记录数

    // 构造函数
    public ListResult(List<T> list, int total) {
        this.list = list;
        this.total = total;
    }

    // getters and setters
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
