package xyz.doikki.dkplayer.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.bean.TiktokBean;
import xyz.doikki.dkplayer.util.cache.PreloadManager;
import xyz.doikki.dkplayer.widget.component.TikTokView;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
public class Tiktok2Adapter extends PagerAdapter {

    /**
     * View缓存池，从ViewPager中移除的item将会存到这里面，用来复用
     */
    private final List<View> mViewPool = new ArrayList<>();

    /**
     * 数据源
     */
    private final List<TiktokBean> mVideoBeans;

    public Tiktok2Adapter(List<TiktokBean> videoBeans) {
        this.mVideoBeans = videoBeans;
    }

    @Override
    public int getCount() {
        return mVideoBeans == null ? 0 : mVideoBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Context context = container.getContext();
        View view = null;
        if (!mViewPool.isEmpty()) {//取第一个进行复用
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                view = mViewPool.getFirst();
                mViewPool.removeFirst();
            } else {
                view = mViewPool.get(0);
                mViewPool.remove(0);
            }
        }

        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_tik_tok, container, false);
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        TiktokBean item = mVideoBeans.get(position);
        //开始预加载
        PreloadManager.getInstance(context).addPreloadTask(item.videoDownloadUrl, position);
        Glide.with(context)
                .load(item.coverImgUrl)
                .placeholder(android.R.color.white)
                .into(viewHolder.mThumb);
        viewHolder.mTitle.setText(item.title);
        viewHolder.mTitle.setOnClickListener(v -> Toast.makeText(context, "点击了标题", Toast.LENGTH_SHORT).show());
        viewHolder.mPosition = position;
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View itemView = (View) object;
        container.removeView(itemView);
        TiktokBean item = mVideoBeans.get(position);
        //取消预加载
        PreloadManager.getInstance(container.getContext()).removePreloadTask(item.videoDownloadUrl);
        //保存起来用来复用
        mViewPool.add(itemView);
    }

    /**
     * 借鉴ListView item复用方法
     */
    public static class ViewHolder {

        public int mPosition;
        public final TextView mTitle;//标题
        public final ImageView mThumb;//封面图
        public final TikTokView mTikTokView;
        public final FrameLayout mPlayerContainer;

        ViewHolder(View itemView) {
            mTikTokView = itemView.findViewById(R.id.tiktok_View);
            mTitle = mTikTokView.findViewById(R.id.tv_title);
            mThumb = mTikTokView.findViewById(R.id.iv_thumb);
            mPlayerContainer = itemView.findViewById(R.id.container);
            itemView.setTag(this);
        }
    }
}
