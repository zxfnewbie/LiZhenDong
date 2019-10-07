package com.test1.calculator.evaluator.base;


import com.test1.calculator.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Keeps track of the application wide number base, and manages the IDs of views to disable
 * when changing base.
 */
public class   NumberBaseManager {
    private Base mBase;
    private Map<Base, Set<Integer>> mDisabledViewIds;
    private Set<Integer> mBasicViewIds;
    private Set<Integer> mHexViewIds;
    private Set<Integer> mOctViewIds;

    public NumberBaseManager(Base base) {
        mBase = base;
        List<Integer> hexList = Arrays.asList(R.id.A, R.id.B, R.id.C, R.id.D, R.id.E, R.id.F);
        List<Integer> binaryList = Arrays.asList(
                R.id.digit2, R.id.digit3, R.id.digit4,
                R.id.digit5, R.id.digit6,
                R.id.digit7,
                R.id.digit8,
                R.id.digit9);

        List<Integer> octalList = Arrays.asList(R.id.digit8, R.id.digit9);
        Set<Integer> disabledForBinary = new HashSet<>(binaryList);
        disabledForBinary.addAll(hexList);

        Set<Integer> disableForOctal = new HashSet<>(octalList);
        disableForOctal.addAll(hexList);

        mDisabledViewIds = new HashMap<>();
        mDisabledViewIds.put(Base.DECIMAL, new HashSet<>(hexList));
        mDisabledViewIds.put(Base.BINARY, disabledForBinary);
        mDisabledViewIds.put(Base.HEXADECIMAL, new HashSet<Integer>());
        mDisabledViewIds.put(Base.OCTAL, disableForOctal);

        mBasicViewIds = new HashSet<>();
        mBasicViewIds.addAll(binaryList);

        mHexViewIds = new HashSet<>();
        mHexViewIds.addAll(hexList);

        mOctViewIds = new HashSet<>();
        mOctViewIds.addAll(octalList);
        // 设置默认基数
        setNumberBase(mBase);
    }

    public void setNumberBase(Base base) {
        mBase = base;
    }


    /**
     * @返回由启用/禁用列表管理的视图资源ID集
     */
    public Set<Integer> getViewIds() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.addAll(mBasicViewIds);
        set.addAll(mHexViewIds);
        set.addAll(mOctViewIds);
        return set;
    }

    /**
     * 如果基于当前基础禁用给定视图，则返回true
     *
     * @param viewResId
     * @return
     */
    public boolean isViewDisabled(int viewResId) {
        Set<Integer> disabledSet = mDisabledViewIds.get(mBase);
        return disabledSet.contains(viewResId);
    }
}
