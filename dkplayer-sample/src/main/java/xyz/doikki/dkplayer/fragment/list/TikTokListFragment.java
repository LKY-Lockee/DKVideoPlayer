package xyz.doikki.dkplayer.fragment.list;

import android.widget.Button;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.adapter.TikTokListAdapter;
import xyz.doikki.dkplayer.bean.TiktokBean;
import xyz.doikki.dkplayer.fragment.BaseFragment;
import xyz.doikki.dkplayer.util.DataUtil;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
public class TikTokListFragment extends BaseFragment {

    private final List<TiktokBean> data = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TikTokListAdapter mAdapter;
    private Button mSwitchImpl;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tiktok_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView = findViewById(R.id.rv_tiktok);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mAdapter = new TikTokListAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        mSwitchImpl = findViewById(R.id.btn_switch_impl);
        PopupMenu menu = new PopupMenu(requireContext(), mSwitchImpl);
        menu.inflate(R.menu.tiktok_impl_menu);
        menu.setOnMenuItemClickListener(item -> {
            mAdapter.setImpl(item.getItemId());
            int itemId = item.getItemId();
            if (itemId == R.id.impl_recycler_view) {
                mSwitchImpl.setText("RecyclerView");
            } else if (itemId == R.id.impl_vertical_view_pager) {
                mSwitchImpl.setText("VerticalViewPager");
            } else if (itemId == R.id.impl_view_pager_2) {
                mSwitchImpl.setText("ViewPager2");
            }
            return false;
        });
        mSwitchImpl.setOnClickListener(v -> menu.show());

        //默认VerticalViewPager
        mAdapter.setImpl(R.id.impl_vertical_view_pager);
        mSwitchImpl.setText("VerticalViewPager");
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
        //模拟请求数据
        new Thread(() -> {
            List<TiktokBean> tiktokBeans = DataUtil.getTiktokDataFromAssets(requireActivity());
            int oldSize = data.size();
            data.addAll(tiktokBeans);
            mRecyclerView.post(() -> mAdapter.notifyItemRangeInserted(oldSize, tiktokBeans.size()));
        }).start();
    }
}
