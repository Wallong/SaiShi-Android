package cn.edu.twt.saishi_android.ui.main.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.DataItem;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.StringUtils;
import cn.edu.twt.saishi_android.ui.common.OnItemClickListener;

/**
 * Created by clifton on 16-2-27.
 */
public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final static String LOG_TAG = DataAdapter.class.getSimpleName();

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_UP_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_BIG_ITEM = 2;
    private static final int ITEM_VIEW_TYPE_FOOTER = 3;

    private static final String TONGZHI = "tongzhi";
    private static final String YIJIAN = "yijian";
    private static final String BIAOZHUN = "biaozhun";
    private static final String DONGTAI = "dongtai";
    private static final String HUIWU = "huiwu";

    private Context _context;
    private OnItemClickListener _onItemClicked;

    private ArrayList<DataItem> _DataSet = new ArrayList<>();
    private TreeSet _dataBigSet = new TreeSet();
    private boolean _useFooter;

    private String eachTime;
    private String anotherTime;

    public DataAdapter(Context context, OnItemClickListener onItemClickListener){
        _context = context;
        _onItemClicked = onItemClickListener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_data_item_type)
        TextView _tvType;
        @Bind(R.id.tv_data_item_title)
        TextView _tvTitle;
        @Bind(R.id.tv_data_item_subtitle)
        TextView _tvSubtitle;
        @Bind(R.id.tv_data_item_time)
        TextView _tvTime;


        public ItemHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class ItemUpHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_data_item_type_up)
        TextView _tvType;
        @Bind(R.id.tv_data_item_title_up)
        TextView _tvTitle;
        @Bind(R.id.tv_data_item_subtitle_up)
        TextView _tvSubtitle;
        @Bind(R.id.tv_time)
        TextView _tvTopic;


        public ItemUpHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class ItemBigHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_data_item_type)
        TextView _tvType;
        @Bind(R.id.tv_data_item_title)
        TextView _tvTitle;
        @Bind(R.id.tv_data_item_subtitle)
        TextView _tvSubtitle;
        @Bind(R.id.tv_data_item_time)
        TextView _tvTime;
        @Bind(R.id.tv_time)
        TextView _tvTopic;


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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

        if(viewType == ITEM_VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.data_list_item, parent, false);
            viewHolder = new ItemHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_UP_ITEM){
            View view = inflater.inflate(R.layout.data_up_list_item,parent,false);
            viewHolder = new ItemUpHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_BIG_ITEM){
            View view = inflater.inflate(R.layout.data_big_list_item,parent,false);
            viewHolder = new ItemBigHolder(view);
        }else {
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more, parent, false);
            viewHolder = new FooterHolder(view);
        }

        return viewHolder;
    }

    private void selectItem(){
        if(!(_DataSet.size() == 0)){
            _dataBigSet.clear();
            for(int j = 0; j < _DataSet.size(); j++){
                if(! (j==(_DataSet.size()-1))){
                    anotherTime = StringUtils.cutString(_DataSet.get(j).createtime, 1);
                    eachTime = StringUtils.cutString(_DataSet.get(j + 1).createtime, 1);
                    if(!(eachTime.equals(anotherTime)) && !(_dataBigSet.contains(j))){
                        _dataBigSet.add(j);
                    }
                }

                DataItem everyItem = _DataSet.get(j);
                if(!(everyItem.isup.equals("0"))){
                    _DataSet.add(0, _DataSet.get(j));
                    _DataSet.remove(j);
                    break;
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        View.OnClickListener onClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                _onItemClicked.onItemClicked(v, position);
            }
        };


        if(getItemViewType(position) == ITEM_VIEW_TYPE_ITEM) {
            DataItem dataItem = _DataSet.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;

            if(!(dataItem == null)) {
                itemHolder._tvTitle.setText(dataItem.title);
                itemHolder._tvSubtitle.setText(dataItem.subtitle);
                itemHolder._tvTime.setText(StringUtils.cutString(dataItem.createtime, 2));
                itemHolder._tvType.setText("消息");
                if (dataItem.type.equals(TONGZHI)) {
                    itemHolder._tvType.setText("通知");
                } else if (dataItem.type.equals(YIJIAN)) {
                    itemHolder._tvType.setText("意见");
                } else if (dataItem.type.equals(BIAOZHUN)) {
                    itemHolder._tvType.setText("标准");
                } else if (dataItem.type.equals(DONGTAI)) {
                    itemHolder._tvType.setText("动态");
                } else if (dataItem.type.equals(HUIWU)) {
                    itemHolder._tvType.setText("会务");
                }

                ((CardView)(itemHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
            }
        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_UP_ITEM){
            DataItem dataItem = _DataSet.get(position);
            ItemUpHolder itemUpHolder = (ItemUpHolder) holder;


            if(!(dataItem == null)) {
                itemUpHolder._tvTitle.setText(dataItem.title);
                itemUpHolder._tvSubtitle.setText(dataItem.subtitle);
                itemUpHolder._tvTopic.setText(StringUtils.cutString(dataItem.createtime, 0));
                itemUpHolder._tvType.setText("消息");
                if (dataItem.type.equals(TONGZHI)) {
                    itemUpHolder._tvType.setText("通知");
                } else if (dataItem.type.equals(YIJIAN)) {
                    itemUpHolder._tvType.setText("意见");
                } else if (dataItem.type.equals(BIAOZHUN)) {
                    itemUpHolder._tvType.setText("标准");
                } else if (dataItem.type.equals(DONGTAI)) {
                    itemUpHolder._tvType.setText("动态");
                } else if (dataItem.type.equals(HUIWU)) {
                    itemUpHolder._tvType.setText("会务");
                }

                ((CardView) (itemUpHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
            }
        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_BIG_ITEM){
            DataItem dataItem = _DataSet.get(position);
            ItemBigHolder itemBigHolder = (ItemBigHolder) holder;


            if(!(dataItem == null)) {
                itemBigHolder._tvTitle.setText(dataItem.title);
                itemBigHolder._tvSubtitle.setText(dataItem.subtitle);
                itemBigHolder._tvTopic.setText(StringUtils.cutString(dataItem.createtime, 1));
                itemBigHolder._tvTime.setText(StringUtils.cutString(dataItem.createtime, 2));
                itemBigHolder._tvType.setText("消息");
                if (dataItem.type.equals(TONGZHI)) {
                    itemBigHolder._tvType.setText("通知");
                } else if (dataItem.type.equals(YIJIAN)) {
                    itemBigHolder._tvType.setText("意见");
                } else if (dataItem.type.equals(BIAOZHUN)) {
                    itemBigHolder._tvType.setText("标准");
                } else if (dataItem.type.equals(DONGTAI)) {
                    itemBigHolder._tvType.setText("动态");
                } else if (dataItem.type.equals(HUIWU)) {
                    itemBigHolder._tvType.setText("会务");
                }

                ((CardView) (itemBigHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = _DataSet.size();
        return _useFooter? ++count:count;
    }

    @Override
    public int getItemViewType(int position) {
        if(!(position < _DataSet.size())){
            return ITEM_VIEW_TYPE_FOOTER;
        } else if (position == 0) {
            return ITEM_VIEW_TYPE_UP_ITEM;
        } else if (position == 1 || _dataBigSet.contains(position)) {
            return ITEM_VIEW_TYPE_BIG_ITEM;
        } else {
            return ITEM_VIEW_TYPE_ITEM;
        }

    }

    public DataItem getItem(int position){
        return _DataSet.get(position);
    }

    public void updateData(List<DataItem> items){
        _DataSet.clear();
        _DataSet.addAll(items);
        if (_DataSet.get(0) == null){
            _DataSet.remove(0);
        }

        selectItem();

        notifyDataSetChanged();
    }

    public void addDate(List<DataItem> items){
        _DataSet.addAll(items);
        if (_DataSet.get(0) == null){
            _DataSet.remove(0);
        }

        selectItem();
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }

}
