package dao

import androidx.room.*
import com.killianrvt.memoapplication.MemoDTO

@Dao
abstract class MemosDAO {
    @Query("SELECT * FROM memos")
    abstract fun getListeMemos(): List<MemoDTO?>?

    @Query("SELECT COUNT(*) FROM memos WHERE intitule = :intitule")
    abstract fun countMemosParIntitule(intitule: String?): Long

    @Query("SELECT COUNT(*) FROM memos")
    abstract fun countMemos(): Long

    @Insert
    abstract fun insert(vararg memos: MemoDTO?)

    @Update
    abstract fun update(vararg memos: MemoDTO?)

    @Delete
    abstract fun delete(vararg memos: MemoDTO?)
}