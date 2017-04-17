package com.wjs.common.base.vo;

import com.wjs.common.base.base.BaseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by panqingqing on 16/11/18.
 */
@Setter
@Getter
public class ValueRange<T extends Comparable> extends BaseObject {
    private T begin;
    private T end;
    private boolean closeBegin;//开始时间是否闭区间
    private boolean closeEnd;//结束时间是否闭区间

    public ValueRange() {
    }

    public ValueRange(T begin, T end) {
        this(begin, end, true, true);
    }

    public ValueRange(T begin, T end, boolean closeBegin, boolean closeEnd) {
        if (begin != null && end != null && begin.compareTo(end) > 0)
            throw new RuntimeException("起始值【" + begin + "】不能大于结束值【" + end + "】！");
        this.begin = begin;
        this.end = end;
        this.closeBegin = closeBegin;
        this.closeEnd = closeEnd;
    }

    public boolean greaterBegin(T t) {
        if (t == null) return true;
        if (closeBegin && t.compareTo(begin) >= 0) return true;
        if (t.compareTo(begin) > 0) return true;
        return false;
    }

    public boolean lessEnd(T t) {
        if (t == null) return true;
        if (closeEnd && t.compareTo(end) <= 0) return true;
        if (t.compareTo(end) > 0) return true;
        return false;
    }


}
