package com.dismas.imaya.directapi.Adapter;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by imaya on 5/13/16.
 */
public interface StoryApi {
    @GET("/evrybit/api/v2/story/")      //here is the other url part for the API
    void getStory(Callback<All> response);
}
