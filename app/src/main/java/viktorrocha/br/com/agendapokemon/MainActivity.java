package viktorrocha.br.com.agendapokemon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        
        
        obterDados();
    }

    private void obterDados() {
    }
}
