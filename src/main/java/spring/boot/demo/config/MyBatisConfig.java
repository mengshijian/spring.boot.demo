package spring.boot.demo.config;

import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;


@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {


	private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

	@Autowired
	private DataSourceProperties properties;
	
	
	@Bean
	/**
	 * 配置 dataSource 相当于 xml
	 * <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	 * <property name="driverClass" value="${driverClass}"/>
	 * <property name="jdbcUrl" value="${jdbcUrl}"></property>
	 * <property name="user" value="${user}" />
	 * <property name="password" value="${password}"/> </bean>
	 */
	public DruidDataSource getDataSource() {
		logger.info("开始连接数据源...");
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(this.properties.getDriverClass());
		ds.setUrl(this.properties.getUrl());
		ds.setUsername(this.properties.getUsername());
		ds.setPassword(this.properties.getPassword());
		ds.setMaxActive(this.properties.getMaxActive());
		ds.setInitialSize(this.properties.getInitialSize());
		ds.setValidationQuery(this.properties.getValidationQuery());
		ds.setPoolPreparedStatements(this.properties.isPoolPreparedStatements());
		ds.setMaxPoolPreparedStatementPerConnectionSize(this.properties.getMaxPoolPreparedStatementPerConnectionSize());
		ds.setTestWhileIdle(true);
		ds.setTestOnBorrow(false);
		ds.setTestOnReturn(false);
		try {
			ds.setFilters(this.properties.getFilters());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

    @Bean(name = "sqlSessionFactory")
	/**
	 * xml配置 <bean class="org.mybatis.spring.SqlSessionFactoryBean">
	 * <property name="dataSource" ref="dataSource" />
	 * <property name="typeAliasesPackage" value="com.study.bean"/> </bean>
	 */
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(getDataSource());
        bean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	bean.setMapperLocations(resolver.getResources("spring/boot/demo/entity/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
	/*
	 * 配置注解事物 相当于xml <!-- spring 事务 --> <bean id="transactionManager"
	 * class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	 * <property name="dataSource" ref="dataSource"/> </bean> <!-- 开启注解事务-->
	 * <tx:annotation-driven transaction-manager="transactionManager"/>
	 */
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
}
