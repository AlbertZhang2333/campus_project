package com.example.ooadgroupproject.common;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SplitPage {

    //实现查询页面分组
    @Deprecated
    public static <T> List<List<T>> splitList(List<T> list, int groupSize) {
        int totalSize = list.size();
        int groupCount = (int) Math.ceil((double) totalSize / groupSize);

        return IntStream.range(0, groupCount)
                .mapToObj(i -> list.subList(i * groupSize, Math.min((i + 1) * groupSize, totalSize)))
                .collect(Collectors.toList());
    }

    public static <T> List<T> getPage(List<T> list, int pageSize, int currentPage) {
        int startIndex = (currentPage - 1) * pageSize;
        // 计算结束索引（不包括）
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
}
