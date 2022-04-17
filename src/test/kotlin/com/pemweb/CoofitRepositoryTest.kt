package com.pemweb

import com.google.common.truth.Truth.assertThat
import com.pemweb.Dummy.dummyUserBody
import com.pemweb.Dummy.dummyUserResponse
import com.pemweb.Dummy.existsUser
import com.pemweb.data.ICoofitRepository
import com.pemweb.di.databaseModule
import com.pemweb.di.repositoryModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.`when`
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class CoofitRepositoryTest: KoinTest {
	
	@get:Rule
	var koinTestRule = KoinTestRule.create {
		printLogger(Level.ERROR)
		modules(listOf(databaseModule, repositoryModule))
	}
	
	@get:Rule
	val mockProvider = MockProviderRule.create {
		Mockito.mock(it.java)
	}
	
	@Test
	fun `should add new user`() = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(addNewUser(dummyUserBody)).thenReturn(Unit)
			}
		}
		
		service.addNewUser(dummyUserBody)
		verify(service).addNewUser(dummyUserBody)
	}
	
	@Test
	fun `should return true if user exists`() = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(isUserExist(existsUser.username, existsUser.password)).thenReturn(true)
			}
		}
		
		service.isUserExist(existsUser.username, existsUser.password)
		verify(service).isUserExist(existsUser.username, existsUser.password)
		assertThat(service.isUserExist(existsUser.username, existsUser.password)).isTrue()
	}
	
	@Test
	fun `should get detail of user from selected uid`(): Unit = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(getUserDetail(dummyUserBody.uid)).thenReturn(dummyUserResponse)
			}
		}
		
		service.apply {
			getUserDetail(dummyUserBody.uid)
			verify(this).getUserDetail(dummyUserBody.uid)
			assertThat(this.getUserDetail(dummyUserBody.uid).username).matches(dummyUserResponse.username)
		}
	}
	
}