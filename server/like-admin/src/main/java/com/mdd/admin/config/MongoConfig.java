package com.mdd.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * spring-boot mongodb 配置
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.mdd.admin.mongo")
public class MongoConfig {

/*    @Bean
    @ConfigurationProperties(prefix = "spring.data.mongodb")
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }*/
    /**
     * 自定义转换器
     *
     * @return org.springframework.data.mongodb.core.convert.CustomConversions
     */
    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new BigDecimalToDoubleConverter());
        converterList.add(new DoubleToBigDecimalConverter());
        return new CustomConversions(converterList);
    }

//    @Bean
//    @Primary
//    public MongoDatabaseFactory mongoDatabaseFactory(MongoProperties mongoProperties) throws Exception {
//        MongoClient client = MongoClients.create(mongoProperties.getUri());
//        return new SimpleMongoClientDatabaseFactory(client, mongoProperties.getDatabase());
//    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, MongoMappingContext mongoMappingContext) {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        //去掉_class字段
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        //自定义类型转换
        mappingConverter.setCustomConversions(customConversions());
        mappingConverter.afterPropertiesSet();
        return new MongoTemplate(mongoDatabaseFactory, mappingConverter);
    }
}

/**
 * mongo数字转换器(BigDecimal转Double)
 */
@WritingConverter
class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double> {
    @Override
    public Double convert(@NotNull BigDecimal source) {
        return source.doubleValue();
    }
}

/**
 * mongo数字转换器(Double转BigDecimal)
 **/
@ReadingConverter
class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {
    @Override
    public BigDecimal convert(Double source) {
        return new BigDecimal(String.valueOf(source));
    }
}
