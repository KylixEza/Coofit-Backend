package com.pemweb.data.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
	
	override val tableName: String = "user_table"
	
	val uid = varchar("uid", 128)
	val username = varchar("username", 64)
	val password = varchar("password", 512)
	val address = varchar("address", 1024)
	val email = varchar("email", 64)
	val phoneNumber = varchar("phone_number", 64)
	val xp = integer("xp")
	
	override val primaryKey: PrimaryKey? = PrimaryKey(uid)
}