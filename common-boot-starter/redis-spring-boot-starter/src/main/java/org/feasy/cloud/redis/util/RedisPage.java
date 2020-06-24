package org.feasy.cloud.redis.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis分页工具类
 * @author yangxiaohui
 */
public class RedisPage<T> {
    // 当前页码
    private long pageNum;
    // 每页数据条数
    private long pageSize;
    // 数据总条数
    private long count;
    // 数据总页数
    private long countNum;
    private List<T> data;
    private long start;
    private long end;
    public RedisPage(long pageNum, long pageSize, long count){
        this.pageNum=pageNum;
        this.pageSize=pageSize;
        this.count=count;
        this.countNum=new Double(Math.ceil((count*1.0)/(pageSize*1.0))).intValue();
        this.start=(Math.min(pageNum, countNum)-1)*pageSize;
        this.end=Math.min((Math.min(pageNum, countNum))*pageSize,count-1);
    }
    public RedisPage(){
        this.data=new ArrayList<>();
    }
    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getCountNum() {
        return countNum;
    }

    public void setCountNum(long countNum) {
        this.countNum = countNum;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data=data;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
