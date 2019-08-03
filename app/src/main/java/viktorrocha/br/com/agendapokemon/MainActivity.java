package viktorrocha.br.com.agendapokemon;

import android.nfc.Tag;
import android.nfc.TagLostException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import viktorrocha.br.com.agendapokemon.models.Pokemon;
import viktorrocha.br.com.agendapokemon.models.PokemonResposta;
import viktorrocha.br.com.agendapokemon.pokeapi.PokeApiService;

public class MainActivity<TAG> extends AppCompatActivity {

    private static final String TAG = "Pokedex";
    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    private int offset;
    private boolean aptoParaCarregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if ( dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();


                    if ( aptoParaCarregar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Chegamos ao final .");

                            aptoParaCarregar = false;
                            offset += 20;
                            obterDados(offset);
                        }
                    }
                }
            }
        });



        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        aptoParaCarregar = true;
        offset = 0;
        obterDados(offset);
    }

    private void obterDados(int offset) {

        PokeApiService service = retrofit.create(PokeApiService.class);
        final Call<PokemonResposta> pokemonRespostaCall = service.obterListaPokemon(20, 40);


        pokemonRespostaCall.enqueue(new Callback<PokemonResposta>() {
            @Override
            public void onResponse(Call<PokemonResposta> call, Response<PokemonResposta> response) {
                aptoParaCarregar = true;
                if (response.isSuccessful()){


                    PokemonResposta pokemonResposta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonResposta.getResultado();

                listaPokemonAdapter.adicionarListaPokemon(listaPokemon);

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<PokemonResposta> call, Throwable t) {
                aptoParaCarregar = true;
                Log.e(TAG, "onFailure" + t.getMessage());
            }

        });
    }
}
