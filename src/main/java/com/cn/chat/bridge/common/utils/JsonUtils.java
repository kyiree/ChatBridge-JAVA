package com.cn.chat.bridge.common.utils;

import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@UtilityClass
public class JsonUtils {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String DATE_TIME_PATTERN = "yyy-MM-dd HH:mm:ss";

    private final String DATE_PATTERN = "yyy-MM-dd";

    private final String TIME_PATTERN = "HH:mm:ss";

    static {

    }

    public ObjectMapper newObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        configObjectMapper(objectMapper);
        return objectMapper;
    }

    /**
     * Long 会被序列化为字符串
     * 1L -> "1"
     * 1 -> 1
     *
     * @return
     */
    public ObjectMapper newObjectMapper4LongMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 将 long 转为 String, 防止 Http 无法识别 1<<52 以后的数字
        SimpleModule longModule = new SimpleModule();
        longModule.addSerializer(Long.class, ToStringSerializer.instance);
        longModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(longModule);

        configObjectMapper(objectMapper);
        return objectMapper;
    }

    /**
     * Long 会被序列化为字符串
     * 1L -> "1"
     * 1 -> 1
     *
     * @param objectMapper
     */
    public void configObjectMapper4LongMapper(ObjectMapper objectMapper) {
        // 将 long 转为 String，防止 Http 无法识别 1<<52 以后的数字
        SimpleModule longModule = new SimpleModule();
        longModule.addSerializer(Long.class, ToStringSerializer.instance);
        longModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(longModule);

        configObjectMapper(objectMapper);
    }

    public void configObjectMapper(ObjectMapper objectMapper) {

        // 序列化时，遇到空不报错
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 不序列化 null 字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 反序列时，多余字段不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 不允许使用数字替代enum
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);

        // 设置 Date 的格式
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_PATTERN));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 处理 java8 时间的序列化方式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_PATTERN)));

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        objectMapper.registerModule(javaTimeModule);
    }

    public <T> String toJson(T o) {
        BusinessException.assertNotNull(o);

        try {
            return o instanceof String ? (String) o : OBJECT_MAPPER.writeValueAsString(o);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> String toJsonSilence(T o) {
        if (Objects.isNull(o)) {
            return null;
        }
        try {
            return o instanceof String ? (String) o : OBJECT_MAPPER.writeValueAsString(o);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

    public <T> T translate(Object map, Class<T> cls) {
        if (Objects.isNull(map)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.convertValue(map, cls);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> T translate(Object map, TypeReference<T> cls) {
        if (Objects.isNull(map)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.convertValue(map, cls);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> Map<String, Object> toMap(T o) {
        try {
            return o instanceof String ? OBJECT_MAPPER.readValue((String) o, Map.class) : OBJECT_MAPPER
                    .convertValue(o, Map.class);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> T toJavaObject(String json, Class<T> cls) {
        BusinessException.assertNotBlank(json);

        try {
            return cls.equals(String.class) ? (T) json : OBJECT_MAPPER.readValue(json, cls);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> List<T> toJavaList(String json, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readValue(json, getCollectionType(List.class, cls));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> T toJavaObject(String json, TypeReference<T> tTypeReference) {
        BusinessException.assertNotBlank(json);

        try {
            return String.class.equals(tTypeReference.getType()) ? (T) json : OBJECT_MAPPER.readValue(json, tTypeReference);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> T toJavaObjectSilence(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return cls.equals(String.class) ? (T) json : OBJECT_MAPPER.readValue(json, cls);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return null;
    }

    public <T> T toJavaObjectSilence(String json, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return String.class.equals(typeReference.getType()) ? (T) json : OBJECT_MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

    public <T> T toJavaObjectSilence(String json, JavaType javaType) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

    public <T> T toJavaList(String json, TypeReference<T> typeReference) {
        BusinessException.assertNotBlank(json);

        try {
            return (T) (String.class.equals(typeReference.getType()) ? json : OBJECT_MAPPER.readValue(json, typeReference));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> T toJavaCollection(String json, Class<?> collectionClass, Class<?> ... elementClasses) {
        JavaType javaType = getCollectionType(collectionClass, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    public <T> T toJavaObject(String json, Type type) {
        try {
            return OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructType(type));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw BusinessException.create(CodeEnum.JSON_ERROR);
        }
    }

    private JavaType getCollectionType(Class<?> collectionClass, Class<?> ... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
