package com.mallfe.item.aop;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.<br>
 * @author ChenZenghui
 */
public final class JsonMapper {

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static final JsonMapper NON_EMPTY = new JsonMapper(Include.NON_EMPTY);
    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static final JsonMapper NON_DEFAULT = new JsonMapper(Include.NON_DEFAULT);

    public static final JsonMapper ALWAYS = new JsonMapper(Include.ALWAYS);
    public static final JsonMapper NON_NULL = new JsonMapper(Include.NON_NULL);

    private final ObjectMapper mapper = new ObjectMapper();

    private JsonMapper(Include include) {
        //设置输出时包含属性的风格，此设置仅对序列化有效，反序列化时不同的 Include 并没有区别
        mapper.setSerializationInclusion(include);
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //禁止使用int代表Enum的order()来反序列化Enum,非常危险
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    }

    /**
     * 将 JavaBean 对象序列化为json
     * @param object JavaBean
     * @return json
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化无泛型的简单 bean
     * @param jsonString json 字符串
     * @param clazz 目标类型
     * @param <T> 目标类型
     * @return 目标类型的新对象
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return JsonMapper.NON_NULL.getMapper().readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static JsonMapper buildNonNullMapper() {
        return new JsonMapper(Include.NON_EMPTY);
    }
    /**
     * 反序列化带有泛型的复杂对象，如List<Bean>, 先使用函数 constructParametricType 构造类型,然后调用本函数.
     * @param jsonString json 字符串
     * @param javaType 目标类型
     * @param <T> 目标类型
     * @return 目标类型的新对象
     * @see #constructParametricType(Class, Class...)
     */
    public static <T> T fromJson(String jsonString, JavaType javaType) {
        try {
            return JsonMapper.NON_NULL.getMapper().readValue(jsonString, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json 反序列化为集合
     * @param json json 字符串
     * @param elementClass 集合中的元素的类型
     * @param <T> 集合中的元素的类型
     * @return 目标类型的集合
     */
    public static <T> List<T> fromJsonToList(String json, Class<T> elementClass) {
        return fromJson(json, constructParametricType(List.class, elementClass));
    }

    /**
     * json 反序列化为 Map
     * @param json json 字符串
     * @param keyClass Map 的 key 的类型
     * @param valueClass Map 的 value 的类型
     * @param <K> Map 的 key 的类型
     * @param <V> Map 的 value 的类型
     * @return Map
     */
    public static <K, V> Map<K, V> fromJsonToMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return fromJson(json, constructParametricType(Map.class, keyClass, valueClass));
    }

    /**
     * json 反序列化为 Map
     * @param json json 字符串
     * @return Map
     */
    public static Map<String, Object> fromJsonToMap(String json) {
        return fromJson(json, constructParametricType(Map.class, String.class, Object.class));
    }

    /**
     * 构造泛型的 Java Type如:<br>
     * ArrayList<MyBean>, 则调用 constructParametricType(ArrayList.class,MyBean.class)<br>
     * HashMap<String,MyBean>, 则调用 constructParametricType(HashMap.class,String.class, MyBean.class)
     * @param parametrized 主类型
     * @param elementClasses 内部泛型
     * @return JavaType
     */
    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... elementClasses) {
        return JsonMapper.NON_NULL.getMapper().getTypeFactory().constructParametricType(parametrized, elementClasses);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     * @return ObjectMapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}