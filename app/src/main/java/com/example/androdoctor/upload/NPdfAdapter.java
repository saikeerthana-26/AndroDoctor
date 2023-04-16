package com.example.androdoctor.upload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androdoctor.R;

import java.util.ArrayList;

public class NPdfAdapter extends RecyclerView.Adapter<NPdfAdapter.ViewHolder> {

    Context context;
    ArrayList link=new ArrayList();
    ArrayList title=new ArrayList();

    public NPdfAdapter(ViewNotes viewNotes, ArrayList link, ArrayList title) {
        this.context=viewNotes;
        this.link=link;
        this.title=title;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.pdf_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(title.get(position).toString().trim());
        String url=link.get(position).toString().trim();
        holder.webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);
    }


    @Override
    public int getItemCount() {
        return link.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        WebView webView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            webView=itemView.findViewById(R.id.web);
            webView.getSettings().getDisplayZoomControls();
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setWebViewClient(new Callback());
        }
    }
}
