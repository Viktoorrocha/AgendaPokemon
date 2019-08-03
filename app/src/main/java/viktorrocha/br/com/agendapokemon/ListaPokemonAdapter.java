package viktorrocha.br.com.agendapokemon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.ArrayList;

import viktorrocha.br.com.agendapokemon.models.Pokemon;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>  {
    private ArrayList<Pokemon> dataset;
    private Context context;


    public ListaPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nomeTextView.setText(p.getName());

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nomeTextView;

        public ViewHolder(View itemView){
            super(itemView);


            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nomeTextView = (TextView) itemView.findViewById(R.id.nomeTextView);
        }

    }
}
