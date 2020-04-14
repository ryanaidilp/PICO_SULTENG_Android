package com.banuacoders.pico.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.banuacoders.pico.data.dao.PostsDao;
import com.banuacoders.pico.data.database.CovidDatabase;
import com.banuacoders.pico.data.model.DistrictPost;

import java.util.List;

public class PostsRepository {
    PostsDao postsDao;
    private LiveData<List<DistrictPost>> postsList;

    public PostsRepository(Application application) {
        CovidDatabase database = CovidDatabase.getInstance(application);
        postsDao = database.postsDao();
        postsList = postsDao.getAllPosts();
    }

    public void insert(DistrictPost post) {
        new InsertPostAsyncTask(postsDao).execute(post);
    }

    public void deleteAllPosts() {
        new DeleteAllPostAsyncTask(postsDao).execute();
    }

    public LiveData<List<DistrictPost>> getPostsList() {
        return postsList;
    }

    private static class InsertPostAsyncTask extends AsyncTask<DistrictPost, Void, Void> {
        private PostsDao postsDao;

        InsertPostAsyncTask(PostsDao postsDao) {
            this.postsDao = postsDao;
        }

        @Override
        protected Void doInBackground(DistrictPost... districtPosts) {
            postsDao.insert(districtPosts[0]);
            return null;
        }
    }


    private static class DeleteAllPostAsyncTask extends AsyncTask<Void, Void, Void> {
        private PostsDao postsDao;

        public DeleteAllPostAsyncTask(PostsDao postsDao) {
            this.postsDao = postsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postsDao.deleteAllPosts();
            return null;
        }
    }
}
