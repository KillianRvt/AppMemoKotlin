package AppDB

import android.content.Context
import androidx.room.Room
import com.killianrvt.memoapplication.AppDatabase

class AppDatabaseHelper (c: Context) {
    companion object {
        private var dbHelper: AppDatabaseHelper? = null
        private lateinit var db: AppDatabase

        @Synchronized
        fun getDatabase(c: Context): AppDatabase {
            if (dbHelper == null)
                dbHelper = AppDatabaseHelper(c.applicationContext)

            return db
        }
    }

    init {
        db = Room
            .databaseBuilder(c, AppDatabase::class.java, "memos.db")
            .allowMainThreadQueries()
            .build()
    }
}