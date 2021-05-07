package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, List<News> allNews) {
        super(context, 0, (List<News>)allNews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        // Check if the existing view is being reused, otherwise inflate the view
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
        News news = getItem(position);
        TextView titleView = (TextView)listItemView.findViewById(R.id.title);
        TextView sectionView = (TextView)listItemView.findViewById(R.id.section);
        TextView dateView = (TextView)listItemView.findViewById(R.id.date);
        TextView authorView = (TextView)listItemView.findViewById(R.id.author);
        titleView.setText(news.getTitle());
        sectionView.setText(news.getSectionName());
        dateView.setText(news.getPublishedDate());
        authorView.setText(news.getAuthorName());
        return listItemView;
    }
}
