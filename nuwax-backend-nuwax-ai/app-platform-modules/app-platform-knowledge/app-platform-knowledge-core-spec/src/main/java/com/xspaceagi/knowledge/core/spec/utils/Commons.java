package com.xspaceagi.knowledge.core.spec.utils;

import java.util.ArrayList;
import java.util.List;

public class Commons {
    public static String collectionName(Long id) {
        return Constants.VECTOR_DB_COLLECTION_PRE + id;
    }

    public static List<String> segmentStrings(List<String> input, int words,
                                              int overlap) {
        List<String> result = new ArrayList<>();

        for (String str : input) {
            var strLen = str.length();

            if (strLen <= words) {
                result.add(str.trim());
            } else {
                int start = 0;
                //限制最大循环次数
                int maxRecycleCount = 3000;
                int currentRecycleCount = 0;

                while (start < strLen && currentRecycleCount < maxRecycleCount) {
                    int end = Math.min(start + words, strLen);
                    String segment = str.substring(start, end).trim();
                    result.add(segment);
                    //重叠字符比例 overlap 计算
                    var minOverlap = Math.min(overlap, 100);
                    var overlapLength = (int) (segment.length() * minOverlap / 100.0);

                    //如果重叠字符,大于等于 最大字符长度,则不进行重叠处理,否则永远无法循环处理完毕
                    if (overlapLength >= words) {
                        start += words;
                    } else {
                        start += words - overlapLength;

                        //限制start最小是end,因为start 可能因重叠字符比例,变成负数
                        start = Math.max(start, 1);
                    }

                    currentRecycleCount++;

                }
            }
        }
        return result;
    }

    public static String parseJSON(String json) {
        int start = json.indexOf("[");
        int end = json.lastIndexOf("]");
        if (start == -1 || end == -1) {
            return "[]";
        }
        return json.substring(start, end + 1);
    }
}
