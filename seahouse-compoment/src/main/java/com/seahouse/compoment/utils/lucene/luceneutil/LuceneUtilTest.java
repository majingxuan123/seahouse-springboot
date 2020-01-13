package com.seahouse.compoment.utils.lucene.luceneutil;

import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: LuceneUtilTest</li>
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
public class LuceneUtilTest {

    private int[] indexs = {1, 2, 3, 4, 5, 6};
    private String[] names = {"马境宣", "周杰伦", "迪丽热巴", "周星驰", "古力娜扎", "周杰伦1"};
    private String[] content = {"my name is majingxuan", "my name is jay", "my name is dear", "my name is chou", "my name is nazha", "my name is test"};

    /**
     * 创建索引
     *
     * @throws IOException
     */
    public void createIndex() throws IOException {

        //内存中
//        Directory directory = LuceneUtil.getRamDirectory();
        Directory directory = FSDirectory.open(Paths.get("/Volumes/c2000/lucene/lucenetest"));
        //2.创建indexwriter
        IndexWriterConfig iwc = new IndexWriterConfig(new HanLPAnalyzer());
//        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        //create意思是新增索引
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iw = null;
        try {
            iw = new IndexWriter(directory, iwc);
            //3创建document对象
            Document doc = null;
            File file = new File("/Volumes/c2000/lucene/lucenetest");
            File[] files = file.listFiles();
            for (int i = 0; i < indexs.length; i++) {
                //这个参数用来排序
                int testIndex = 6 - i;
                doc = new Document();
                doc.add(new Field("content", content[i], TextField.TYPE_NOT_STORED));
                doc.add(new Field("name", names[i], StringField.TYPE_STORED));
                //声明一个index参数  用于排序
//              1.如果要存储，必须创建同名的StoredField类
//              2.如果要排序使用，必须同时创建同名的StoredField类与NumericDocValuesField类
                doc.add(new NumericDocValuesField("index", testIndex));
                doc.add(new IntPoint("index", testIndex));
                doc.add(new StoredField("index", testIndex));
                //吧文档加入
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


    public static void main(String[] args) throws IOException {
        LuceneUtilTest luceneUtilTest = new LuceneUtilTest();
        //创建索引
        luceneUtilTest.createIndex();
//        List<Document> lists = LuceneUtil.searchByString("content","my name",10);
        List<Document> lists1 =LuceneUtil.searchByString("content","my name is",10);

        if(lists1.size()>0){
            for (Document document : lists1) {
                System.out.println("----------");
                System.out.println(document.get("name"));
                System.out.println(document.get("content"));
            }

        }
    }

}
