package com.pemweb.di

import com.pemweb.data.CoofitRepository
import com.pemweb.data.ICoofitRepository
import com.pemweb.data.database.DatabaseFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.util.*
import org.koin.dsl.module
import java.net.URI

val databaseModule = module {
	single {
		DatabaseFactory(get())
	}
	
	single {
		val config = HikariConfig()
		config.apply {
			driverClassName = System.getenv("JDBC_DRIVER")
			//jdbcUrl = System.getenv("DATABASE_URL")
			maximumPoolSize = 6
			isAutoCommit = false
			transactionIsolation = "TRANSACTION_REPEATABLE_READ"
			
			val uri = URI(System.getenv("DATABASE_URL"))
			val username = uri.userInfo.split(":").toTypedArray()[0]
			val password = uri.userInfo.split(":").toTypedArray()[1]
			jdbcUrl =
				"jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"
			validate()
		}
		HikariDataSource(config)
	}
}

@InternalAPI
val repositoryModule = module {
	single<ICoofitRepository> {
		CoofitRepository(get())
	}
}