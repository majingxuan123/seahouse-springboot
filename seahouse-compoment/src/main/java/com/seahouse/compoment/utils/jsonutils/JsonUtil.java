package com.seahouse.compoment.utils.jsonutils;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <ul>
 * <li>文件名称: JsonUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/3/14 0014</li>
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
public class JsonUtil {
    private static ObjectMapper objectMapper = null;
    /**
     * JSON初始化
     */
    static {
        objectMapper = new ObjectMapper();
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //禁止把POJO中值为null的字段映射到json字符串中
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    }

    private JsonUtil() {
    }
    /**
     * jackson
     * 把对象(包括数组，集合，实体类)转换成为Json字符串
     *对象(Entity,Arrays,List<String>,List<User>,Map<String,Object>,List<Map<String,Object>>,String,Date)--->JSON
     * @param obj
     * @return
     */
    public static String convertObjectToJson(Object obj) {
        if (obj == null) {
//            throw new IllegalArgumentException("对象参数不能为空。");
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * jackson
     *  把json字符串转成Object对象(json转为实体类)
     * @param jsonString 待转化的json字符串
     * @param valueType	转化后的实体类
     * @return T 转化的类型
     */
    public static <T> T parseJsonToObject(String jsonString, Class<T> valueType) {

        if(jsonString == null || "".equals((jsonString))){
            return null;
        }
        try {
            return objectMapper.readValue(jsonString, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * jackson
     *  把json字符串转成List对象（list中存放的是实体类）
     * @param jsonString 待转化的json字符串
     * @param valueType	转化后的实体类
     * @return List<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseJsonToList(String jsonString, Class<T> valueType) {

        if(jsonString == null || "".equals((jsonString))){
            return null;
        }

        List<T> result = new ArrayList<T>();
        try {
            List<LinkedHashMap<Object, Object>> list = objectMapper.readValue(jsonString, List.class);

            for (LinkedHashMap<Object, Object> map : list) {

                String jsonStr = convertObjectToJson(map);

                T t = parseJsonToObject(jsonStr, valueType);

                result.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * json
     * JSON处理含有嵌套关系对象，避免出现异常：net.sf.json.JSONException: There is a cycle in the hierarchy的方法

     * 注意：这样获得到的字符串中，引起嵌套循环的属性会置为null
     * JSONObject 和Map类似的对象，JSONObject是json中表示对象的类
     * 		json.getString(key),json.getInteger(key),json.getJSONArray(key),json.getJSONObject(key)
     * @param obj
     * @return
     */
    public static JSONObject getJsonObject(String obj) {

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object source, String name, Object value) {
                if(value==null){
                    return true;
                }
                return false;
            }
        });
        return JSONObject.fromObject(obj, jsonConfig);
    }

    /**
     * json
     * JSON处理含有嵌套关系对象，避免出现异常：net.sf.json.JSONException: There is a cycle in the hierarchy的方法

     * 注意：这样获得到的字符串中，引起嵌套循环的属性会置为null
     * JSONArray 类似List，json.getString(key),json.getInteger(key),json.getJSONArray(key),json.getJSONObject(key)
     * @param obj
     * @return
     */
    public static JSONArray getJsonArray(Object obj) {

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object source, String name, Object value) {
                if(value==null){
                    return true;
                }
                return false;
            }
        });

        return JSONArray.fromObject(obj, jsonConfig);
    }

    /**
     * json
     * 解析JSON字符串成一个MAP
     *
     * @param jsonStr json字符串，格式如： {dictTable:"BM_XB",groupValue:"分组值"}
     * @return
     */
    public static Map<String, Object> parseJsonStr(String jsonStr) {

        Map<String, Object> result = new HashMap<String, Object>();

        JSONObject jsonObj = JsonUtil.getJsonObject(jsonStr);

        for (Object key : jsonObj.keySet()) {
            result.put((String) key, jsonObj.get(key));
        }
        String str = "123123";
        return result;
    }
//
//    public static void main(String[] args) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("a", 1);
//        map.put("b", "name");
//        map.put("c", 1.2);
//        map.put("d", true);
//
//        int a = Integer.parseInt(map.get("a")+"");//null-->"null"; ""-->int
//        int a1 = MapUtils.getIntValue(map, "a", 0);
//        String b = MapUtils.getString(map, "b", null);
//        System.out.println(StringUtils.isEmpty(" "));//false 不会去空格
//        System.out.println(StringUtils.isBlank(" "));//true 会去空格
//
//        System.out.println(StringUtils.isNotEmpty(" "));//true 不会去空格
//        System.out.println(StringUtils.isNotBlank(" "));//false 会去空格
//
//    }

}
