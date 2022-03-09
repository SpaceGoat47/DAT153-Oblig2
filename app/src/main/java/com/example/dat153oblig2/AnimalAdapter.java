package com.example.dat153oblig2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dat153oblig2.Room.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {
    private List<Animal> animals = new ArrayList<>();
// Thor var her

    @NonNull
    @Override
    public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_item, parent, false);
        return new AnimalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalHolder holder, int position) {
        Animal currentAnimal = animals.get(position);
        holder.txtName.setText(currentAnimal.getName());
        holder.imgImage.setImageURI(currentAnimal.getUriImage());
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public void setAnimals(List<Animal> animals){
        this.animals = animals;
        notifyDataSetChanged();
    }

    //holds the views in our single recyclerview items
    public class AnimalHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private ImageView imgImage;

        public AnimalHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            imgImage = itemView.findViewById(R.id.imgImage);
        }

    }
}
