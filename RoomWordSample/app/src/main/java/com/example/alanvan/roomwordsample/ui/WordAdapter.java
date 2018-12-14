package com.example.alanvan.roomwordsample.ui;

import android.content.Context;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alanvan.roomwordsample.R;
import com.example.alanvan.roomwordsample.data.database.WordEntry;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private final Context mContext;
    private List<WordEntry> mWords;

    public WordAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.
                from(mContext).inflate(R.layout.word_layout, viewGroup, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        // Determine the values of the wanted data
        WordEntry word = mWords.get(position);
        String wordText = word.getWord();

        // Set values
        holder.wordView.setText(wordText);
    }

    @Override
    public int getItemCount() {
        if (mWords == null)
            return 0;
        else
            return mWords.size();
    }

    void setWords(List<WordEntry> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    List<WordEntry> getWords() {
        return mWords;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordView;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordView = itemView.findViewById(R.id.word_tv);
        }
    }
}
