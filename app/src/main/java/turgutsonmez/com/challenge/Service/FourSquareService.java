package turgutsonmez.com.challenge.Service;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import turgutsonmez.com.challenge.Model.Explore.Explore;
import turgutsonmez.com.challenge.Model.Explore.Photos;

public interface FourSquareService {

  @GET("venues/explore/")
  Call<Explore> requestExplore(
    @Query("client_id") String client_id,
    @Query("client_secret") String client_secret,
    @Query("v") String v,
    @Query("ll") String ll,
    @Query("query") String query,
    @Query("sortByDistance") int sortByDistance);


  @GET("venues/explore/")
  Call<Explore> requestExplore2(
    @Query("client_id") String client_id,
    @Query("client_secret") String client_secret,
    @Query("v") String v,
    @Query("ll") String ll,
    @Query("query") String query,
    @Query("near") String place);

  @GET("venues/{venue_id}/photos/")
  Call<Photos> requestPhotos(
    @Path("venue_id") String venue_id,
    @Query("client_id") String client_id,
    @Query("client_secret") String client_secret,
    @Query("v") String v);


  Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.foursquare.com/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build();
}
