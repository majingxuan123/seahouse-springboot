package com.seahouse.compoment.utils.lucene.lucenestudy0507;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

/**
 * 创建索引  并且通过index排序
 * <p>
 * 新版本的lucene推荐存储整形的时候使用 intpoint
 * <p>
 * 而想要存储的话 必须要new一个 StoredField
 * <p>
 * 如果想要排序的话  则需要  NumericDocValuesField
 * <p>
 * <p>
 * 因为IndexReader打开关闭十分消耗时间 所以 应该写一个单例的
 *
 * @author majx
 * @version 1.0.0
 */
public class CreateIndex {

    //这里假设几个数据
    //假设目录1 的title是马境宣  内容是my name is majingxuan
    private int[] indexs = {1, 2, 3, 4, 5, 6};
    private String[] names = {"马境宣", "周杰伦", "迪丽热巴", "周星驰", "古力娜扎", "测试员1"};
    private String[] content = {"my name is majingxuan", "my name is jay", "my name is dear", "my name is chou", "my name is nazha", "my name is test"};


    private static IndexReader iReader = null;
    private static Directory directory = null;

    //reader打开关闭十分耗时  所以 应该建立一个单例的
    public CreateIndex() {
        getDirectory();
        getiReader();
    }

    public IndexReader getiReader() {
        try {
            if (iReader == null) {
                iReader = DirectoryReader.open(directory);
            } else {
                IndexReader newIReader = DirectoryReader.openIfChanged((DirectoryReader) iReader);
                if (newIReader != null) {
                    iReader.close();
                    iReader = newIReader;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return iReader;
    }

    public Directory getDirectory() {
        try {
            if (directory != null) {
            } else {
                directory = FSDirectory.open(Paths.get("d:/test/lucene/index0507"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory;
    }

    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void createIndex() {
        //2.创建indexwriter
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        //create意思是新增索引
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(this.getDirectory(), iwc);
            //3创建document对象
            Document doc = null;
            for (int i = 0; i < indexs.length; i++) {
                int testIndex = 6 - i;
                doc = new Document();
                //方式1：
                //对内容进行分词、建立索引、   最后一个参数判断是否存储
//                doc.add(new TextField("name", names[i], Field.Store.YES));
                //会对内容进行索引  但是不进行分词     比如作者是 马境宣   必须用马境宣来搜索  用马搜索就无效
//                doc.add(new StringField("name", names[i], Field.Store.YES));

                //方式2：
                doc.add(new Field("content", content[i], TextField.TYPE_NOT_STORED));
                doc.add(new Field("name", names[i], StringField.TYPE_STORED));
                //声明一个index参数  用于排序
//                1.如果要存储，必须创建同名的StoredField类
//                2.如果要排序使用，必须同时创建同名的StoredField类与NumericDocValuesField类
                doc.add(new NumericDocValuesField("index", testIndex));
                doc.add(new IntPoint("index", testIndex));
                doc.add(new StoredField("index", testIndex));
                iw.addDocument(doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (iw != null) {
                try {
                    iw.commit();
                    iw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建查询
     */
    @Test
    public void search() {
        try {
            //1 创建directory
//            Directory directory = getRamDirectory();
            //2 创建IndexReader
            IndexReader iReader = getiReader();
            //3 根据indexreader创建indexsearcher
            IndexSearcher searcher = new IndexSearcher(iReader);
            //4 创建搜索的Query
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            //------------------------------搜索  文档中的 content   是否有关键字
            Query query = parser.parse("my name");
            //5 根据seacher 搜索并且返回topdocs
            //------------------------------搜索10条数据   并且通过index来排序
            TopDocs topDocs = searcher.search(query, 10, new Sort(new SortField("index", SortField.Type.INT)));
            //6、根据topdocs获取scoredoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                Document document = searcher.doc(sdoc.doc);
                System.out.println("-------------begin--------------");
                System.out.println(document.toString());
                System.out.println();
                System.out.println(document.get("index") + "---" + document.get("name"));
                System.out.println("-------------over--------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将 NAME = 周星驰的  索引删除
     */
    @Test
    public void deleteIndex() {
        search();
        Directory directory = getDirectory();
        //2.创建indexwriter
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(directory, iwc);
            iw.deleteDocuments(new Term("name", "周星驰"));
            iw.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        search();
    }


    public static void main(String[] args) throws java.text.ParseException {
        String str = DateTools.dateToString(new Date(), DateTools.Resolution.MINUTE);
        System.out.println(str);
        Date date = DateTools.stringToDate(str);
        System.out.println("date" + date);
    }
}
