package com.example.jason.ichinese.CustomCtrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jason.ichinese.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ImageTextAdapter extends BaseAdapter {
    private Context mContext;
    private Map<String, ImageView> itemMap = new HashMap<>();
    private ImageLoader mImageLoader = ImageLoader.getInstance();

    public ImageTextAdapter(Context context) {
        this.mContext = context;
        mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }


    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemImg = convertView.findViewById(R.id.img);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        // 这里只是模拟，实际开发可能需要加载网络图片，可以使用ImageLoader这样的图片加载框架来异步加载图片
        mImageLoader.displayImage("http://39.96.51.230/test/dlrb.jpg" , viewHolder.itemImg);

        return convertView;
    }


    class ViewHolder {
        ImageView itemImg;
    }

}
