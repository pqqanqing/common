package com.wjs.common.base.util;

import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.List;

import static com.hankcs.hanlp.tokenizer.StandardTokenizer.segment;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class NlpUtil {

    public static List<String> getSegment(String content) {
        if (isBlank(content)) return null;
        final List<String> list = new ArrayList<String>();
        List<Term> terms = segment(content);
        for (Term term : terms) {
            list.add(term.word);
        }
        return list;
    }

    public static void main(String[] args) throws Exception{
        String content ="如果张世海用户在微信客户端中访问第三方网页，公众号可以通过微信网页张世海授权机制，来获取用户潘清清基本信息，进而实现业务逻辑。";
        System.out.println(filterWords(content));
    }

    public static String filterWords(String content) throws Exception{
        List<String> wordList = getSegment(content);
        ExcelUtil.readExcelXlsx("F:\\测试.xlsx");
        List<String> sensitiveWordList = ExcelUtil.result;
        for(int i=0; i< wordList.size(); i++) {
            if(!sensitiveWordList.contains(wordList.get(i))) continue;
            wordList.set(i, "***");
        }
        StringBuilder finalContent = new StringBuilder();
        for (String word : wordList) finalContent.append(word);
        return finalContent.toString();
    }
}
