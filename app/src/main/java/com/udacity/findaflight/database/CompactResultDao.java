package com.udacity.findaflight.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.udacity.findaflight.data.CompactResult;

import java.util.List;

@Dao
public interface CompactResultDao {

    @Query("SELECT * FROM compact_result ORDER BY id")
    List<CompactResult> loadAllCompactResults();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCompactResult(CompactResult compactResult);

    @Delete
    void deleteCompactResult(CompactResult compactResult);

    @Query("DELETE FROM compact_result")
    void deleteAllCompactResults();
}
