package viktorrocha.br.com.agendapokemon.pokeapi;

import android.telecom.Call;

public interface PokeApiService {

    Call <PokemonResposta> obterListaPokemon();
}
