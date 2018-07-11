package com.example.cz.recyclerviewexample;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    //(2).定義兩個參數 並生成建構式
    // a.Context
    // b.ArrayList<ExampleItem> 存放ExampleItem抓取的資料

    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;

    //建立interface
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //註冊要放入RecyclerView的樣板 example_item
        // 並回傳生成
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        //holder 是ExampleViewHolder型態 他可以呼叫元件做設定
        //position 目前位子

        //ArrayList.get()方法 存放目前position位子  並依照ExampleItem的模式建立實體
        ExampleItem currentItem = mExampleList.get(position);

        //呼叫 ExampleItem的 getter方法拿取資料
        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        int likesCount = currentItem.getLikes();

        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText(likesCount + "");
        Picasso.get().load(imageUrl).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        //ArrayLIst的存放大小
        return mExampleList.size();
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        //(1).建立一個類別 繼承RecyclerView.ViewHolder
        // 生成建構式(目的:抓取example_item樣板的元件)
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener !=null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);

                        }
                    }
                }
            });

        }
    }
}
