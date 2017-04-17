package com.wjs.common.base.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by panqingqing on 16/5/4.
 */
public class ClassPathNameFileFilter implements FileFilter {

    private boolean recursive = false;//是否要进行循环迭代,默认不循环
    private String matchParam;//匹配参数

    public ClassPathNameFileFilter(String matchParam, boolean recursive) {
        this.matchParam = matchParam;
        this.recursive = recursive;
    }

    @Override
    public boolean accept(File file) {
        //自定义过滤规则,如果可以循环(包含子目录)或则是以.matchParam结尾的文件(编译好的java类文件)
        return (recursive && file.isDirectory()) || (file.getName().endsWith(matchParam));
    }
}
