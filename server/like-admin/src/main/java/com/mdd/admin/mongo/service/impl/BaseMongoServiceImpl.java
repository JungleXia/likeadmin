package com.mdd.admin.mongo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.google.gson.reflect.TypeToken;
import com.mdd.admin.mongo.service.BaseMongoService;
import com.mdd.common.core.PageResult;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class BaseMongoServiceImpl<T> implements BaseMongoService<T> {

    protected Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Resource
    protected MongoTemplate mongoTemplate;

    @Override
    public T save(T entity) {
        mongoTemplate.save(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        Map<String, Object> map = null;
        try {
            map = parseEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String id = null;
        Object value = null;
        Update update = new Update();
        if (map != null && map.size() > 0) {
            for (String key : map.keySet()) {
                if (key.startsWith("{")) {
                    id = key.substring(key.indexOf("{") + 1, key.indexOf("}"));
                    value = map.get(key);
                } else {
                    if (map.get(key) != null && map.get(key) != "") update.set(key, map.get(key));
                }
            }
        }
        if (id != null)
            mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where(id).is(value)), update, getEntityClass());
        return entity;
    }

    @Override
    public void delete(Object[] ids) {
        if (ids != null) {
            for (Object id : ids) {
                delete(id);
            }
        }
    }

    @Override
    public void delete(Object id) {
        Object obj = mongoTemplate.findById(id, getEntityClass());
        if (obj != null)
            mongoTemplate.remove(obj);
    }

    @Override
    public T getById(Object id) {
        return mongoTemplate.findById(id, getEntityClass());
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(getEntityClass());
    }

    @Override
    public List<T> findAll(String order) {
        List<Order> orderlist = parseOrder(order);
        if (orderlist == null || orderlist.size() == 0) {
            return findAll();
        }

        return mongoTemplate.find(new Query().with(Sort.by(orderlist)), getEntityClass());
    }

    @Override
    public List<T> findByProp(String propName, Object value) {
        return findByProp(propName, value, null);
    }

    @Override
    public List<T> findByProp(String propName, Object value, String order) {
        Query query = new Query();
        query.addCriteria(Criteria.where(propName).is(value));
        List<Order> orderlist = parseOrder(order);
        if (orderlist != null && orderlist.size() > 0) {
            query.with(Sort.by(orderlist));
        }
        return mongoTemplate.find(query, getEntityClass());
    }

    @Override
    public List<T> findByProps(String[] propName, Object[] values) {
        return findByProps(propName, values, null);
    }

    @Override
    public List<T> findByProps(String[] propName, Object[] values, String order) {
        Query query = createQuery(propName, values, order);
        return mongoTemplate.find(query, getEntityClass());
    }

    @Override
    public T uniqueByProp(String propName, Object value) {
        return mongoTemplate.findOne(new Query().addCriteria(Criteria.where(propName).is(value)), getEntityClass());
    }

    @Override
    public T uniqueByProps(String[] propName, Object[] values) {
        Query query = createQuery(propName, values, null);
        return mongoTemplate.findOne(query, getEntityClass());
    }

    @Override
    public long countByCondition(String[] params, Object[] values) {
        Query query = createQuery(params, values, null);
        long count = mongoTemplate.count(query, getEntityClass());
        return count;
    }

    protected Map<String, Object> parseEntity(T t) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String id = "";
        Field[] declaredFields = getEntityClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                map.put("{" + field.getName() + "}", field.get(t));
                id = field.getName();
                break;
            }
        }

        Method[] declaredMethods = getEntityClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().startsWith("get") && method.getModifiers() == Modifier.PUBLIC) {
                String fieldName = parse2FieldName(method.getName());
                if (!fieldName.equals(id)) {
                    map.put(fieldName, method.invoke(t));
                }
            }
        }
        return map;
    }

    private String parse2FieldName(String method) {
        String name = method.replace("get", "");
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    public Query createQuery(String[] propName, Object[] values, String order) {
        Query query = new Query();
        // where
        if (propName != null && values != null) {
            for (int i = 0; i < propName.length; i++) {
                query.addCriteria(Criteria.where(propName[i]).is(values[i]));
            }
        }

        List<Order> orderlist = parseOrder(order);
        if (orderlist != null && orderlist.size() > 0) {
            query.with(Sort.by(orderlist));
        }
        return query;
    }

    public List<Order> parseOrder(String order) {
        List<Order> list = null;
        if (order != null && !"".equals(order)) {
            list = new ArrayList<>();
            String[] fields = order.split(",");
            Order o = null;
            String[] items = null;
            for (String field : fields) {
                if (field == null) {
                    continue;
                }
                items = field.split(" ");
                if (items.length == 1) {
                    o = new Order(Direction.ASC, items[0]);
                } else if (items.length == 2) {
                    o = new Order("desc".equalsIgnoreCase(items[1]) ? Direction.DESC : Direction.ASC, items[0]);
                } else {
                    throw new RuntimeException("order field parse error");
                }
                list.add(o);
            }
        }
        return list;
    }

    @Override
    public Page<T> findPage(PageRequest pageReq) {
        Query query = new Query();
        query.with(pageReq);
        List<T> resultList = mongoTemplate.find(query, getEntityClass());
        long total = mongoTemplate.count(new Query(), getEntityClass());
        return new PageImpl<T>(resultList, pageReq, total);
    }

    @Override
    public Page<T> findPage(PageRequest pageReq, String propName, Object value) {
        Query query = new Query();
        query.addCriteria(Criteria.where(propName).is(value));
        query.with(pageReq);
        List<T> resultList = mongoTemplate.find(query, getEntityClass());
        long total = countByCondition(new String[]{propName}, new Object[]{value});
        return new PageImpl<T>(resultList, pageReq, total);
    }

    @Override
    public Page<T> findPage(PageRequest pageReq, String[] propName, Object[] values, String order) {
        Query query = createQuery(propName, values, order);
        query.with(pageReq);
        List<T> resultList = mongoTemplate.find(query, getEntityClass());
        long total = countByCondition(propName, values);
        return new PageImpl<T>(resultList, pageReq, total);
    }

    @Override
    public List<T> findByProp(String propName, Object value, String[] includeFields, String[] excludeFields) {
        Query query = new Query();
        query.addCriteria(Criteria.where(propName).is(value));
        query.with(Sort.by(new Order(Direction.DESC, "_id")));
        queryFieldWithOutPage(query, includeFields, excludeFields);
        return mongoTemplate.find(query, getEntityClass());
    }

    @Override
    public List<T> findByProps(String[] propName, Object[] values, String[] includeFields, String[] excludeFields) {
        Query query = new Query();
        if (propName != null && values != null) {
            for (int i = 0; i < propName.length; i++) {
                query.addCriteria(Criteria.where(propName[i]).is(values[i]));
            }
        }
        query = queryFieldWithOutPage(query, includeFields, excludeFields);
        return mongoTemplate.find(query, getEntityClass());
    }

    @Override
    public Page<T> findPage(PageRequest pageReq, String propName, Object value, String[] includeFields, String[] excludeFields) {
        Query query = new Query();
        query.addCriteria(Criteria.where(propName).is(value));
        query = includeQueryField(query, pageReq, includeFields, excludeFields);
        List<T> resultList = mongoTemplate.find(query, getEntityClass());
        long total = countByCondition(new String[]{propName}, new Object[]{value});
        return new PageImpl<T>(resultList, pageReq, total);
    }

    @Override
    public Page<T> findPage(PageRequest pageReq, String[] propName, Object[] values, String[] includeFields, String[] excludeFields) {
        Query query = createQuery(propName, values, null);
        query = includeQueryField(query, pageReq, includeFields, excludeFields);
        List<T> resultList = mongoTemplate.find(query, getEntityClass());
        long total = countByCondition(propName, values);
        return new PageImpl<T>(resultList, pageReq, total);
    }

    protected Query includeQueryField(Query query, PageRequest pageReq, String[] includeFields, String[] excludeFields) {
        query.with(pageReq);
        // includeFields 和 excludeFields 不能同时使用
        query = queryFieldWithOutPage(query, includeFields, excludeFields);
        return query;
    }

    protected Query queryFieldWithOutPage(Query query, String[] includeFields, String[] excludeFields) {
        // includeFields 和 excludeFields 不能同时使用
        if (includeFields != null) {
            for (String includeField : includeFields) {
                query.fields().include(includeField);
            }
        } else {
            if (excludeFields != null) {
                for (String excludeField : excludeFields) {
                    query.fields().exclude(excludeField);
                }
            }
        }
        return query;
    }

    @Override
    public PageResult<T> findPage(PageRequest pageReq, Object queryFilter) {
        return findPage(pageReq, queryFilter, null, null);
    }

    @Override
    public PageResult<T> findPage(PageRequest pageReq, Object queryFilter, String[] includeFields, String[] excludeFields) {
        Query query = new Query();
        if (queryFilter == null) {
            return null;
        }
        Type types = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> params = JSON.parseObject(JSONObject.toJSONString(queryFilter), types);
        List<String> propNameList = new ArrayList<>();
        List<Object> propValueList = new ArrayList<>();
        for (String key : params.keySet()) {
            if (params.get(key) != null && String.valueOf(params.get(key)).length() > 0) {
                query.addCriteria(Criteria.where(key).is(params.get(key)));
                propNameList.add(key);
                propValueList.add(params.get(key));
            }
        }
        query = includeQueryField(query, pageReq, includeFields, excludeFields);
        List<T> resultList = mongoTemplate.find(query, getEntityClass());
        long total = countByCondition((String[]) propNameList.toArray(new String[0]), propValueList.toArray());
        return PageResult.iPageHandle(total, (long) (pageReq.getPageNumber() + 1), (long) pageReq.getPageSize(), resultList);
    }
}