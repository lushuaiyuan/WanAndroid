//package com.lsy.lib_base.utils;
//
//import android.content.Context;
//import android.util.Log;
//import android.util.SparseArray;
//
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.lsy.lib_base.R;
//
//
///**
// * @author maoqitian
// * @Description: BottomNavigationView fragment 切换帮助类
// * @date 2019/5/7 0007 10:58
// */
//public class NavHelper<T> {
//
//    private final SparseArray<Tab<T>> tabs = new SparseArray<Tab<T>>();
//
//    private final SparseArray<Fragment> tabFragments = new SparseArray<Fragment>();
//
//    private final Context mContext;
//    private final int containerId;
//    private final FragmentManager fragmentManager;
//    private final OnTabChangeListener<T> mListener;
//
//    private Tab<T> currentTab;
//
//    private Fragment currentFragment;
//
//    public NavHelper(Context mContext, int containerId, FragmentManager fragmentManager, OnTabChangeListener<T> mListener) {
//        this.mContext = mContext;
//        this.containerId = containerId;
//        this.fragmentManager = fragmentManager;
//        this.mListener = mListener;
//    }
//
//    /**
//     * 添加tab
//     *
//     * @param menuId
//     * @param tab
//     * @return
//     */
//    public NavHelper<T> add(int menuId, Tab<T> tab) {
//        tabs.put(menuId, tab);
//        return this;
//    }
//
//    /**
//     * 添加 fragment ab
//     *
//     * @param menuId
//     * @param fragment
//     * @return
//     */
//    public NavHelper<T> add(int menuId, Fragment fragment) {
//        tabFragments.put(menuId, fragment);
//        return this;
//    }
//
//    /**
//     * 获取当前Tab
//     *
//     * @return
//     */
//    public Tab<T> getCurrentTab() {
//        return currentTab;
//    }
//
//    private int lastIndexId = R.id.tab_main;
//
//    public boolean performClickMenuFragment(int menuId) {
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        Fragment currentFragment = tabFragments.get(menuId);
//        Fragment lastFragment = tabFragments.get(lastIndexId);
//        lastIndexId = menuId;
//        ft.hide(lastFragment);
//        if (!currentFragment.isAdded()) {
//            fragmentManager.beginTransaction().remove(currentFragment).commit();
//            ft.add(containerId, currentFragment);
//        }
//        ft.show(currentFragment);
//        ft.commitAllowingStateLoss();
//        return true;
//    }
//
//    /**
//     * 执行点击菜单的操作
//     * 如果点击了 nav_home 并且当前显示的是首页模块 则不切换
//     * 如果是当前 收藏 点击了nav_home 切换到 home
//     *
//     * @param menuId
//     * @return
//     */
//    public boolean performClickMenu(int menuId) {
//        Tab<T> tab = tabs.get(menuId);
//        if (R.id.nav_home == menuId && currentTab != null) {
//            String name = (String) currentTab.extra;
//            if ("首页".equals(name) || "体系".equals(name) || "公众号".equals(name) || "导航".equals(name) || "项目".equals(name)) {
//                //如果点击了 nav_home 并且当前显示的是首页模块 则不切换
//                return true;
//            } else if ("收藏".equals(name) && tab != null) {
//                //如果是当前是收藏 设置 常用网站 等  点击了nav_home 切换到 home
//                doSelect(tab);
//                return true;
//            }
//        } else if (tab != null) {
//            //正常切换逻辑
//            doSelect(tab);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 进行tab的选择操作
//     *
//     * @param tab
//     */
//    private void doSelect(Tab<T> tab) {
//        Tab<T> oldTab = null;
//        if (currentTab != null) {
//            oldTab = currentTab;
//            if (oldTab == tab) {
//                //如果是当前tab点击的tab，不做任何操作或者刷新
//                notifyReselect(tab);
//                return;
//            }
//        }
//
//        currentTab = tab;
//        doTabChanged(currentTab, oldTab);
//    }
//
//    /**
//     * Tab切换方法
//     *
//     * @param newTab
//     * @param oldTab
//     */
//    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//
//        if (oldTab != null) {
//            if (oldTab.fragment != null) {
//                //从界面移除，但在Fragment的缓存中
//                ft.detach(oldTab.fragment);
//            }
//        }
//
//        if (newTab != null) {
//            if (newTab.fragment == null) {
//                //首次新建并缓存
//                Fragment fragment = Fragment.instantiate(mContext, newTab.clx.getName(), null);
//                newTab.fragment = fragment;
//                ft.add(containerId, fragment, newTab.clx.getName());
//            } else {
//                ft.attach(newTab.fragment);
//            }
//            /*Log.e("毛麒添","newTab.clx.getName() : "+newTab.clx.getName());
//            ft.addToBackStack(newTab.mTag);*/
//        }
//        ft.commit();
//        notifySelect(newTab, oldTab);
//    }
//
//    /**
//     * 选择通知回调
//     *
//     * @param newTab
//     * @param oldTab
//     */
//    private void notifySelect(Tab<T> newTab, Tab<T> oldTab) {
//        if (mListener != null) {
//            mListener.onTabChange(newTab, oldTab);
//        }
//
//    }
//
//    private void notifyReselect(Tab<T> tab) {
//        //TODO 刷新
//    }
//
//    /**
//     * 对应的 tab 对象
//     *
//     * @param <T>
//     */
//    public static class Tab<T> {
//        public Tab(Class<?> clx, T extra, String tag) {
//            this.clx = clx;
//            this.extra = extra;
//            this.mTag = tag;
//        }
//
//        public Class<?> clx;
//        public T extra;
//        String mTag;
//        //内部缓存对应的Fragment
//        private Fragment fragment;
//
//    }
//
//    /**
//     * 事件处理回调接口
//     *
//     * @param <T>
//     */
//    public interface OnTabChangeListener<T> {
//        void onTabChange(Tab<T> newTab, Tab<T> oldTab);
//    }
//}
