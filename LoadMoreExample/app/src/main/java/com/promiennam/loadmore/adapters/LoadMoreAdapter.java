package com.promiennam.loadmore.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.promiennam.loadmore.R;

import java.util.List;

/**
 * Created by Phuong on 5/14/2016.
 */
public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int CHILD = 0;
    public static final int PROGRESS = 1;

    private Context context;
    private List<Item> itemList;
    private RecyclerView recyclerView;
    private LayoutInflater inflater;

    private boolean isLoading = false;
    private boolean isFirstTime = true;
    public int totalItemCount;
    private int lastVisibleItem;
    private int visibleThreshold;

    public LoadMoreAdapter(Context context, List<Item> itemList, RecyclerView recyclerView) {
        this.context = context;
        this.itemList = itemList;
        this.recyclerView = recyclerView;
        this.inflater = LayoutInflater.from(context);
        this.visibleThreshold = 10;
        this.handleLoadMore();
    }

    private void handleLoadMore() {
        if (this.recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).
                            findLastVisibleItemPosition();
                    if (!isLoading
                            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (!isFirstTime) { // not load more at the first time
                            loadMore();
                        }
                    }
                    isFirstTime = false;
                }
            });
        }
    }

    private void loadMore() {
        isLoading = true;
        // add progress bar
        itemList.add(new Item(PROGRESS, null, null));
        notifyItemInserted(totalItemCount - 1);
        // create a new thread to load more
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // remove progress item
                removeProgressBar();
                // add more child item
                for (int i = 0; i < 10; i++){
                    itemList.add(new Item(CHILD, "Philosopher BTW", "Male"));
                }
                Toast.makeText(context, "total item count: " + totalItemCount, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged(); // update adapter
                isLoading = false;
            }
        }, 1000); // show progress bar with 1 second
    }

    private void removeProgressBar() {
        itemList.remove(totalItemCount - 1);
        notifyItemRemoved(totalItemCount - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CHILD:
                view = inflater.inflate(R.layout.item_people, parent, false);
                final ChildViewHolder child = new ChildViewHolder(view);
                return child;
            case PROGRESS:
                view = inflater.inflate(R.layout.item_progress_bar, parent, false);
                final ProgressViewHolder progress = new ProgressViewHolder(view);
                return progress;
            default:
                break;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = itemList.get(position);
        if (item != null) {
            switch (item.getType()) {
                case CHILD:
                    ChildViewHolder child = (ChildViewHolder) holder;
                    child.getTxtName().setText(item.getName());
                    child.getTxtGender().setText(item.getGender());
                    break;
                case PROGRESS:
                    ProgressViewHolder progress = (ProgressViewHolder) holder;
                    progress.getProgressBar().setIndeterminate(true);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    public static class Item {
        private int type; // child or progress bar
        private String name;
        private String gender;

        public Item() {
        }

        public Item(int type, String name, String gender) {
            this.type = type;
            this.name = name;
            this.gender = gender;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtGender;

        private Item item;

        public ChildViewHolder(View v) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txt_name);
            txtGender = (TextView) v.findViewById(R.id.txt_gender);
        }

        public TextView getTxtName() {
            return txtName;
        }

        public void setTxtName(TextView txtName) {
            this.txtName = txtName;
        }

        public TextView getTxtGender() {
            return txtGender;
        }

        public void setTxtGender(TextView txtGender) {
            this.txtGender = txtGender;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public void setProgressBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }
    }

}
