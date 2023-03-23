package com.example.quizapp.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @property INSTANCE static property holding the instance of the room database
 */
@Database(entities = [Question::class], version = 1, exportSchema = false)
abstract class QuestionRoomDatabase: RoomDatabase() {
    /**
     * Creates the implementation of the QuestionDao
     * Returns an object of QuestionDao
     * Implemented by Room library
     */
    abstract fun questionDao(): QuestionDao

    companion object{

        @Volatile
        private var INSTANCE: QuestionRoomDatabase? = null

        /**
         * Static function
         * Creates the connection to the database
         * Returns a QuestionRoomDatabase
         * If INSTANCE is not null (connection is already created) then it is returned
         * else this function creates the instance and returns it
         * This ensures that there is only a single instance of the database for a application
         */
        fun getDatabase(context: Context): QuestionRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionRoomDatabase::class.java,
                    "questionDB"
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                /**
                 * The database needs to be pre-populated with the questions
                 * Whenever it is installed in a new device
                 */
                .createFromAsset("database/questionDB.db")
                .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
