package com.dblck.curas.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration(
      @Value("${spring.data.redis.host:localhost}") String host,
      @Value("${spring.data.redis.port:6379}") int port,
      @Value("${spring.data.redis.password:}") String password
  ) {
    RedisStandaloneConfiguration cfg = new RedisStandaloneConfiguration(host, port);
    if (password != null && !password.isBlank()) {
      cfg.setPassword(RedisPassword.of(password));
    }
    return cfg;
  }

  @Bean
  public LettuceConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration cfg) {
    return new LettuceConnectionFactory(cfg);
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory cf, ObjectMapper mapper) {
    mapper.registerModule(new JavaTimeModule());
    var valueSerializer = new GenericJackson2JsonRedisSerializer(mapper);

    RedisCacheConfiguration base = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
        .computePrefixWith(name -> "curas::" + name + "::")
        .disableCachingNullValues()
        .entryTtl(Duration.ofMinutes(10));

    Map<String, RedisCacheConfiguration> perCache = Map.of(
        "deptById", base.entryTtl(Duration.ofHours(6)),
        "membershipsByUser", base.entryTtl(Duration.ofMinutes(10)),
        "checklistTemplateById", base.entryTtl(Duration.ofHours(1))
    );

    return RedisCacheManager.builder(cf)
        .cacheDefaults(base)
        .withInitialCacheConfigurations(perCache)
        .transactionAware()
        .build();
  }
}
