package xyz.doikki.dkplayer.fragment.main;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.adapter.ListPagerAdapter;
import xyz.doikki.dkplayer.fragment.BaseFragment;
import xyz.doikki.dkplayer.util.Tag;

/**
 * 注意：RecyclerView demo 我采用继承的方式实现，
 * ${@link xyz.doikki.dkplayer.fragment.list.RecyclerViewPortraitFragment} 我甚至使用了三重继承😂，
 * 实际开发中可以不需要这样。
 * 我这样做仅仅只为代码复用，方便维护
 */
public class ListFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {
        super.initView();

        ViewPager viewPager = findViewById(R.id.view_pager);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.str_list_view));
        titles.add(getString(R.string.str_recycler_view));
        titles.add(getString(R.string.str_auto_play_recycler_view));
        titles.add(getString(R.string.str_tiktok));
        titles.add(getString(R.string.str_seamless_play));
        titles.add(getString(R.string.str_portrait_when_fullscreen));

        viewPager.setAdapter(new ListPagerAdapter(getChildFragmentManager(), titles));

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getVideoViewManager().releaseByTag(Tag.LIST);
        getVideoViewManager().releaseByTag(Tag.SEAMLESS);
    }
}
