package com.cloud.common.core.bean;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class Page<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -1306686970807124291L;

    /** 当前分页查询的数据 */
    private List<T> list;

    /** 当前页 */
    private int pageNum;

    /** 总记录数 */
    private int totalCount;

    /** 总页数 */
    private int totalPageNum;

    /** 每页记录数 */
    private int pageSize;

    public Page() {
    }

    public Page(List<T> list, int pageNum, int totalCount, int totalPageNum, int pageSize) {
        this.list = list;
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.totalPageNum = totalPageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "list=" + list +
                ", pageNum=" + pageNum +
                ", totalCount=" + totalCount +
                ", totalPageNum=" + totalPageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
