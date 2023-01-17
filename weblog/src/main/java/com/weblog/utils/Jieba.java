package com.weblog.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import io.swagger.annotations.ApiModel;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/15
 */
@ApiModel(description = "jieba结巴分词 工具类")
@Component
public class Jieba {
    public Set<String> JIEBA(String text){
        JiebaSegmenter jieba = new JiebaSegmenter();
        List<String> list = jieba.sentenceProcess(text);
        Set<String> wordList = new HashSet<>();
        for ( String word : list ) {
            wordList.add( word );
        }
//        List<SegToken> res = segmenter.process(text, JiebaSegmenter.SegMode.INDEX);
        return wordList;
    }
}
