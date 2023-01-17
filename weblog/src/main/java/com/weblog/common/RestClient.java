package com.weblog.common;

import com.weblog.constants.other.EsConstants;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@Component
public class RestClient {

    @Autowired
    private RestHighLevelClient client;

    @ApiOperation("删除文档")
    public void deleteDocumentById(String index , String id) {
        try {
            //1.准备Request
            DeleteRequest request = new DeleteRequest(index,id);
            //2.备请求参数
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 参数介绍：
     * @param indexName     索引表明
     * @param esMust        必须匹配的过滤条件
     * @param esShould      选择性匹配条件
     * @param sort          排序方式以及依据
     * @param startIndex    分页查询开始的其实下表
     * @param size          分页查询的大小
     * @return
     */
    @ApiOperation("根据 综合条件 从索引库中进行分页查询")
    public SearchHit[] getListByMust_Should_SortFromIndex(String indexName, Map<String, String> esMust, Map<String, String> esShould,
                                                          Map<String, String> sort, Integer startIndex, Integer size) {

        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest(indexName);

            // 2.准备DSL
            // 2.1 query
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            request.source().query(QueryBuilders.matchAllQuery());

            // 2.2 根据 必须匹配 的过滤条件进行结果过滤查询
            if ( !esMust.isEmpty() && esMust.size() != 0 && esMust != null ) {
                filters_must(boolQuery, esMust);
            }

            // 2.3 选择性过滤查询
            if ( !esShould.isEmpty() && esShould.size() != 0 && esShould != null ) {
                filters_should(boolQuery, esShould);
            }

            request.source().query(boolQuery);

            // 2.4 排序
            if (sort.get("sortWay").equals("DESC")) {
                // 降序
                request.source().sort(sort.get("sortCondition"), SortOrder.DESC);
            } else {
                // 升序
                request.source().sort(sort.get("sortCondition"), SortOrder.ASC);
            }

            // 2.5 分页
            request.source().from(startIndex).size(size);

            // 3. 发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = handleResponce(response);

            return hits;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ApiOperation("解析响应")
    public SearchHit[] handleResponce(SearchResponse response) {
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1.获取总条数
        long total = searchHits.getTotalHits().value;
        // 4.2.文档数据
        SearchHit[] hits = searchHits.getHits();
        return hits;
    }

    @ApiOperation("选择性过滤查询")
    public void filters_should(BoolQueryBuilder boolQuery, Map<String, String> esShould) {
        /**
         * 条件过滤
         */
        Set<String> keys = esShould.keySet();
        for(String key : keys){
            String value = esShould.get(key);
            //过滤条件
            if(value != null && !value.equals("")){
                boolQuery.should(QueryBuilders.matchQuery(key, value));
            }
        }
        boolQuery.minimumShouldMatch(1);
    }

    @ApiOperation("根据 必须匹配 的过滤条件进行结果过滤查询")
    public void filters_must(BoolQueryBuilder boolQuery, Map<String, String> esMust) {
        Set<String> keys = esMust.keySet();
        for(String Key : keys){
            String value = esMust.get(Key);
            System.out.println(Key + "-->"+value);
            //过滤条件
            if(value != null && !value.equals("")){
                boolQuery.filter(QueryBuilders.termQuery(Key, esMust.get(Key)));
            }
        }
    }

    @ApiOperation("关键字搜索博文，最新、最热")
    public SearchHit[] searchBlogList(String indexName, Map<String, String> esMust, Map<String, Set<String>> esShouldList, Map<String, String> sort, Integer startIndex, Integer size) {
        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest(indexName);

            // 2.准备DSL
            // 2.1 query
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            request.source().query(QueryBuilders.matchAllQuery());

            // 2.2 根据 必须匹配 的过滤条件进行结果过滤查询
            if ( !esMust.isEmpty() && esMust != null ) {
                filters_must(boolQuery, esMust);
            }

            // 2.3 选择性过滤查询
            if ( !esShouldList.isEmpty() && esShouldList != null ) {
                Set<String> keys = esShouldList.keySet();
                for ( String key : keys ) {
                    String[] esNames = key.split(" ");
                    Set<String> values = esShouldList.get( key );
                    for ( String value : values ) {
                        boolQuery.should(QueryBuilders.matchQuery(esNames[0], value));
                        boolQuery.should(QueryBuilders.matchQuery(esNames[1], value));
                    }
                    boolQuery.minimumShouldMatch(1);
                }
            }

            request.source().query(boolQuery);

            // 2.4 排序
            if (sort.get(EsConstants.SORT_WAY).equals("DESC")) {
                // 降序
                request.source().sort(sort.get(EsConstants.SORT_CONDITION), SortOrder.DESC);
            } else {
                // 升序
                request.source().sort(sort.get(EsConstants.SORT_CONDITION), SortOrder.ASC);
            }

            // 2.5 分页
            request.source().from(startIndex).size(size);

            // 3. 发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = handleResponce(response);

            return hits;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
