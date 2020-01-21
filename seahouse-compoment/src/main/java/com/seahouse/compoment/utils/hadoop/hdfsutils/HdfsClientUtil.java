package com.seahouse.compoment.utils.hadoop.hdfsutils;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * <ul>
 * <li>文件名称: HdfsClientUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:9/19/18</li>
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

/**
 * hadoop工具类
 */

public class HdfsClientUtil {

    private static Logger log = LogManager.getLogger(HdfsClientUtil.class.getName());

    /**
     * 第三个参数是用户名  不填写 默认是windows的用户名
     */
    private static String USER;
    /**
     * hdfs url地址
     */
    @Value("${hdfs.url}")
    private static String URI_STRING;


    private static FileSystem FS = null;


    /**
     * 使用静态块加载配置文件设置参数
     */
    static {
        //加载jdbc配置文件
        InputStream _is = Object.class.getResourceAsStream("/hdfs.properties");
        Properties _pro = new Properties();
        try {
            //properties加载输入流
            _pro.load(_is);
        } catch (IOException e) {
            log.info("加载hdfs.properties失败！" + e.getMessage());
        }
        URI_STRING = _pro.getProperty("hdfs.url");
        USER = _pro.getProperty("hdfs.user");
    }


    // hdfs-default.xml    core-site.xml  hdfs-site.xml
    public static FileSystem getFileSystem() throws IOException, InterruptedException {
        if (FS == null) {
            synchronized (HdfsClientUtil.class) {
                if (FS == null) {
                    Configuration conf = new Configuration();
                    //----------------------------------不设置   就使用默认值
                    //客户端上传文件   副本数量   默认是3    改成了2
                    conf.set("dfs.replication", "2");
                    //客户端商船文件 拆分的块大小  默认是128mb
                    conf.set("dfs.bloksize", "64m");
                    //选择使用的HDFS系统类型  没有会报错
                    conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
                    //-----------------------------------
                    FS = FileSystem.get(URI.create(URI_STRING), conf, "root");
                    conf.set("fs.defaultFS", URI_STRING);
                }
            }
        }
        return FS;
    }




    /**
     * 复制本地文件夹到hdfs的文件
     *
     * @param localPath
     * @param hdfsPath
     * @return
     */
    public static boolean copyLocalDirToHdfs(String localPath, String hdfsPath) {
        try {
            File root = new File(localPath);
            File[] files = root.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    copyLocalFileToHDFS(file.getPath().toString(), hdfsPath);

                } else if (file.isDirectory()) {
                    copyLocalDirToHdfs(localPath + "/" + file.getName(), hdfsPath + "/" + file.getName());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 读取本地文件到HDFS系统, 保证文件格式是utf-8
     *
     * @param localFilename
     * @param hdfsPath
     * @return
     */
    public static boolean copyLocalFileToHDFS(String localFilename, String hdfsPath) {
        try {
            // 如果路径不存在就创建文件夹
            mkdir(hdfsPath);

            File file = new File(localFilename);
            FileInputStream is = new FileInputStream(file);

            // 如果hdfs上已经存在文件，那么先删除该文件
            if (exists(hdfsPath + "/" + file.getName())) {
                delete(hdfsPath + "/" + file.getName());
            }
            Path f = new Path(hdfsPath + "/" + file.getName());

            FileSystem hdfs = getFileSystem();

            FSDataOutputStream os = hdfs.create(f, true);
            byte[] buffer = new byte[10240000];
            int nCount = 0;

            while (true) {
                int bytesRead = is.read(buffer);
                if (bytesRead <= 0) {
                    break;
                }
                os.write(buffer, 0, bytesRead);
                nCount++;
                if (nCount % (100) == 0) {
                    System.out.println((new Date()).toString() + ": Have move " + nCount + " blocks");
                }
            }
            is.close();
            os.close();
            System.out.println((new Date()).toString() + ": Write content of file " + file.getName()
                    + " to hdfs file " + f.getName() + " success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 删除文件或者文件夹
     *
     * @param filePath  删除文件地址
     * @param recursion 是否递归删除
     * @throws Exception
     */
    public static void delete(String filePath, boolean recursion) throws Exception {
        Path p1 = new Path(filePath);
        FileSystem fs = getFileSystem();
        fs.delete(p1, recursion);
        System.out.println("删除文件夹成功: " + filePath);

    }

    /**
     * 删除文件或者文件夹
     *
     * @param filePath 删除文件地址
     * @throws Exception
     */
    public static void delete(String filePath) throws Exception {
        Path p1 = new Path(filePath);
        FileSystem fs = getFileSystem();
        fs.delete(p1, true);
        System.out.println("删除文件夹成功: " + filePath);
    }

    /**
     * 移动文件或者文件夹
     *
     * @param oldFilePath 初始路径
     * @param newFilePath 移动结束路径
     * @throws Exception
     */
    public boolean movefile(String oldFilePath, String newFilePath) throws Exception {
        Path p1 = new Path(oldFilePath);
        Path p2 = new Path(newFilePath);
        return getFileSystem().rename(p1, p2);
    }


    /**
     * 创建一个空文件
     *
     * @param filePath 文件的完整路径名称
     * @return
     */
    public static boolean mkfile(String filePath) {
        try {
            Path f = new Path(filePath);
            FSDataOutputStream os = getFileSystem().create(f, true);
            os.close();
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 创建文件夹
     *
     * @param dirName
     * @return
     */
    public static boolean mkdir(String dirName) {
        if (exists(dirName)) {
            return true;
        }
        try {
            Path f = new Path(dirName);
            System.out.println("Create and Write :" + f.getName() + " to hdfs");
            boolean flag = getFileSystem().mkdirs(f);

            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 从HDFS拷贝文件到本地
     *
     *
     *
     * @param hdfsFilePath   hdfs上的文件路径   /xxx/xxx/xxx.jpg
     * @param localFilePath  本地硬盘上的路径   d:/xxx/xxx.jpg
     * @throws IOException
     * @throws InterruptedException
     */
    public static void copyToLocalFile(String hdfsFilePath, String localFilePath) throws IOException, InterruptedException {
        FileSystem fs = getFileSystem();
        fs.copyToLocalFile(new Path(hdfsFilePath), new Path(localFilePath));
//        fs.close();
    }


    /**
     * 检查文件或者文件夹是否存在
     *
     * @param filename
     * @return
     */
    public static boolean exists(String filename) {
        try {
            Path f = new Path(filename);
            boolean flag = getFileSystem().exists(f);
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 将文件上传到HDFS中
     *
     * @param filePath 文件路径
     * @param desPath  目标路径
     * @throws IOException
     * @throws InterruptedException
     */
    public static void copyFromLocalFile(String filePath, String desPath) throws IOException, InterruptedException {
        FileSystem fs = getFileSystem();
        fs.copyFromLocalFile(new Path(filePath), new Path(desPath));
//        fs.close();
    }


    /**
     * 列出所有文件
     *
     * @param dir 需要列出的目录地址
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static List<String> ls(String dir) throws IOException, InterruptedException {
        if (StringUtils.isBlank(dir)) {
            return new ArrayList<String>();
        }
        dir = URI_STRING + dir;
        FileSystem fs = getFileSystem();
        FileStatus[] stats = fs.listStatus(new Path(dir));
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < stats.length; ++i) {
            if (stats[i].isFile()) {
                // regular file
                names.add(stats[i].getPath().toString());
            } else if (stats[i].isDirectory()) {
                // dir
                names.add(stats[i].getPath().toString());
            } else if (stats[i].isSymlink()) {
                // is s symlink in linux
                names.add(stats[i].getPath().toString());
            }
        }
//        fs.close();
        return names;
    }


    /**
     * 列出所有文件
     *
     * @param dir 需要列出的目录地址
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static List<FileStatus> lsAsFileStatus(String dir) throws IOException, InterruptedException {
        if (StringUtils.isBlank(dir)) {
            return new ArrayList<FileStatus>();
        }
        dir = URI_STRING + dir;
        FileSystem fs = getFileSystem();
        FileStatus[] stats = fs.listStatus(new Path(dir));

        return Arrays.asList(stats);
    }


    /**
     * @param path
     * @param recursion
     * @return List<LocatedFileStatus>
     * bloksize      快大小
     * len           文件长度
     * replication   副本数量
     * bloklocations 块信息  arrays.tostring(xxx.getbloklocations());
     * path          文件路径
     * @throws IOException
     * @throws InterruptedException
     */
    public static List<LocatedFileStatus> lsf(String path, boolean recursion) throws IOException, InterruptedException {
        FileSystem hdfs = getFileSystem();
        //使用迭代可以防止 文件过多 返回数组 过大  程序宕机
        RemoteIterator<LocatedFileStatus> iterator = hdfs.listFiles(new Path(path), recursion);
        List<LocatedFileStatus> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }


    /**
     * 列出所有DataNode的名字信息
     */
    public static void getDataNodeStats() {
        try {
            FileSystem hdfs = getFileSystem();
            DistributedFileSystem fs = null;
            fs = (DistributedFileSystem) hdfs;
            DatanodeInfo[] dataNodeStats = fs.getDataNodeStats();
            String[] names = new String[dataNodeStats.length];
            System.out.println("List of all the datanode in the HDFS cluster:");
            for (int i = 0; i < names.length; i++) {
                names[i] = dataNodeStats[i].getHostName();
                //输出所有datanode
                System.out.println(names[i]);
            }
            //输出HDFS的url
            System.out.println(hdfs.getUri().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测是否是备用节点
     *
     * @throws Exception
     */
    public boolean checkStandbyException(String filename) {
        try {
            Path f = new Path(filename);
            getFileSystem().exists(f);
        } catch (org.apache.hadoop.ipc.RemoteException e) {
            if (e.getClassName().equals("org.apache.hadoop.ipc.StandbyException")) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 合并文件
     *
     * @param fileList         文件的 filestatus
     * @param tarPath          新文件的地址  要附加新的文件名
     *
     * @param rowTerminateFlag 两个文件之间用什么拼接
     *                         <p>
     *                         比如文件1 是  aaaaaa
     *                         文件2是      bbbbb
     *                         传入  rowTerminateFlag 是 33333
     *                         拼接后的文件是
     *                         <p>
     *                         aaaaaa
     *                         33333
     *                         bbbbb
     * @return
     */
    public static boolean mergeDirFiles(List<FileStatus> fileList, String tarPath, String rowTerminateFlag) throws IOException, InterruptedException {
        // rowTerminateFlag \n
        FSDataOutputStream tarFileOutputStream = null;
        FSDataInputStream srcFileInputStream = null;
        FileSystem hdfs = getFileSystem();
        try {
            Path tarFile = new Path(tarPath);
            tarFileOutputStream = hdfs.create(tarFile, true);

            byte[] buffer = new byte[1024000];
            int length = 0;
            long nTotalLength = 0;
            int nCount = 0;
            boolean bfirst = true;
            for (FileStatus file : fileList) {
                if (file.getPath().equals(tarFile)) {
                    continue;
                }
                System.out.println(" merging file from  " + file.getPath() + " to " + tarPath);

                if (!bfirst) {
                    // 添加换行符
                    tarFileOutputStream.write(rowTerminateFlag.getBytes(), 0, rowTerminateFlag.length());
                }

                srcFileInputStream = hdfs.open(file.getPath(), buffer.length);
                while ((length = srcFileInputStream.read(buffer)) > 0) {
                    nCount++;
                    tarFileOutputStream.write(buffer, 0, length);
                    nTotalLength += length;
                    // System.out.println(" file length " + file.getLen() + "
                    // read " + length);
                    if (nCount % 1000 == 0) {
                        tarFileOutputStream.flush();
                        System.out.println(
                                (new Date()).toLocaleString() + ": Have move " + (nTotalLength / 1024000) + " MB");
                    }
                }
                srcFileInputStream.close();
                bfirst = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                delete(tarPath);
            } catch (Exception e2) {
                // TODO: handle exception
            }
            return false;
        } finally {
            try {
                if (tarFileOutputStream != null) {
                    tarFileOutputStream.flush();
                    tarFileOutputStream.close();
                    srcFileInputStream.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }
        }
        return true;
    }


    /**
     * 按行读取文件内容，并且防止乱码
     *
     * @param hdfsFilename
     * @return
     */
    public static String readByLine(String hdfsFilename) {
        StringBuffer sb = new StringBuffer();
        try {
            Path f = new Path(hdfsFilename);
            FileSystem hdfs = getFileSystem();
            FSDataInputStream fsdi = hdfs.open(f);
            // 防止中文乱码
            BufferedReader bf = new BufferedReader(new InputStreamReader(fsdi));
            String line = null;
            while ((line = bf.readLine()) != null) {
                sb.append(new String(line.getBytes(),"utf-8"));
//                System.out.println(new String(line.getBytes(), "utf-8"));
            }
            fsdi.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将一个字符串写入某个路径
     * 如果存在文件 就在后面追加   如果不存在 就新建
     *
     * @param text    要保存的字符串
     * @param pathStr 要保存的路径
     */
    public static void appendStringToFile(String text, String pathStr) {
        try {
            Path path = new Path(pathStr);
            FileSystem fs = getFileSystem();
            if (!fs.exists(path)) {
                fs.createNewFile(path);
            }
            //换行符
            String line = System.getProperty("line.separator");
            text = line + text;
            //要追加的文件流，inpath为文件
            InputStream in = new BufferedInputStream(new ByteArrayInputStream(text.getBytes()));
            OutputStream out = fs.append(path);
            IOUtils.copyBytes(in, out, 4096, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

//        readByLine("/test2.txt");
//        appendStringToFile("dlkajfdklasj", "/test1.txt");

//        copyLocalDirToHdfs("D:\\project\\fileUploadTest\\","/test/");

//        appendStringToFile("1233211234567", "/test/data.txt");
//        copyToLocalFile("/test/6.PNG","d:/test/6.PNG");

//        String str = readByLine("/test/data.txt");




        copyLocalFileToHDFS("D:\\test\\ahc998.txt","/wordcount/ahc998.txt");
        System.out.println("*************");
        System.out.println();
        System.out.println("*************");

    }
}
