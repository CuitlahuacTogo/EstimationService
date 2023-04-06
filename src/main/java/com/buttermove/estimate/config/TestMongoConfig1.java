package com.buttermove.estimate.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Profile("test")
@ActiveProfiles("test")
public class TestMongoConfig1 implements InitializingBean, DisposableBean {

//    MongodForTestsFactory factory = null;

    MongodConfig mongodConfig = MongodConfig.builder().version(Version.Main.PRODUCTION).build();

    MongodStarter runtime = MongodStarter.getDefaultInstance();

    MongodExecutable mongodExecutable = null;
    MongodProcess mongod = null;

    @Override
    public void destroy() {
        mongodExecutable.stop();

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mongodExecutable = runtime.prepare(mongodConfig);
        mongod = mongodExecutable.start();
    }

    @Bean(name = "test1")
    public MongoClient mongoClient() {
        MongoClient mongoClient = MongoClients.create();
        System.out.println("============================================");
        System.out.println(mongoClient);
        System.out.println("============================================");

        return mongoClient;
    }


}