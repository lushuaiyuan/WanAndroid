package com.lsy.wanandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lsy.lib_base.base.BaseActivity;
import com.lsy.module_home.view.HomeFragment;
import com.lsy.module_navigation.NavigationFragment;
import com.lsy.module_official_accounts.OfficialAccountsFragment;
import com.lsy.module_project.ProjectFragment;
import com.lsy.module_system.SystemFragment;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;

    private static final String TAG = "MainActivity";

    private String[] tabText = {"首页", "体系", "公众号", "导航", "项目"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.ic_home_normal, R.mipmap.ic_book_normal, R.mipmap.ic_wechat_normal, R.mipmap.ic_navigation_normal, R.mipmap.ic_project_normal};
    //选中时icon
    private int[] selectIcon = {R.mipmap.ic_home_selected, R.mipmap.ic_book_selected, R.mipmap.ic_wechat_selected, R.mipmap.ic_navigation_selected, R.mipmap.ic_project_selected};

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        initBottomBar();
    }

    private void initBottomBar() {
        fragments.add(new HomeFragment());
        fragments.add(new SystemFragment());
        fragments.add(new OfficialAccountsFragment());
        fragments.add(new NavigationFragment());
        fragments.add(new ProjectFragment());
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .lineHeight(1)
                .normalTextColor(ContextCompat.getColor(this,R.color.tab_normal))   //Tab未选中时字体颜色
                .selectTextColor(ContextCompat.getColor(this,R.color.tab_selected))   //Tab选中时字体颜色
                .lineColor(ContextCompat.getColor(this, R.color.bar_divider))
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        Log.e(TAG, "onTabClickEvent: position-----" + position);
                        return false;
                    }
                })
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
//                .navigationHeight(50)
                .anim(Anim.ZoomIn)

                .build();
        // ((WeiboActivity) getActivity()).getNavigationBar().selectTab(1); //指定跳转位置
        //TODO 设置小红点和消息
//        navigationBar.setMsgPointCount(0, 109);
//        navigationBar.setMsgPointCount(3, 5);

//        navigationBar.setHintPoint(1, true);

        //移除消息和小红点
//        navigationBar.clearHintPoint(position);//移除指定位置的小红点
//        navigationBar.clearAllHintPoint();//移除所有的小红点
//        navigationBar.clearMsgPoint(position);//移除指定位置的消息
//        navigationBar.clearAllMsgPoint();//移除所有的消息
    }
}
