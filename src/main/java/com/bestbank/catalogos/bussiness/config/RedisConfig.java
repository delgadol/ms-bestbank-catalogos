package com.bestbank.catalogos.bussiness.config;

import com.bestbank.catalogos.bussiness.dto.res.CatalogoRes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean(name = "ReactiveHashOperationsCustom")
    public ReactiveHashOperations<String, String, CatalogoRes> hashOperations(ReactiveRedisConnectionFactory redisConnectionFactory){
        var template = new ReactiveRedisTemplate<>(
          redisConnectionFactory,
             RedisSerializationContext.<String, CatalogoRes>newSerializationContext(new StringRedisSerializer())
                   .hashKey(new GenericToStringSerializer<>(String.class))
                   .hashValue(new Jackson2JsonRedisSerializer<>(CatalogoRes.class))
                   .build()
        );
        return template.opsForHash();
    }


}
