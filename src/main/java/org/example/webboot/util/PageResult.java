package org.example.webboot.util;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private int total;
    private List<T> list;

    public PageResult(int total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}