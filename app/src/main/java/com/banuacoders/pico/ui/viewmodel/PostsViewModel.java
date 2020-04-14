package com.banuacoders.pico.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.banuacoders.pico.data.model.DistrictPost;
import com.banuacoders.pico.data.repository.PostsRepository;

import java.util.List;

public class PostsViewModel extends AndroidViewModel {

    private PostsRepository repository;
    private LiveData<List<DistrictPost>> allPosts;

    public PostsViewModel(@NonNull Application application) {
        super(application);
        repository = new PostsRepository(application);
        allPosts = repository.getPostsList();
    }

    public void insert(DistrictPost post) {
        repository.insert(post);
    }

    public void deleteAllPosts() {
        repository.deleteAllPosts();
    }

    public LiveData<List<DistrictPost>> getAllPosts() {
        return allPosts;
    }
}
