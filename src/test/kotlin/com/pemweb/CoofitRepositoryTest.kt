package com.pemweb

import com.pemweb.data.ICoofitRepository
import com.pemweb.di.databaseModule
import com.pemweb.di.repositoryModule
import com.pemweb.model.user.UserBody
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
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.verify
import kotlin.test.assertEquals

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
	fun `shouldAddedNewUser`() = runBlocking {
		val dummyUser = UserBody(
			"",
			"kylix",
			"100902",
			"Jalan",
			"",
			1000,
			"kylix.dev@gmail.com",
			"Kylix Eza Saputra",
			"08123456789",
			5000
		)
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				given(addNewUser(dummyUser)).willReturn(Unit)
				`when`(addNewUser(dummyUser)).thenReturn(Unit)
			}
		}
		
		service.addNewUser(dummyUser)
		verify(service).addNewUser(dummyUser)
	}
	
}