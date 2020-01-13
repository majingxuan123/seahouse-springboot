package com.seahouse.compoment.utils.lucene.luceneutil;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: LuceneUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/5/14 0014</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
public class LuceneUtil {

    private static IndexReader I_READER = null;
    //内存中建立索引
    private static Directory RAM_DIRECTORY = null;
    //硬盘中建立索引
    private static Directory DISK_DIRECOTRY = null;

    private static int DEFAULT_QUERY_NUMBER = 10;


    //true:使用ram    false：使用disk
    private static final boolean USERAM = false;
    private static final String DISKPATH = "/Volumes/c2000/lucene/lucenetest";

    public static Directory getDirectory(){
        if(USERAM){
            return getRamDirectory();
        }
        try {
            return getDiskDirectory();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 在内存中创建索引
     * <p>
     * 内存中的索引应该是单例的
     *
     * @return
     */
    public static Directory getRamDirectory() {
        if (RAM_DIRECTORY == null) {
            synchronized (LuceneUtil.class) {
                if (RAM_DIRECTORY == null) {
                    RAM_DIRECTORY = new RAMDirectory();
                }
            }
        }
        return RAM_DIRECTORY;
    }

    /**
     * 在硬盘中创建索引
     *
     * @return
     */
    public static Directory getDiskDirectory() throws IOException {
        if (DISK_DIRECOTRY == null) {
            File f = new File(DISKPATH);
            if (!f.exists()) {
                f.mkdirs();
            } else {
                if (!f.isDirectory()) {
                    throw new IOException("请传入正确的 【目录】");
                }
            }
            DISK_DIRECOTRY = FSDirectory.open(Paths.get(DISKPATH));
        }
        return DISK_DIRECOTRY;
    }

    /**
     * 在硬盘中创建索引
     *
     * @return
     */
    public static Directory getDiskDirectory(String path) throws IOException {
        if (DISK_DIRECOTRY == null) {
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            } else {
                if (!f.isDirectory()) {
                    throw new IOException("请传入正确的 【目录】");
                }
            }
            DISK_DIRECOTRY = FSDirectory.open(Paths.get(path));
        }
        return DISK_DIRECOTRY;
    }
    /**
     * index reader 开启比较耗时  应该用一个单例的
     *
     * @return
     */
    public static IndexReader getiReader() {
        try {
            if (I_READER == null) {
                synchronized (LuceneUtil.class) {
                    if (I_READER == null) {
                        I_READER = DirectoryReader.open(getDirectory());
                    } else {
                        //如果不为空    判directory是否有改变  如果改变了  就更新indexreader
                        IndexReader newIReader = DirectoryReader.openIfChanged((DirectoryReader) I_READER);
                        if (newIReader != null) {
                            I_READER.close();
                            //将新的值附给原来的reader
                            I_READER = newIReader;
                        }
                    }
                }
            }
            return I_READER;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IndexSearcher getiIndexSearcher() {
        return new IndexSearcher(getiReader());
    }

    /**
     * 创建索引  应该让用户自己创建
     */
    public void createIndex() {

    }


    /**
     * 通过term的值来查询
     * <p>
     * 只能查询单个词
     * 例如 my name is majingxuan
     * <p>
     * 查  my 可以查到   但是查 my name 就查不到
     *
     * @param fieldName
     * @param value
     * @return
     */

    public static List<Document> searchByOneWord(String fieldName, String value, int num) {
        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        //4 创建搜索的Query
        Query termQuery = new TermQuery(new Term(fieldName, value));
        try {
            TopDocs topDocs = searcher.search(termQuery, num);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByOneWord(String fieldName, String value) {
        return searchByOneWord(fieldName, value, DEFAULT_QUERY_NUMBER);
    }

    /**
     * 通過fieldname  和值查询  可以查询到一个字母出错的情况
     *
     * @param fieldName
     * @param values
     * @param num
     * @return
     */
    public static List<Document> searchByValueFuzzy(String fieldName, String values, int num) {
        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        Query fuzzyQuery = new FuzzyQuery(new Term(fieldName, values));
        try {
            TopDocs topDocs = searcher.search(fuzzyQuery, num);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByValueFuzzy(String fieldName, String values) {
        return searchByValueFuzzy(fieldName, values, DEFAULT_QUERY_NUMBER);
    }

    /**
     * 查询包含有 传入  字符串的
     * 通过content中的内容
     * <p>
     * my name is majingxuan
     * <p>
     * 可以通过my name  或者 name is / is majingxuan
     * 都可以查到
     * <p>
     * 如果传入了   my空格is   那么会查询有my或者有is的    空格  类似 oracle中的   or
     * <p>
     * 如果传入的值是  - name:马 意思就是名字中没有马的  如果 -name:马 + dear 就是查询  名字中没有马  但是  传入的搜索域中有dear的
     *
     * @param fieldName 创建索引时  的属性名   例子中就是 content或者name
     * @param value     要搜索的字符串
     * @return
     */
    public static List<Document> searchByString(String fieldName, String value, int num) {
        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        //4 创建搜索的Query
        QueryParser parser = new QueryParser(fieldName, new HanLPAnalyzer());

        //传入的  比如是   my空格is 那么    空格会被替换成类似 oracle中的or
        //默认就是 or
        parser.setDefaultOperator(QueryParser.Operator.OR);
//      parser.setDefaultOperator(QueryParser.Operator.AND);

        try {
            //------------------------------搜索  文档中的 content   是否有关键字
            Query query = parser.parse(value);
            //5 根据seacher 搜索并且返回topdocs
            //------------------------------搜索10条数据   并且通过index来排序
            TopDocs topDocs = searcher.search(query, num);
            //6、根据topdocs获取scoredoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByString(String fieldName, String value) {
        return searchByString(fieldName, value, DEFAULT_QUERY_NUMBER);
    }

    /**
     * 前缀搜索
     * <p>
     * 比如  my name is majingxuan
     * <p>
     * 搜索 m n i   都可以搜索得到
     * <p>
     * 具体看分词
     *
     * @param fieldName
     * @param value
     * @return
     */

    public static List<Document> searchByPrefix(String fieldName, String value, int num) {
        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        //4 创建搜索的Query
        Query prefixQuery = new PrefixQuery(new Term(fieldName, value));
        try {
            TopDocs topDocs = searcher.search(prefixQuery, num);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByPrefix(String fieldName, String value) {
        return searchByPrefix(fieldName, value, DEFAULT_QUERY_NUMBER);
    }

    /**
     * 通配符搜索
     * <p>
     * 比如  my name is majingxuan
     * <p>
     * ?  匹配一个字符
     * *  匹配任意字符
     * <p>
     * 可以用这些搜索
     * <p>
     * 具体看分词
     *
     * @param fieldName
     * @param value
     * @return
     */

    public static List<Document> searchByWildCard(String fieldName, String value, int num) {
        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        //4 创建搜索的Query
        Query prefixQuery = new WildcardQuery(new Term(fieldName, value));
        try {
            TopDocs topDocs = searcher.search(prefixQuery, num);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByWildCard(String fieldName, String value) {
        return searchByWildCard(fieldName, value, DEFAULT_QUERY_NUMBER);
    }

    /**
     * 根据多条件查询    将多个条件放入booleanqueryentity的数组中
     * <p>
     * Occur.MUST：必须满足此条件，相当于and
     * <p>
     * Occur.SHOULD：应该满足，但是不满足也可以，相当于or
     * <p>
     * Occur.MUST_NOT：必须不满足。相当于not
     *
     * @param entities 多个 BooleanQueryEntity
     * @param num      查询数目
     * @return
     */
    public static List<Document> searchByBoolean(BooleanQueryEntity[] entities, int num) {

        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        //4 创建搜索的Query
        BooleanQuery.Builder boolQuery = new BooleanQuery.Builder();
        for (BooleanQueryEntity entity : entities) {
            Query query = null;
            if (!StringUtils.isEmpty(entity.getFieldName()) && !StringUtils.isEmpty(entity.getValue())) {
                query = new TermQuery(new Term(entity.getFieldName(), entity.getValue()));
            } else {
                throw new NullPointerException("传入的搜索字段与值不可为空");
            }
            if (entity.getOccur() == null) {
                throw new NullPointerException("传入的搜索类型不可为空");
            }
            BooleanClause bc = new BooleanClause(query, entity.getOccur());
            boolQuery.add(bc);
        }
        BooleanQuery booleanQueryBuild = boolQuery.build();
        System.out.println(boolQuery.toString());
        // 返回前10条
        try {
            TopDocs topDocs = searcher.search(booleanQueryBuild, num);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByBoolean(BooleanQueryEntity[] entities) {
        return searchByBoolean(entities, DEFAULT_QUERY_NUMBER);
    }

    /**
     * 針對英文
     * <p>
     * 例如 正文 my name is majingxuan i like play
     * <p>
     * 传入 参数如果是  content  ,  ['my','i'] , [3] , 10
     * <p>
     * 意思就是         查询   content中  my 和is 之间有一个词语空位的（默认分词 例子中的name is majingxuan 就分别一个词语空位）
     *
     * @param fieldName 查询字段名
     * @param values    查询的短语
     * @param intervals 词汇间隔
     * @param num       查询条数
     * @return
     */
    public static List<Document> searchByPhrase(String fieldName, String[] values, int[] intervals, int num) {
        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        PhraseQuery.Builder builder = new PhraseQuery.Builder();

        if (values.length != intervals.length + 1) {
            throw new IndexOutOfBoundsException("传入的 values.length 大小必须比 intervals.length 大1");
        }

        for (int i = 0; i < values.length; i++) {
            //如果传入的这个 间隔小于0  那么久不加入间隔
            if (i == 1) {
                builder.add(new Term(fieldName, values[i]));
            } else {
                builder.add(new Term(fieldName, values[i]), intervals[i - 1]);
            }
        }
        PhraseQuery pq = builder.build();
        try {
            TopDocs topDocs = searcher.search(pq, num);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByPhrase(String fieldName, String[] values, int[] intervals) {
        return searchByPhrase(fieldName, values, intervals, DEFAULT_QUERY_NUMBER);
    }

    /**
     * 針對英文
     * <p>
     * 查询两个字符串之间有某词语的
     * <p>
     * 比如 my name is majingxuan  i like play
     * <p>
     * 传入   content,[my majingxuan] , 2 ,10
     * <p>
     * 意思就是查询 content中   my  和majingxuan  之间相隔两个词语的        最多查询10条
     *
     * @param fieldName 查询的字段名字
     * @param values    查询的两个字符
     * @param intervals 查询两个字符之间有几个词语
     * @param num       查询多少条
     * @return
     */
    public static List<Document> searchByPhrase(String fieldName, String[] values, int intervals, int num) {

        if (values.length != 2) {
            throw new IndexOutOfBoundsException("传入的 values.length 大小必须为2");
        }

        List<Document> list = new ArrayList<>();
        IndexSearcher searcher = getiIndexSearcher();
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        //如果传入的这个 间隔小于0  那么久不加入间隔

        builder.add(new Term(fieldName, values[0]));
        builder.add(new Term(fieldName, values[1]), intervals + 1);

        PhraseQuery pq = builder.build();
        try {
            TopDocs topDocs = searcher.search(pq, num);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共查询到了---【" + scoreDocs.length + "】条数据");
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                list.add(searcher.doc(sdoc.doc));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Document> searchByPhrase(String fieldName, String[] values, int intervals) {
        return searchByPhrase(fieldName, values, DEFAULT_QUERY_NUMBER);
    }


}
