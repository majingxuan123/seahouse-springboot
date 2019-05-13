package com.seahouse.compoment.utils.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
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
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        //1.创建directory
        //这种是建立在内存中的索引
//        Directory directory = new RAMDirectory();
        //创建在硬盘中
        Directory directory = FSDirectory.open(Paths.get("d:/test/lucene/index0502"));
        //2.创建indexwriter
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        //create意思是新增索引
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(directory, iwc);
            //3创建document对象
            Document doc = null;
            File file = new File("d:/test/luceneTest");
            File[] files = file.listFiles();

            for (File file1 : files) {
                //针对每一个文件new一个document              先 创建文档 然后给文档添加关联域
                doc = new Document();
                //4 給 document增加field
                //-------------------------------------------------------这里如果用stringField会报错
                //                             这里如果是type_not_stored  意思就是无法用doc.get("***")获取到内容
                doc.add(new Field("content", new FileReader(file1), TextField.TYPE_NOT_STORED));
                doc.add(new Field("filename", file1.getName(), StringField.TYPE_STORED));
                doc.add(new Field("path", file.getAbsolutePath(), StringField.TYPE_STORED));
                // 5.通过IndexWriter添加文件      将文档写入索引之中
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
        //1 创建directory
        try {

            Directory directory = FSDirectory.open(Paths.get("d:/test/lucene/index0502"));
            //2 创建IndexReader
            IndexReader iReader = DirectoryReader.open(directory);

            //3 根据indexreader创建indexsearcher
            IndexSearcher searcher = new IndexSearcher(iReader);

            //4 创建搜索的Query   在content中查找
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            //------------------------------搜索  文档中的 content   是否有关键字
            Query query = parser.parse("yansiying");
            //5 根据seacher 搜索并且返回topdocs
            //------------------------------搜索10条数据
            TopDocs topDocs = searcher.search(query, 10);
            //6、根据topdocs获取scoredoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (ScoreDoc sdoc : scoreDocs) {
                //7根据seacher 和scordoc对象获取具体的document
                Document document = searcher.doc(sdoc.doc);

                System.out.println("-------------begin--------------");

                System.out.println(document.toString());
                System.out.println(document.get("filename") + "---" + document.get("path"));

                System.out.println("-------------over--------------");
            }
            iReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    /**
     * 删除一个已经建立的索引
     *
     * 这个索引可以被恢复
     */
    public void deleteIndex() {
        try {
            Directory directory = FSDirectory.open(Paths.get("d:/test/lucene/index0502"));
            //创建indexwriter
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            //这样就可以吧ID等于12的 索引删除
            indexWriter.deleteDocuments(new Term("id", "12"));
            //这样就是把一个查询结果删除
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            Query query = parser.parse("yansiying");
            indexWriter.deleteDocuments(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
