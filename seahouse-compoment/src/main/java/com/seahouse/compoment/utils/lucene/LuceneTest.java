package com.seahouse.compoment.utils.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * <ul>
 * <li>文件名称: LuceneTest</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/4/18 0018</li>
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
public class LuceneTest {

    /**
     * 创建索引
     *
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        //1.创建directory
        //这种是建立在内存中的索引
//        Directory directory = new RAMDirectory();
//        创建在硬盘中
        Directory directory = FSDirectory.open(Paths.get("/usr/local/test/lucene/index0816"));

        //2.创建indexwriter
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        //create意思是新增索引
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        IndexWriter iw = null;

        try {
            iw = new IndexWriter(directory, iwc);

            //3创建document对象
            Document doc = null;
            File file = new File("/usr/local/test/luceneTest");
            File[] files = file.listFiles();
            int i = 0;
            for (File file1 : files) {
                //针对每一个文件new一个document
                doc = new Document();
                //4 給 document增加field
                //-------------------------------------------------------这里如果用stringField会报错
                //                             这里如果是type_not_stored  意思就是无法用doc.get("***")获取到内容
                doc.add(new Field("content", new FileReader(file1), TextField.TYPE_NOT_STORED));
                doc.add(new Field("filename", file1.getName(),StringField.TYPE_STORED));



                doc.add(new Field("path", file.getAbsolutePath(), StringField.TYPE_STORED));
                doc.add(new Field("id", i+"", StringField.TYPE_STORED));
//                doc.add(new NumericDocValuesField("id",i));
//                doc.add(new IntPoint("usernge",14));
                // 5.通过IndexWriter添加文件
                iw.addDocument(doc);
                i++;
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
     *             //lucene4.0以后删除的文档无法undelete
     * @throws IOException
     */
    @Test
    public void delteDocument() throws IOException {

        //1.创建directory
        //这种是建立在内存中的索引
//        Directory directory = new RAMDirectory();
//        创建在硬盘中
        Directory directory = FSDirectory.open(Paths.get("/usr/local/test/lucene/index0816"));

        //2.创建indexwriter
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        //create意思是新增索引
        iwc.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

        IndexWriter iw = null;

        try {
            iw = new IndexWriter(directory, iwc);
            //-------------------------------可以创建一个QUERY 或者传入一个trem
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            Query query = parser.parse("yansiying");
            //删除query
            iw.deleteDocuments(query);

            //删除了ID为1的文档
//            iw.deleteDocuments(new Term("id","1"));


            //意思是强制删除  不是放在回收站里
//            iw.forceMergeDeletes();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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

    @Test
    public void updateDocument() throws IOException {

        //1.创建directory
        //这种是建立在内存中的索引
//        Directory directory = new RAMDirectory();
//        创建在硬盘中
        Directory directory = FSDirectory.open(Paths.get("/usr/local/test/lucene/index0816"));

        //2.创建indexwriter
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        //create意思是新增索引
        iwc.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

        IndexWriter iw = null;

        try {
            iw = new IndexWriter(directory, iwc);
            Document doc = new Document();
            doc.add(new Field("content", "测试正文", TextField.TYPE_NOT_STORED));
            doc.add(new Field("filename", "测试名字", StringField.TYPE_STORED));
            doc.add(new Field("path", "测试地址", StringField.TYPE_STORED));
            doc.add(new Field("id", "10", StringField.TYPE_STORED));

            //更新其实是删除一个    比如这里删除ID为1的 然后新增一个新的DOC
            //删除的东西放在回收站
            iw.updateDocument(new Term("id","1"),doc);



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
        //1 创建directory
        try {
            Directory directory = FSDirectory.open(Paths.get("/usr/local/test/lucene/index0816"));
            //2 创建IndexReader
            IndexReader iReader = DirectoryReader.open(directory);

            System.out.println("最大："+iReader.maxDoc());
            System.out.println("已删除："+iReader.numDeletedDocs());
            //如果小于最大说明有文档被删除在回收站
            System.out.println("可查询："+iReader.numDocs());

            //3 根据indexreader创建indexsearcher
            IndexSearcher searcher = new IndexSearcher(iReader);
            //4 创建搜索的Query
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            //------------------------------搜索  文档中的 content   是否有关键字
            Query query = parser.parse("INFO");
//            Query query = parser.parse("INFO");
            //5 根据seacher 搜索并且返回topdocs
            //------------------------------搜索10条数据
            TopDocs topDocs = searcher.search(query, 10);
            //6、根据topdocs获取scoredoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                Document document = searcher.doc(sdoc.doc);
                System.out.println("-------------begin--------------");
                System.out.println("DOC对象："+document.toString());
                System.out.println("文件名："+document.get("filename") + "---文件地址：" + document.get("path"));
                System.out.println("-------------over--------------");
            }
            iReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
