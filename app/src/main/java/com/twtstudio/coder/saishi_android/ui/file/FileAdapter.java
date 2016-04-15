package com.twtstudio.coder.saishi_android.ui.file;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.bean.FileInfo;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.StringUtils;
import com.twtstudio.coder.saishi_android.ui.common.OnItemClickListener;

/**
 * Created by clifton on 16-2-28.
 */
public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static String LOG_TAG = FileAdapter.class.getSimpleName();

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_BIG_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;

    private Context _context;
    private OnItemClickListener _onItemClicked;

    public static ArrayList<FileInfo> _FileSet = new ArrayList<>();
    private TreeSet _fileBigSet = new TreeSet();
    private String eachTime;
    private String anotherTime;
    private boolean _useFooter;
    private int r;
    private int g;
    private int b;

    public FileAdapter(Context context, OnItemClickListener onItemClickListener){
        _context = context;
        _onItemClicked = onItemClickListener;

    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_file_item_type)
        TextView _tvType;
        @Bind(R.id.tv_file_item_title)
        TextView _tvTitle;
        @Bind(R.id.tv_file_item_time)
        TextView _tvTime;

        public ItemHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class ItemBigHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_file_item_type)
        TextView _tvType;
        @Bind(R.id.tv_file_item_title)
        TextView _tvTitle;
        @Bind(R.id.tv_file_item_time)
        TextView _tvTime;
        @Bind(R.id.tv_file_time)
        TextView _tvFileTime;


        public ItemBigHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.pb_footer_load_more)
        ProgressBar pbLoadMore;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void selectItem(){
        if(!(_FileSet.size() == 0)){
            _fileBigSet.clear();
            _fileBigSet.add(0);
            for(int j = 0; j < _FileSet.size(); j++){
                if(!(j==(_FileSet.size()-1))){
                    anotherTime = StringUtils.cutString(_FileSet.get(j).createtime, 1);
                    eachTime = StringUtils.cutString(_FileSet.get(j + 1).createtime, 1);
                    if(!(eachTime.equals(anotherTime)) && !(_fileBigSet.contains(j+1))){
                        _fileBigSet.add(j+1);
                    }
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

        if(viewType == ITEM_VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.file_list_item, parent, false);
            viewHolder = new ItemHolder(view);
        }else if(viewType == ITEM_VIEW_TYPE_BIG_ITEM){
            View view = inflater.inflate(R.layout.file_big_list_item, parent, false);
            viewHolder = new ItemBigHolder(view);
        } else {
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more, parent, false);
            viewHolder = new FooterHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        View.OnClickListener onClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                _onItemClicked.onItemClicked(v, position);
            }
        };

        if(getItemViewType(position) == ITEM_VIEW_TYPE_ITEM) {
            FileInfo fileInfo = _FileSet.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder._tvTitle.setText(fileInfo.title);
            itemHolder._tvTime.setText(StringUtils.cutString(fileInfo.createtime, 2));
            itemHolder._tvType.setText(fileInfo.tag);
            r = Integer.parseInt(fileInfo.r);
            g = Integer.parseInt(fileInfo.g);
            b = Integer.parseInt(fileInfo.b);
            itemHolder._tvType.setBackgroundColor(Color.parseColor("#"
                    + StringUtils.cutString(Integer.toHexString(r), 5)
                    + StringUtils.cutString(Integer.toHexString(g), 5)
                    + StringUtils.cutString(Integer.toHexString(b), 5)));

            ((CardView)(itemHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
        }else if(getItemViewType(position) == ITEM_VIEW_TYPE_BIG_ITEM){
            FileInfo fileInfo = _FileSet.get(position);
            ItemBigHolder itemBigHolder = (ItemBigHolder) holder;
            itemBigHolder._tvTitle.setText(fileInfo.title);
            itemBigHolder._tvTime.setText(StringUtils.cutString(fileInfo.createtime, 2));
            itemBigHolder._tvType.setText(fileInfo.tag);
            r = Integer.parseInt(fileInfo.r);
            g = Integer.parseInt(fileInfo.g);
            b = Integer.parseInt(fileInfo.b);
            LogHelper.e(LOG_TAG, "#"
                    + StringUtils.cutString(Integer.toHexString(r), 5)
                    + StringUtils.cutString(Integer.toHexString(g), 5)
                    + StringUtils.cutString(Integer.toHexString(b), 5));
            itemBigHolder._tvType.setBackgroundColor(Color.parseColor("#"
                    + StringUtils.cutString(Integer.toHexString(r), 5)
                    + StringUtils.cutString(Integer.toHexString(g), 5)
                    + StringUtils.cutString(Integer.toHexString(b), 5)));
            itemBigHolder._tvFileTime.setText(StringUtils.cutString(fileInfo.createtime, 1));

            ((CardView)(itemBigHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        int count = _FileSet.size();
        return _useFooter? ++count:count;
    }

    @Override
    public int getItemViewType(int position) {
        if(!(position < _FileSet.size())){
            return ITEM_VIEW_TYPE_FOOTER;
        }else if(position == 0 || _fileBigSet.contains(position)){
            return ITEM_VIEW_TYPE_BIG_ITEM;
        }else{
            return ITEM_VIEW_TYPE_ITEM;
        }
    }

    public FileInfo getItem(int position){
        return _FileSet.get(position);
    }

    public void updateFile(List<FileInfo> items){
        ArrayList <FileInfo> list = new ArrayList<>();
        list.addAll(items);
        _FileSet.clear();
        _FileSet.addAll(list);
        selectItem();
        notifyDataSetChanged();

    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}
