package com.confrades.dataSources.repository

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider

class MongoClients {

    fun getMongoClient(): MongoClient {
        val pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
        val codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry)
        val clientSettings = MongoClientSettings.builder().codecRegistry(codecRegistry).build()

        return MongoClients.create(clientSettings)
    }

}
