package com.seahouse.compoment.utils.hadoop.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 *
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT></KEYIN,>
 *
 * 1:map task 读取到的数据的key类型一行的起始偏移量
 * 2：map task 读取到的数据的value类型一行的内容
 * 3：用户自定义方法中 要返回的结果中key类型
 * 4：返回出去的结果数据的value的数据类型
 *
 *
 *
 */
public class WordCountAhc998Mapper extends Mapper<IntWritable, Text, Text, IntWritable> {

    /**
     * map方法
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     *
     *
     *
     * 17879	08066CB8F918981B47370CB3508444A8E1EA774C72BE3BC8C5973BBEA3E0457F
     * 17880	08067644C09E1EC3F3471EE9F6C86C58177D2621C3C8E588331FA2427D7910EE
     * 17881	08072961644C3A10657F700215B539D026A2FDFE43F994FED35DEB94C051453A
     */
    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行数据
        String line = value.toString();
        //根据字段切分
        String[] s = line.split("\t");
        //将结果输出
        //key：ahc998的前三位   value：ahc998的ID
        context.write(new Text(s[1].substring(0,2)),new IntWritable(Integer.parseInt(s[0])));
    }
}
