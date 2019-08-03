package viktorrocha.br.com.agendapokemon.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import viktorrocha.br.com.agendapokemon.models.PokemonResposta;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonResposta> obterListaPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
