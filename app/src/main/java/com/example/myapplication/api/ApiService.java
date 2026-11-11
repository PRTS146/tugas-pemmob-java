package com.example.myapplication.api;

import com.example.myapplication.model.Mahasiswa;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("read.php")
    Call<List<Mahasiswa>> getAll();

    @POST("create.php")
    Call<ResponseBody> create(@Body Mahasiswa mahasiswa);

    @PUT("update.php")
    Call<ResponseBody> update(@Body Mahasiswa mahasiswa);

    @HTTP(method = "DELETE", path = "delete.php", hasBody = true)
    Call<ResponseBody> delete(@Body Map<String, Integer> id);
}
