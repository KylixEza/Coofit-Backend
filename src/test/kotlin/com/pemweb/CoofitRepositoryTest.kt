package com.pemweb

import com.google.common.truth.Truth.assertThat
import com.pemweb.Dummy.dummyFavoriteMenuId
import com.pemweb.Dummy.dummyUpdatedUserBody
import com.pemweb.Dummy.dummyUserBody
import com.pemweb.Dummy.dummyUserResponse
import com.pemweb.Dummy.existsUser
import com.pemweb.data.ICoofitRepository
import com.pemweb.di.databaseModule
import com.pemweb.di.repositoryModule
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.loadKoinModules
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.`when`
import org.mockito.Mockito
import org.mockito.Mockito.verify

@InternalAPI
@RunWith(JUnit4::class)
class CoofitRepositoryTest: KoinTest {
	
	private val repository by inject<ICoofitRepository>()
	
	@get:Rule
	var koinTestRule = KoinTestRule.create {
		printLogger(Level.ERROR)
		modules(listOf(databaseModule, repositoryModule))
	}
	
	@get:Rule
	val mockProvider = MockProviderRule.create {
		Mockito.mock(it.java)
	}
	
	@Before
	fun setup() {
		loadKoinModules(listOf(databaseModule, repositoryModule))
	}
	
	@Test
	fun `should add new user`() = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(addNewUser(dummyUserBody)).thenAnswer {
					launch { repository.addNewUser(dummyUserBody) }
				}
			}
		}
		verify(service).addNewUser(dummyUserBody)
	}
	
	@Test
	fun `should return true if user exists`(): Unit = runBlocking {
		
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(isUserExist(existsUser.username, existsUser.password)).thenReturn(true)
			}
		}
		service.isUserExist(existsUser.username, existsUser.password)
		verify(service).isUserExist(existsUser.username, existsUser.password)
		
		val isExist = repository.isUserExist(existsUser.username, existsUser.password)
		assertThat(isExist).isTrue()
	}
	
	@Test
	fun `should get detail of user from selected uid`(): Unit = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(getUserDetail(dummyUserResponse.uid)).thenReturn(dummyUserResponse)
			}
		}
		
		service.apply {
			getUserDetail(dummyUserResponse.uid)
			verify(this).getUserDetail(dummyUserResponse.uid)
			val user = repository.getUserDetail(dummyUserResponse.uid)
			assertThat(user).isNotNull()
			print(user)
			assertThat(this.getUserDetail(dummyUserResponse.uid).username).matches(dummyUserResponse.username)
		}
	}
	
	@Test
	fun `should update the selected user`(): Unit = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(updateUser(dummyUserResponse.uid, dummyUpdatedUserBody)).thenReturn(Unit)
			}
		}
		
		service.apply {
			updateUser(dummyUserResponse.uid, dummyUpdatedUserBody)
			verify(this).updateUser(dummyUserResponse.uid, dummyUpdatedUserBody)
			assertThat(dummyUpdatedUserBody.username).isEqualTo("kylix")
			assertThat(dummyUpdatedUserBody.address).isEqualTo("Jalan Veteran Dalam Gg V, No. 398")
			assertThat(dummyUpdatedUserBody.avatar).isEqualTo("https://kylixeza.png")
		}
	}
	
	@Test
	fun `should add favorite`() = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(addFavorite(dummyUserResponse.uid, dummyFavoriteMenuId)).thenAnswer {
					launch { addFavorite(dummyUserResponse.uid, dummyFavoriteMenuId) }
				}
			}
		}
		service.addFavorite(dummyUserResponse.uid, dummyFavoriteMenuId)
		verify(service).addFavorite(dummyUserResponse.uid, dummyFavoriteMenuId)
	}
	
	/*@Test
	fun `should get all favorites by selected user`() = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(getAllFavoritesByUser(dummyUserBody.uid)).thenReturn()
			}
		}
	}*/
	
	@Test
	fun `should delete favorite`() = runBlocking {
		val service = declareMock<ICoofitRepository> {
			launch(Dispatchers.IO) {
				`when`(deleteFavorite(dummyUserResponse.uid, dummyFavoriteMenuId)).thenReturn(Unit)
			}
		}
		service.deleteFavorite(dummyUserResponse.uid, dummyFavoriteMenuId)
		verify(service).deleteFavorite(dummyUserResponse.uid, dummyFavoriteMenuId)
	}
	
	
}