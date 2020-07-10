package com.example.smartagriculture.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartagriculture.R;
import com.example.smartagriculture.bean.CommentItem;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

import java.util.ArrayList;

public class CommentExpandAdapter extends ExpandableRecyclerAdapter<CommentItem> {
    public static final int TYPE_PERSON = 1001;

    private LRecyclerView recyclerView;
    public CommentExpandAdapter(Context context, LRecyclerView recyclerView) {
        super(context);
        this.recyclerView = recyclerView;
        //setItems(getSampleItems());
    }

    /*public static class CommentItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text;

        public CommentItem(String group) {
            super(TYPE_HEADER);
            Text = group;
        }

        public CommentItem(String first, String last) {
            super(TYPE_PERSON);
            Text = first + " " + last;
        }
    }*/

    public class CommentViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView tvName;


        public CommentViewHolder(View view, LRecyclerView recyclerView) {
            super(view, (ImageView) view.findViewById(R.id.imageView46),recyclerView);

            tvName = (TextView) view.findViewById(R.id.textDrawable25);

        }

        public void bind(int position) {
            super.bind(position);

            tvName.setText(visibleItems.get(position).Text);
        }
    }

    public class CommentChildViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView tvName;
        public CommentChildViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.textView28);
        }

        public void bind(int position) {
            tvName.setText(visibleItems.get(position).Text);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                //header中的箭头默认隐藏，如有需要，item_arrow设置为visible即可
                return new CommentViewHolder(inflate(R.layout.problem_parent, parent), recyclerView);
            case TYPE_PERSON:
            default:
                return new CommentChildViewHolder(inflate(R.layout.problem_item, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((CommentViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((CommentChildViewHolder) holder).bind(position);
                break;
        }


    }

}
