package network;

import models.CurrencyConversion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerAPIInterface {

    @GET("latest?")
    Call<CurrencyConversion> getCurrencyConversion(@Query("base") String base,  @Query("symbols") String out);
}
