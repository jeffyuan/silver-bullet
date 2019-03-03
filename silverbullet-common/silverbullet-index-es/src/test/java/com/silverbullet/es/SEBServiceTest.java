package com.silverbullet.es;

import com.google.common.collect.Lists;
import com.silverbullet.TestApplication;
import com.silverbullet.dao.SBERepository;
import com.silverbullet.domain.SBELog;
import com.silverbullet.service.impl.SBESerivce;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by jeffyuan on 2018/11/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class SEBServiceTest {
    @Autowired
    private SBESerivce sbeSerivce;

    @Autowired
    private SBERepository sbeRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
//        if (elasticsearchTemplate.indexExists(SBELog.class) ){
//            elasticsearchTemplate.deleteIndex(SBELog.class);
//        }
//
//        elasticsearchTemplate.createIndex(SBELog.class);
//        elasticsearchTemplate.putMapping(SBELog.class);
//        elasticsearchTemplate.refresh(SBELog.class);
    }

    @Test
    public void testSave() {
        SBELog sbeLog = new SBELog("1", "es log", "es details ", "jeffyuan", new Date(), 20);

        SBELog testSBELog = sbeSerivce.save(sbeLog);
        assertNotNull(testSBELog.getId());
        assertEquals(testSBELog.getTitle(), sbeLog.getTitle());
        assertEquals(testSBELog.getLogCont(), sbeLog.getLogCont());
        assertEquals(testSBELog.getInputDate(), sbeLog.getInputDate());
    }

    @Test
    public void testFindOne() {
        SBELog sbeLog = sbeSerivce.findOne("1");
        assertNotNull(sbeLog);
    }

    @Test
    public void testUpdate() {
        this.testSave();

        SBELog sbeLog = new SBELog("1", "es log2", "es details 2", "jeffyuan", new Date(), 2);

        SBELog testSBELog = sbeSerivce.save(sbeLog);
        assertNotNull(testSBELog.getId());
        assertEquals(testSBELog.getTitle(), sbeLog.getTitle());
        assertEquals(testSBELog.getLogCont(), sbeLog.getLogCont());
        assertEquals(testSBELog.getInputDate(), sbeLog.getInputDate());
    }

    @Test
    public void testQuery() {

        this.testSave();

        //QueryBuilder query = QueryBuilders.queryStringQuery("es");
        QueryBuilder query = QueryBuilders.termQuery("title", "es");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        List<SBELog> sbelogs = elasticsearchTemplate.queryForList(searchQuery, SBELog.class);

        for (SBELog sbelog : sbelogs) {
            System.out.println(sbelog.toString());
        }


        Pageable pageable = Pageable.unpaged();
        String title = "es";
        //按标题进行搜索
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("title", title);

        //如果实体和数据的名称对应就会自动封装，pageable分页参数
        Iterable<SBELog> listIt = sbeRepository.search(queryBuilder, pageable);

        //Iterable转list
        List<SBELog> list = Lists.newArrayList(listIt);


        {   //--单个匹配termQuery
            //不分词查询 参数1： 字段名，参数2：字段查询值，因为不分词，所以汉字只能查询一个字，英语是一个单词.
            QueryBuilder queryBuilder3 = QueryBuilders.termQuery("fieldName", "fieldlValue");
            //分词查询，采用默认的分词器
            QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("fieldName", "fieldlValue");

            //---多个匹配
            //不分词查询，参数1： 字段名，参数2：多个字段查询值,因为不分词，所以汉字只能查询一个字，英语是一个单词.
            queryBuilder = QueryBuilders.termsQuery("fieldName", "fieldlValue1", "fieldlValue2...");
            //分词查询，采用默认的分词器
            queryBuilder = QueryBuilders.multiMatchQuery("fieldlValue", "fieldName1", "fieldName2", "fieldName3");
            //匹配所有文件，相当于就没有设置查询条件
            queryBuilder = QueryBuilders.matchAllQuery();

            //-- 模糊查询（只要包含就行）
            //模糊查询常见的5个方法如下
            //1.常用的字符串查询
            QueryBuilders.queryStringQuery("fieldValue").field("fieldName");//左右模糊
            //2.常用的用于推荐相似内容的查询
            //QueryBuilders.moreLikeThisQuery(new String[] {"fieldName"})..addLikeText("pipeidhua");//如果不指定filedName，则默认全部，常用在相似内容的推荐上
            //3.前缀查询  如果字段没分词，就匹配整个字段前缀
            QueryBuilders.prefixQuery("fieldName", "fieldValue");
            //4.fuzzy query:分词模糊查询，通过增加fuzziness模糊属性来查询,如能够匹配hotelName为tel前或后加一个字母的文档，fuzziness 的含义是检索的term 前后增加或减少n个单词的匹配查询
            QueryBuilders.fuzzyQuery("hotelName", "tel").fuzziness(Fuzziness.ONE);
            //5.wildcard query:通配符查询，支持* 任意字符串；？任意一个字符
            QueryBuilders.wildcardQuery("fieldName", "ctr*");//前面是fieldname，后面是带匹配字符的字符串
            QueryBuilders.wildcardQuery("fieldName", "c?r?");

            //-- 范围查询
            //闭区间查询
            queryBuilder = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");
            //开区间查询
            queryBuilder = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2").includeUpper(false).includeLower(false);//默认是true，也就是包含
            //大于
            queryBuilder = QueryBuilders.rangeQuery("fieldName").gt("fieldValue");
            //大于等于
            queryBuilder = QueryBuilders.rangeQuery("fieldName").gte("fieldValue");
            //小于
            queryBuilder = QueryBuilders.rangeQuery("fieldName").lt("fieldValue");
            //小于等于
            queryBuilder = QueryBuilders.rangeQuery("fieldName").lte("fieldValue");
        }

    }

    @Test
    public void testQueryByWeight() {

//        this.testSave();

        String searchContent = "es";

        QueryBuilder queryBuilder = QueryBuilders.termsQuery("title", searchContent);

        // weight 为数字类型
        FieldValueFactorFunctionBuilder age = ScoreFunctionBuilders.fieldValueFactorFunction("weight").factor(10);
        //FieldValueFactorFunctionBuilder age1 = ScoreFunctionBuilders.fieldValueFactorFunction("logCont").modifier(FieldValueFactorFunction.Modifier.LN1P).factor(4);
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders =
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(age),
                        //new FunctionScoreQueryBuilder.FilterFunctionBuilder(age1)
                };

        // 根据权重进行查询
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryBuilder, filterFunctionBuilders)
                .setMinScore(2);
        System.out.println("查询的语句:" + functionScoreQueryBuilder.toString());


        Iterable<SBELog> searchResult = sbeRepository.search(functionScoreQueryBuilder);
        Iterator<SBELog> iterator = searchResult.iterator();
        List<SBELog> list = new ArrayList<SBELog>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        System.out.println(list.size());
    }

    @Test
    public void testGrouyBy() {
        // this.testSave();

        // 创建查询条件，也就是QueryBuild
        // 设置查询所有，查找所有数据
        QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();

        // 构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        // 设置QueryBuilder
        nativeSearchQueryBuilder.withQuery(matchAllQuery);

        // 设置搜索类型，默认值就是QUERY_THEN_FETCH，参考https://blog.csdn.net/wulex/article/details/71081042
        // 指定索引的类型，只先从各分片中查询匹配的文档，再重新排序和排名，取前size个文档
        nativeSearchQueryBuilder.withSearchType(SearchType.QUERY_THEN_FETCH);

        // 指定索引库和文档类型，看具体情况
        // nativeSearchQueryBuilder.withIndices("myBlog").withTypes("blog");

        // 指定聚合函数,本例中以某个字段分组聚合为例（可根据你自己的聚合查询需求设置）
        // 该聚合函数解释：计算该字段(假设为author)在所有文档中的出现频次，并按照降序排名（常用于某个字段的热度排名）
        String name = "author_test";
        TermsAggregationBuilder termsAggregation =
                AggregationBuilders.terms(name).field("author").order(BucketOrder.count(false));
        nativeSearchQueryBuilder.addAggregation(termsAggregation);

        // 构建查询对象
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        // 执行查询
        // 方法1,通过reporitory执行查询,获得有Page包装了的结果集
        Page<SBELog> search = sbeRepository.search(nativeSearchQuery);
        List<SBELog> content = search.getContent();

        List<String> ueserNameList = new ArrayList<>();
        for (SBELog esBlog : content) {
            ueserNameList.add(esBlog.getAuthor());
        }

        //获得对应的文档之后我就可以获得该文档的作者，那么就可以查出最热门用户了
        //3.2方法2,通过elasticSearch模板elasticsearchTemplate.queryForList方法查询
        List<SBELog> queryForList = elasticsearchTemplate.queryForList(nativeSearchQuery, SBELog.class);

        //3.3方法3,通过elasticSearch模板elasticsearchTemplate.query()方法查询,获得聚合(常用)
        Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        //转换成map集合
        Map<String, Aggregation> aggregationMap = aggregations.asMap();
        //获得对应的聚合函数的聚合子类，该聚合子类也是个map集合,里面的value就是桶Bucket，我们要获得Bucket
        StringTerms stringTerms = (StringTerms) aggregationMap.get(name);
        //获得所有的桶
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        //将集合转换成迭代器遍历桶,当然如果你不删除buckets中的元素，直接foreach遍历就可以了
        Iterator<StringTerms.Bucket> iterator = buckets.iterator();

        while (iterator.hasNext()) {
            //bucket桶也是一个map对象，我们取它的key值就可以了
            String username = iterator.next().getKeyAsString();//或者bucket.getKey().toString();
            //根据username去结果中查询即可对应的文档，添加存储数据的集合
            ueserNameList.add(username);
        }
    }

    @Test
    public void testHighlightField() {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        HighlightBuilder.Field[] hfields = new HighlightBuilder.Field[2];
        hfields[0] = new HighlightBuilder.Field("title").preTags("<em style='color:red'>").postTags("</em>").fragmentSize(250);
        hfields[1] = new HighlightBuilder.Field("logCont").preTags("<em style='color:red'>").postTags("</em>").fragmentSize(250);

        //添加查询的字段内容
        String[] fileds = {"title","logCont"};

        QueryBuilder matchQuery = QueryBuilders.multiMatchQuery("es", fileds);

        //搜索数据
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.withQuery(matchQuery)
                .withHighlightFields(hfields).build();

        SearchHits searchHits = elasticsearchTemplate.query(nativeSearchQuery, SearchResponse::getHits);
        System.out.println("记录数-->"+searchHits.getTotalHits());

        List<SBELog> list = new ArrayList<>();

        for(SearchHit hit : searchHits) {
            SBELog entity = new SBELog();
            Map<String, Object> entityMap = hit.getSourceAsMap();
            //高亮字段
            if(!StringUtils.isEmpty(hit.getHighlightFields().get("title"))) {
                Text[] text = hit.getHighlightFields().get("title").getFragments();
                entity.setAuthor(text[0].toString());
            }

            //map to object
            if(!CollectionUtils.isEmpty(entityMap)) {
                if(!StringUtils.isEmpty(entityMap.get("id"))) {
                    entity.setId(String.valueOf(entityMap.get("id")));
                }
                if(!StringUtils.isEmpty(entityMap.get("title"))) {
                    entity.setTitle(String.valueOf(entityMap.get("title")));
                }
            }
            list.add(entity);
        }

        /*Page<SBELog> page = elasticsearchTemplate.queryForPage(nativeSearchQuery, SBELog.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                ArrayList<SBELog> poems = new ArrayList<SBELog>();
                SearchHits hits = searchResponse.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }

                    SBELog poem = new SBELog();
                    String highLightMessage = searchHit.getHighlightFields().get("title").fragments()[0].toString();
                    poem.setId(searchHit.getId());
                    poem.setTitle(String.valueOf(searchHit.getSourceAsMap().get("title")));
                    poem.setLogCont(String.valueOf(searchHit.getSourceAsMap().get("content")));
                    // 反射调用set方法将高亮内容设置进去
                    try {
                        String setMethodName = parSetName("title");
                        Class<? extends SBELog> poemClazz = poem.getClass();
                        Method setMethod = poemClazz.getMethod(setMethodName, String.class);
                        setMethod.invoke(poem, highLightMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    poems.add(poem);
                }
                if (poems.size() > 0) {
                    return new AggregatedPageImpl<T>((List<T>) poems);
                }
                return null;
        }});*/
    }

    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

}
