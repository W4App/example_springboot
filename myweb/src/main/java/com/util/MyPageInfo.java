package com.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.model.RepairModel;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/30(星期六)
 * @Time: 10:09
 * 集合到分页->利用pagehelper的page构建分页,自定义总数, 根据参数切割集合.添加进page对象
 * 返回pageInfo对象,齐活!
 * *** 这是软分页,巨量数据有性能问题.
 */
public class MyPageInfo {
    public static PageInfo<RepairModel> getMyPage(List<RepairModel> rps, int pageNum, int pageSize) {
        Page<RepairModel> page = new Page<>(pageNum, pageSize);
        int total = rps.size();
        page.setTotal(total);
        int startIndex = (pageNum - 1) * pageSize < 0 ? 0 : (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        page.addAll(rps.subList(startIndex, endIndex));
        return new PageInfo<>(page);
    }
}
