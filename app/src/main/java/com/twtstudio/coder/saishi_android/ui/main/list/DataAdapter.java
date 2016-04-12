package com.twtstudio.coder.saishi_android.ui.main.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.DataItem;
import com.twtstudio.coder.saishi_android.support.IgnoreCaseComparator;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.StringUtils;
import com.twtstudio.coder.saishi_android.ui.common.ImageHelper;
import com.twtstudio.coder.saishi_android.ui.common.OnItemClickListener;

/**
 * Created by clifton on 16-2-27.
 */
public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final static String LOG_TAG = DataAdapter.class.getSimpleName();

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_UP_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;

    private Context _context;
    private OnItemClickListener _onItemClicked;

    private ArrayList<DataItem> _DataSet = new ArrayList<>();
    IgnoreCaseComparator icc = new IgnoreCaseComparator();
    private boolean _useFooter;

    public DataAdapter(Context context, OnItemClickListener onItemClickListener){
        _context = context;
        _onItemClicked = onItemClickListener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_data_item_title)
        TextView _tvTitle;
        @Bind(R.id.tv_data_item_time)
        TextView _tvTime;
        @Bind(R.id.tv_data_item_pv)
        TextView _tvPv;
        @Bind(R.id.iv_data_item_icon)
        ImageView _ivIcon;


        public ItemHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public static class ItemUpHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_data_item_title_up)
        TextView _tvTitle;
        @Bind(R.id.tv_data_item_pv_up)
        TextView _tvPv;
        @Bind(R.id.tv_data_item_time_up)
        TextView _tvTime;
        @Bind(R.id.iv_data_item_icon_up)
        ImageView _ivIcon;


        public ItemUpHolder(View itemView){
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
        } else {
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more, parent, false);
            viewHolder = new FooterHolder(view);
        }

        return viewHolder;
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
            LogHelper.e(LOG_TAG, "小item提供");
            DataItem dataItem = _DataSet.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;
            if(!(dataItem == null)) {
                itemHolder._ivIcon.setVisibility(View.GONE);
                itemHolder._tvTitle.setText(dataItem.title);
                itemHolder._tvPv.setText("阅读数:" + dataItem.fwl);
                itemHolder._tvTime.setText(StringUtils.cutString(dataItem.createtime, 2));
                if(dataItem.url != null) {
                    ImageHelper.getImageLoder().displayImage(
                            ApiClient.getBaseUrl() + dataItem.url,
                            itemHolder._ivIcon, ImageHelper.getDisplayImageOptions());
                }else {
                    itemHolder._ivIcon.setImageResource(R.mipmap.ic_placeholder);
                }
                itemHolder._ivIcon.setVisibility(View.VISIBLE);

                ((CardView)(itemHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
            }
        } else if (getItemViewType(position) == ITEM_VIEW_TYPE_UP_ITEM){
            LogHelper.e(LOG_TAG, "大item提供");
            DataItem dataItem = _DataSet.get(position);
            ItemUpHolder itemUpHolder = (ItemUpHolder) holder;

            if(!(dataItem == null)) {
                itemUpHolder._ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                itemUpHolder._ivIcon.setImageResource(R.mipmap.placeholder_up);
//                itemUpHolder._ivIcon.requestLayout();
//                int width = itemUpHolder._ivIcon.getWidth();
//                itemUpHolder._ivIcon.getLayoutParams().width = width;
//                itemUpHolder._ivIcon.getLayoutParams().height = width / 2;
                itemUpHolder._ivIcon.setAdjustViewBounds(true);
                itemUpHolder._tvTitle.setText(dataItem.title);
                itemUpHolder._tvPv.setText("阅读数:" + dataItem.fwl);
                itemUpHolder._tvTime.setText(StringUtils.cutString(dataItem.createtime, 2));
                if(dataItem.url != null) {
                    ImageHelper.getImageLoder().displayImage(
                            ApiClient.getBaseUrl() + dataItem.url,
                            itemUpHolder._ivIcon, ImageHelper.getDisplayImageOptions());
                }

                ((CardView) (itemUpHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
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
        Collections.sort(_DataSet, icc);//排序
        selectItem();
        notifyDataSetChanged();
    }

    public void addDate(List<DataItem> items){
        _DataSet.addAll(items);
        if (_DataSet.get(0) == null){
            _DataSet.remove(0);
        }
        Collections.sort(_DataSet, icc);//排序
        selectItem();
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }

    //将置顶条目置顶
    private void selectItem(){
        if(!(_DataSet.size() == 0)){
            for(int j = 0; j < _DataSet.size(); j++){
                DataItem everyItem = _DataSet.get(j);
                if(!(everyItem.isup.equals("0"))){
                    _DataSet.add(0, _DataSet.get(j));
                    _DataSet.remove(j);
                    break;
                }
            }
        }
    }

}
