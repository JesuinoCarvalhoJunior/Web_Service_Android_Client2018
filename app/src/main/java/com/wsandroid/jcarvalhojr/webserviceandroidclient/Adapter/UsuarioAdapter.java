package com.wsandroid.jcarvalhojr.webserviceandroidclient.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wsandroid.jcarvalhojr.webserviceandroidclient.Dominio.Usuario;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Provider.ApplicationContextProvider;
import com.wsandroid.jcarvalhojr.webserviceandroidclient.Sincronizar.ItemClickListener;
import com.wsandroid.jcarvalhojr.webserviceandroidcliente.R;

import java.util.List;

/**
 * Created by JCarvalhoJr on 10/03/2018.
 */

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolderUsuario> {

    private List<Usuario> usuariosList;
    private ItemClickListener clickListener;


    public UsuarioAdapter(List<Usuario> usuariosList) {
        this.usuariosList = usuariosList;
    }


    @NonNull
    @Override
    public UsuarioAdapter.ViewHolderUsuario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.usuario_row, parent, false);
        return new ViewHolderUsuario(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioAdapter.ViewHolderUsuario holder, final int position) {
        if ((usuariosList != null) && (usuariosList.size() > 0)) {
            Usuario usuario = usuariosList.get(position);
            holder.editNome.setText(usuario.getNome());
            holder.editIdade.setText(Integer.toString(usuario.getIdade()));
            holder.nome.setText("Nome");
            holder.idade.setText("Idade");
        }
    }

    @Override
    public int getItemCount() {
        return usuariosList != null ? usuariosList.size() : 0;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void removeItem(int position) {
        if(position < 0 || position >= usuariosList.size()){
            return;
        }
        if (usuariosList.size() > 1) {
            usuariosList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, usuariosList.size());
        } else {
            Toast.makeText(ApplicationContextProvider.getContext(), "Não pode remover", Toast.LENGTH_LONG).show();
        }
    }

    public void moveItem(int oldPos, int newPos) {
        Usuario i =  usuariosList.get(oldPos);
        usuariosList.remove(oldPos);
        usuariosList.add(newPos, i);
        notifyItemMoved(oldPos, newPos);
    }

    class ViewHolderUsuario extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView editNome, editIdade , nome, idade;

        public ViewHolderUsuario(View itemView) {
            super(itemView);
            editNome = itemView.findViewById(R.id.editNome);
            editIdade = itemView.findViewById(R.id.editIdade);
            nome =(TextView)  itemView.findViewById(R.id.nome);
            idade = (TextView) itemView.findViewById(R.id.idade);
            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

}
