package com.jstudio.panionline.utility.uiUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom class for handing bottom tabs.
 */
public class BottomTab {
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList = new ArrayList<>(5);
    private int containerId;
    private int visibleIndex = 0;

    public BottomTab addFragmentActivity(FragmentActivity context) {
        fragmentManager = context.getSupportFragmentManager();
        return this;
    }

    public BottomTab addContainerId(int containerId) {
        this.containerId = containerId;
        return this;
    }

    public BottomTab addFragment(Fragment fragment) {
        fragmentList.add(fragment);
        return this;
    }

    public int selectedNavIndex() {
        return visibleIndex;
    }

    public BottomTab build() {
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentManager.beginTransaction().add(containerId, fragmentList.get(i)).commit();
            if (i != 0) {
                fragmentManager.beginTransaction().hide(fragmentList.get(i)).commit();
            }
        }

        return this;
    }

    public void show(int index) {
        if (index > -1 && index < fragmentList.size()) {
            for (int i = 0; i < fragmentList.size(); i++) {
                if (i != index) {
                    fragmentManager.beginTransaction().hide(fragmentList.get(i)).commit();
                } else {
                    visibleIndex = index;
                    fragmentManager.beginTransaction().show(fragmentList.get(i)).commit();
                }
            }
        }
    }

    public Fragment getChild(int index) {
        return fragmentList.get(index);
    }
}
