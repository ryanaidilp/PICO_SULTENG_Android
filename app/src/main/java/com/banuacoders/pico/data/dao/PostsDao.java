package com.banuacoders.pico.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.banuacoders.pico.data.model.DistrictPost;

import java.util.List;

@Dao
public interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DistrictPost post);

    @Query("DELETE FROM Posts")
    void deleteAllPosts();

    @Query("SELECT * FROM Posts")
    LiveData<List<DistrictPost>> getAllPosts();
}
