package com.hobby.gitstat.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	protected final static  Logger log = LogManager.getLogger(RedisConfig.class);
	   
	    @Value("${redis.jedis.pool.max-active}")
	    private Integer maxActive;
	    @Value("${redis.jedis.pool.max-wait}")
	    private Integer maxWait;
	    @Value("${redis.jedis.pool.max-idle}")
	    private Integer maxIdle;
	    @Value("${redis.jedis.pool.min-idle}")
	    private Integer minIdle;
	    @Value("${redis.database}")
	    private Integer database;
	    @Value("${redis.hostname}")
	    private String hostname;
	    @Value("${redis.port}")
	    private Integer port;
	    @Value("${redis.password}")
	    private String password;
	    
	    
	    @Bean
	    public JedisPoolConfig jedisPoolConfig(){
	        JedisPoolConfig config = new JedisPoolConfig();
	        
	        config.setMaxTotal(maxActive);
	        config.setMaxIdle(maxIdle);
	        config.setMinIdle(minIdle);
	        config.setMaxWaitMillis(maxWait);
           
	        return config;
	    }

	    /*
	     * //@Value("${spring.redis.cluster.nodes}")
	   // private String nodes;
	     *  集群解决方案
	     * @Bean
	    public RedisClusterConfiguration redisClusterConfiguration(){
	        RedisClusterConfiguration configuration = new RedisClusterConfiguration(Arrays.asList(nodes));
	        configuration.setMaxRedirects(maxRedirects);

	        return configuration;
	    }
	    @Bean
	    public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration configuration,JedisPoolConfig jedisPoolConfig){
	        return new JedisConnectionFactory(configuration,jedisPoolConfig);
	    }
	    * 集群解决方案
	    */
	    
	    GenericObjectPoolConfig genericObjectPoolConfig() {
	    	
	    	GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
	    	poolConfig.setMaxTotal(maxActive);
	    	poolConfig.setMaxIdle(maxIdle);
	    	poolConfig.setMinIdle(minIdle);
	    	poolConfig.setMaxWaitMillis(maxWait);
           
	    	return poolConfig;
	    	
	    }
	    
	    
	    @Bean
	    public RedisStandaloneConfiguration  redisStandaloneConfiguration() {
	    	RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
	    	log.info(">>database="+database+","+hostname);
	    	redisStandaloneConfiguration.setDatabase(database);
	    	redisStandaloneConfiguration.setHostName(hostname);
	        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
	    	redisStandaloneConfiguration.setPort(port);
	    	
	    	return redisStandaloneConfiguration;
	    }
	   
	    @Bean
	    public JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration  redisStandaloneConfiguration,JedisPoolConfig jedisPoolConfig){
	    	
	        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
	        (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
	        jpcb.poolConfig(jedisPoolConfig);
	        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
	        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);	
	    }
	    
	    @Bean
	    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
	        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        serializer.setObjectMapper(objectMapper);
	        return serializer;
	    }

	    /**
	     * RedisTemplate
	     */
	    @Bean
	    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer){
	        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	        redisTemplate.setConnectionFactory(factory);
	        //字符串方式序列化KEY
	        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	        redisTemplate.setKeySerializer(stringRedisSerializer);
	        redisTemplate.setHashKeySerializer(stringRedisSerializer);
	        //JSON方式序列化VALUE
	        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
	        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
	        redisTemplate.afterPropertiesSet();
	        return redisTemplate;
	    }
	    
	    @Bean
	    StringRedisTemplate stringRedisTemplate(JedisConnectionFactory factory) {
	    	
	    	return new StringRedisTemplate (factory);
	    	
	    }
	    
	   
	    @Bean
	    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
	    	
	    	  return new JedisPool(jedisPoolConfig,hostname);
	    }


}
