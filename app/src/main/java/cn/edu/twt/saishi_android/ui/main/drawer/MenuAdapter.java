package cn.edu.twt.saishi_android.ui.main.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.MenuModel;
import cn.edu.twt.saishi_android.ui.common.OnItemClickListener;

/**
 * Created by clifton on 16-3-17.
 */
public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<MenuModel> list;
    private OnItemClickListener onItemClickListener;

    public MenuAdapter(Context context, List<MenuModel> list, OnItemClickListener onItemClickListener) {
        super();
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(parent, position);
            }
        };

        ViewHold hold;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.menu_item, null);
            hold = new ViewHold(convertView);
            convertView.setTag(hold);
        }else {
            hold = (ViewHold) convertView.getTag();
        }

        hold.imageViewIcon.setImageResource(list.get(position).getImageViewIcon());
        hold.imageViewGo.setImageResource(list.get(position).getImageViewGo());
        hold.textView.setText(list.get(position).getText());

        ((RelativeLayout)hold.imageViewGo.getParent()).setOnClickListener(onClickListener);
        return convertView;
    }

    public static class ViewHold {
        @Bind(R.id.nav_item_icon)
        ImageView imageViewIcon;
        @Bind(R.id.nav_item_go)
        ImageView imageViewGo;
        @Bind(R.id.nav_item_text)
        TextView textView;

        public ViewHold(View itemView){
            ButterKnife.bind(this, itemView);
        }

    }
}
