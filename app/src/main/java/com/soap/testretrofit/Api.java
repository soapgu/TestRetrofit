package com.soap.testretrofit;

import io.reactivex.rxjava3.core.Completable;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface Api {
    @PATCH("v3/me/collect/spaces/{spaceId}")
    Completable collectSpaces(@Header("Authorization") String header, @Path("spaceId") String spaceId);
}
