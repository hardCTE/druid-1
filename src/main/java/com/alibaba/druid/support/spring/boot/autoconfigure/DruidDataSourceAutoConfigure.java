/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.support.spring.boot.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * The Druid data source auto configure.
 *
 * @author lihengming<89921218@qq.com>
 */
@Configuration
@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
@EnableConfigurationProperties(DruidStatProperties.class)
@Import({DruidSpringAopConfiguration.class, DruidStatViewServletConfiguration.class, DruidStatFilterConfiguration.class})
public class DruidDataSourceAutoConfigure {

    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource(Environment env) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();

        //if not found prefix spring.datasource.druid property,spring.datasource prefix property will be used
        if (dataSource.getUsername() == null) {
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
        }
        if (dataSource.getPassword() == null) {
            dataSource.setPassword(env.getProperty("spring.datasource.password"));
        }
        if (dataSource.getUrl() == null) {
            dataSource.setUrl(env.getProperty("spring.datasource.url"));
        }
        if (dataSource.getDriverClassName() == null) {
            dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        }
        return dataSource;
    }
}
