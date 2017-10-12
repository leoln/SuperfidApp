package br.com.bhl.superfid.service;

import br.com.bhl.superfid.model.Produto;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IProdutoService {

    String BASE_URL = "https://superfidweb.herokuapp.com/";

    @GET("produto/parseJson?codigo={codigo}")
    Call<Produto> getProdutoByCodigo(@Path("codigo") long codigo);

}