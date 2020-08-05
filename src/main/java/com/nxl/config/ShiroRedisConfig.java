package com.nxl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redisson")
public class ShiroRedisConfig {

    private String host;

    private int port;
    /**
     * redis登录密码
     */
    private String password;

    /**
     * 连接超时，单位：毫秒
     */
    private int connectTimeout;

    /**
     * 命令等待超时，单位：毫秒
     */
    private int timeout = 3000;

    /**
     * 连接池最大容量。连接池的连接数量自动弹性伸缩
     */
    private int connectionPoolSize = 4;

    /**
     * 最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度
     */
    private int connectionMinimumIdleSize = 1;

    private int expire;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
