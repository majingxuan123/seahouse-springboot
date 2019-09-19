package com.seahouse.compoment.utils.hadoop.yarnutil;

import com.seahouse.compoment.utils.hadoop.hdfsutils.HdfsClientUtil;
import com.seahouse.compoment.utils.hadoop.mapreduce.wordcount.WordCountAhc998Mapper;
import com.seahouse.compoment.utils.hadoop.mapreduce.wordcount.WordCountAhc998Reduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.client.HdfsUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Date;

/**
 * 提交mapreduce job 的客户端程序
 * <p>
 * <p>
 * 这个方法将 map 和reduce两个任务的jar包传到yarn上
 * <p>
 * 实际使用的是jar包
 *
 * <p>
 * <p>
 * 封装必要的参数   将数据提交给yarn
 */
public class JobSubmitterWordCount {


    public static void main(String[] args) throws Exception {


        //设置 jvm 系统参数   job对象访问hdfs 时候默认取当前用户 也就是  mjx
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        //1***************设置JPB要访问的文件系统
        conf.set("fs.defaultFS", "hdfs://192.168.106.100:9000");
        //2*************** mapreduce 放在哪里运行     如果是local就是本地运行  或者yarn
        conf.set("mapreduce.framename", "yarn");
        //设置resourcemanager 地址
        conf.set("yarn.resourcemanager.hostname", "192.168.106.100");
        //如果要在windows上来提交job则需要提交下面这个参数 因为windows下和linux下执行的语句不同
        conf.set("mapreduce.app-submission.cross-platform","true");
        Job job = Job.getInstance(conf);
//        job.setInputFormatClass(SequenceFileInputFormat.class);
//        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        //******************3封装工程为jar包
        //可以使用这种绝对路径
//        job.setJar("d:/test.jar");
        //通过类加载获取当前类的jar包
        job.setJarByClass(JobSubmitterWordCount.class);

        //4封装参数 本次job调用的mapper实现类
        job.setMapperClass(WordCountAhc998Mapper.class);
        job.setReducerClass(WordCountAhc998Reduce.class);
        //5 封装参数  本次JOB的mapper 产生的类的 key valu 类型
        //本次任务结束后会产生一个key val的map  为了序列化  需要其类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //6 封装参数   本次JOB要处理的    输入    数据集所在路径
//        FileInputFormat.setInputPaths(job, new Path("/wordcount"));
        FileInputFormat.setInputDirRecursive(job, true);
        FileInputFormat.addInputPath(job, new Path("/wordcount"));
        //本次JOB要处理的  输出   数据集所在路径
        String outPath = "/wordcount/outputcount/";
        boolean exists = HdfsClientUtil.exists(outPath);
        if(exists){
            HdfsClientUtil.delete("/wordcount/outputcount/");
        }
        //输出的路径必须不存在   如果存在抛出异常  这里应该指定一个输出路径     结果将会在这个路径上
        FileOutputFormat.setOutputPath(job, new Path(outPath));
        //7 封装参数   想要启动的reduce task的数量
        //reduce task就是最后聚合数据的时候的数量
        job.setNumReduceTasks(2);
        //8 向 yarn 提交本次的job   job.submit();这个方法是直接提交
        System.out.println("begin --------------" + new Date().toString());
        //传入的参数是  控制台是否要打印 执行的信息？ 返回值 是运行成功或者失败
        boolean b = job.waitForCompletion(true);
        System.out.println("end --------------" + new Date().toString());
        //设置程序退出码、如果成功就0 否则返回1
        System.exit(b==true?0:1);
    }


}
