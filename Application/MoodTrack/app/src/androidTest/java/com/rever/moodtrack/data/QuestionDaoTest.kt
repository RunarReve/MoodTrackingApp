package com.rever.moodtrack.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class QuestionDaoTest {

    private lateinit var database: QuestionDatabase
    private lateinit var dao: QuestionDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                QuestionDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.questionDao()
    }

    @After
    fun teardown(){
        database.close()
    }


    @Test
    fun insertQuestion() = runBlocking {
        val question = Question(1,0,"NULL", "TEST", "TEST",5)

        dao.addQuestion(question)
        var list = dao.readAllDataList()

        assertThat(list).contains(question)
    }

    @Test
    fun deleteQuestion() = runBlocking {
        val question1 = Question(1,0,"NULL", "TEST", "TEST",5)
        val question2 = Question(2,0,"NULL", "TEST", "TEST",5)

        dao.addQuestion(question1)
        dao.addQuestion(question2)
        dao.deleteQuestion(question1)
        var list = dao.readAllDataList()

        assertThat(list).doesNotContain(question1)
        assertThat(list).contains(question2)
    }

    @Test
    fun increaseOnlyOne() = runBlocking {
        val question1 = Question(1,0,"NULL", "TEST", "TEST",5)

        val sizeBefore = dao.readAllDataList().size
        dao.addQuestion(question1)
        var sizeAfter = dao.readAllDataList().size

        assertThat(sizeAfter).isEqualTo(sizeBefore+1)
    }

    @Test
    fun deleteAll() = runBlocking {
        val question1 = Question(1,0,"NULL", "TEST", "TEST",5)
        val question2 = Question(2,0,"NULL", "TEST", "TEST",5)

        dao.addQuestion(question1)
        dao.addQuestion(question2)
        val sizeBefore = dao.readAllDataList().size
        dao.deleteLocalData()
        val sizeAfter = dao.readAllDataList().size

        assertThat(sizeBefore).isEqualTo(2) //There was ...
        assertThat(sizeAfter).isEqualTo(0) //... now there is none
    }
}