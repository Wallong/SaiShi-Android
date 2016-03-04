package cn.edu.twt.saishi_android.ui.file;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.FileInfo;
import cn.edu.twt.saishi_android.bean.FileUrl;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.support.StringUtils;
import cn.edu.twt.saishi_android.ui.common.OnItemClickListener;

/**
 * Created by clifton on 16-2-28.
 */
public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static String LOG_TAG = FileAdapter.class.getSimpleName();

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_BIG_ITEM = 1;
    private static final int ITEM_VIEW_TYPE_FOOTER = 2;

    private static final String TONGZHI = "tongzhi";
    private static final String YIJIAN = "yijian";
    private static final String BIAOZHUN = "biaozhun";

    private Context _context;
    private OnItemClickListener _onItemClicked;

    public static ArrayList<FileInfo> _FileSet = new ArrayList<>();
    private TreeSet _fileBigSet = new TreeSet();
    private String eachTime;
    private String anotherTime;
    private boolean _useFooter;

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
        @Bind(R.id.iv_download)
        ImageView _ivDownload;

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
        @Bind(R.id.iv_download)
        ImageView _ivDownload;
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
            for(int j = 0; j < _FileSet.size(); j++){
                if(! (j==(_FileSet.size()-1))){
                    anotherTime = StringUtils.cutString(_FileSet.get(j).createtime, 1);
                    eachTime = StringUtils.cutString(_FileSet.get(j + 1).createtime, 1);
                    if(!(eachTime.equals(anotherTime)) && !(_fileBigSet.contains(j))){
                        _fileBigSet.add(j);
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

            if(!(fileInfo.tag == null)){
                itemHolder._ivDownload.setImageResource(R.drawable.ic_downloaded);
            }

            if(fileInfo.title.contains("《")){
                itemHolder._tvTitle.setText(fileInfo.title);
            }else{
                itemHolder._tvTitle.setText("《" + fileInfo.title + "》");
            }
            itemHolder._tvTime.setText(StringUtils.cutString(fileInfo.createtime, 2));
            itemHolder._tvType.setText("文件");
            if(fileInfo.type.equals(TONGZHI)){
                itemHolder._tvType.setText("通知");
            } else if (fileInfo.type.equals(YIJIAN)){
                itemHolder._tvType.setText("意见");
            } else if (fileInfo.type.equals(BIAOZHUN)){
                itemHolder._tvType.setText("标准");
            }

            ((CardView)(itemHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
        }else if(getItemViewType(position) == ITEM_VIEW_TYPE_BIG_ITEM){
            FileInfo fileInfo = _FileSet.get(position);
            ItemBigHolder itemBigHolder = (ItemBigHolder) holder;
            if(!(fileInfo.tag == null)){
                itemBigHolder._ivDownload.setImageResource(R.drawable.ic_downloaded);
            }

            if(fileInfo.title.contains("《")){
                itemBigHolder._tvTitle.setText(fileInfo.title);
            }else{
                itemBigHolder._tvTitle.setText("《" + fileInfo.title + "》");
            }
            itemBigHolder._tvTime.setText(StringUtils.cutString(fileInfo.createtime, 2));
            itemBigHolder._tvType.setText("文件");
            itemBigHolder._tvFileTime.setText(StringUtils.cutString(fileInfo.createtime, 1));
            if(fileInfo.type.equals(TONGZHI)){
                itemBigHolder._tvType.setText("通知");
            } else if (fileInfo.type.equals(YIJIAN)){
                itemBigHolder._tvType.setText("意见");
            } else if (fileInfo.type.equals(BIAOZHUN)){
                itemBigHolder._tvType.setText("标准");
            }

            ((CardView)(itemBigHolder._tvTitle.getParent()).getParent()).setOnClickListener(onClickListener);
        }

    }

    private void markFileInfo(List<FileInfo> items) {
        if (PrefUtils.getPrefFileUrlJson() != null && PrefUtils.getPrefFileUrlJson().contains("[")) {
            Gson gson = new Gson();
            FileUrl[] urls = gson.fromJson(PrefUtils.getPrefFileUrlJson(), FileUrl[].class);
            List<FileUrl> fileList = new ArrayList<>();
            Collections.addAll(fileList, urls);
            for (int i = 0; i < fileList.size(); i++) {
                for (int j = 0; j < items.size(); j++) {
                    if (fileList.get(i).id.equals(items.get(j).file)) {
                        items.get(j).setTag("1");
                        LogHelper.e(LOG_TAG, "这里是标记每一个元素1");
                    }
                    LogHelper.e(LOG_TAG, "这里是标记每一个元素2");
                }
                LogHelper.e(LOG_TAG, "这里是标记每一个元素3");
            }
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
        markFileInfo(items);
        ArrayList <FileInfo> list = new ArrayList<>();
        list.addAll(items);
        _FileSet.clear();
        _FileSet.addAll(list);
        selectItem();
        notifyDataSetChanged();

    }

    public void addFile(List<FileInfo> items){
        markFileInfo(items);
        _FileSet.addAll(items);
        selectItem();
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}
