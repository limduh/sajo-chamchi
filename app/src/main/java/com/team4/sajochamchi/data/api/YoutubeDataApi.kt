package com.team4.sajochamchi.data.api

import com.team4.sajochamchi.data.model.category.CategoryResponse
import com.team4.sajochamchi.data.model.channel.ChannelResponse
import com.team4.sajochamchi.data.model.searchvideo.SearchVideoResponse
import com.team4.sajochamchi.data.model.video.VideoResponse
import com.team4.sajochamchi.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeDataApi {
    // Video: list
    // https://developers.google.com/youtube/v3/docs/videos/list?hl=ko

    // MostPopular In Korea,
    // 한국에서 가장인기있 동영상 20개
    @GET("v3/videos")
    suspend fun getAllMostPopular(
        @Query("part") part: String = "snippet",
        @Query("pageToken") pageToken: String = "",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResults") maxResults: Int = 20,
        @Query("locale") locale: String = "KR",
        @Query("regionCode") regionCode: String = "KR",
        @Query("key") key: String = API_KEY,
    ): Response<VideoResponse>

    // MostPopular In Korea With Category,
    // 한국에서 가장인기있 동영상 20개 카테고리로 가져오기
    @GET("v3/videos")
    suspend fun getAllMostPopularWithCategoryId(
        @Query("videoCategoryId") videoCategoryId: String,
        @Query("part") part: String = "snippet",
        @Query("pageToken") pageToken: String = "",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResults") maxResults: Int = 20,
        @Query("locale") locale: String = "KR",
        @Query("regionCode") regionCode: String = "KR",
        @Query("key") key: String = API_KEY,
    ): Response<VideoResponse>

    // VideoCategories: list
    // https://developers.google.com/youtube/v3/docs/videoCategories/list?hl=ko

    // 카테고리 모두 가져오기
    @GET("v3/videoCategories")
    suspend fun getAllCategories(
        @Query("part") part: String = "snippet",
        @Query("regionCode") regionCode: String = "KR",
        @Query("key") key: String = API_KEY,
    ): Response<CategoryResponse>

    // channel
    // 카테고리로 체널 가져오기
    @GET("v3/channels")
    suspend fun getChannelWithId(
        @Query("id") id : String ,
        @Query("part") part: String = "snippet",
        @Query("key") key: String = API_KEY,
    ): Response<ChannelResponse>

    // search
    // 검색
    @GET("v3/search")
    suspend fun searchVideos(
        @Query("q") q: String,
        @Query("part") part: String = "snippet",
        @Query("order") oreder: String = "viewCount",
        @Query("regionCode") regionCode: String = "KR",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 20,
        @Query("pageToken") pageToken: String = "",//페이징할꺼면 쓸꺼
        @Query("key") key: String = API_KEY,
    ): Response<SearchVideoResponse>
}