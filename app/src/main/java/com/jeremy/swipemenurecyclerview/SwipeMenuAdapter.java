package com.jeremy.swipemenurecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremy.swipemenurecyclerview.view.SwipeMenuItemLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 2016/12/9.
 */
public class SwipeMenuAdapter extends RecyclerView.Adapter<SwipeMenuAdapter.ViewHolder> {

    private SwipeMenuItemLayout mOpenView = null;

    private List<Boolean> menuStatusList;
    private List<String> nameList;
    private Context context;

    public SwipeMenuAdapter(Context context, List<String> nameList) {
        this.context = context;
        this.nameList = nameList;
        this.menuStatusList = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            menuStatusList.add(false);
        }
    }

    @Override
    public SwipeMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SwipeMenuAdapter.ViewHolder holder, final int position) {
        final String name = nameList.get(position);
        final boolean menuOpen = menuStatusList.get(position);
        holder.tvName.setText(name);
        holder.smilRoot.setiMenuStatusChangerListener(new SwipeMenuItemLayout.IMenuStatusChangerListener() {
            @Override
            public void onMenuStatusChangeListener(boolean isOpen) {
                menuStatusList.set(position, isOpen);
                if (isOpen) {
                    mOpenView = holder.smilRoot;
                }
            }
        });
        holder.smilRoot.setMenuOpenByNoScroll(menuOpen);
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                holder.smilRoot.setMenuOpen(false);
            }
        });
        holder.tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.smilRoot.setMenuOpen(false);
                removeItem(position);
                insertItem(0, name);
                Toast.makeText(context, "置顶", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.smilRoot.setMenuOpen(false);
                removeItem(position);
                Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertItem(int position, String name) {
        menuStatusList.add(position, false);
        nameList.add(position, name);
        notifyItemInserted(position);
        if (position != nameList.size()) {
            notifyItemRangeChanged(position, nameList.size() - position);
        }
    }

    private void removeItem(int position) {
        menuStatusList.remove(position);
        nameList.remove(position);
        notifyItemRemoved(position);
        if (position != nameList.size()) {
            notifyItemRangeChanged(position, nameList.size() - position);
        }
    }

    public int getMenuOpenItemIndex() {
        for (int i = 0; i < menuStatusList.size(); i++) {
            if (menuStatusList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void closeMenus() {
        mOpenView.setMenuOpen(false);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SwipeMenuItemLayout smilRoot;
        View tvContent;
        TextView tvName;
        View tvTop;
        View tvDelete;

        ViewHolder(View itemView) {
            super(itemView);
            this.smilRoot = (SwipeMenuItemLayout) itemView.findViewById(R.id.smil_root);
            this.tvContent = itemView.findViewById(R.id.ll_content);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvTop = itemView.findViewById(R.id.tv_top);
            this.tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
