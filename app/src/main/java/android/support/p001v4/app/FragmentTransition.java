package android.support.p001v4.app;

import android.graphics.Rect;
import android.os.Build;
import android.support.p001v4.util.ArrayMap;
import android.support.p001v4.view.OneShotPreDrawListener;
import android.support.p001v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: android.support.v4.app.FragmentTransition */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 11, 10};
    private static final FragmentTransitionImpl PLATFORM_IMPL = (Build.VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null);
    private static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();

    private FragmentTransition() {
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static void startTransitions(FragmentManagerImpl fragmentManager, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, boolean isReordered) {
        if (fragmentManager.mCurState >= 1) {
            SparseArray<FragmentContainerTransition> transitioningFragments = new SparseArray<>();
            for (int i = startIndex; i < endIndex; i++) {
                BackStackRecord record = records.get(i);
                if (isRecordPop.get(i).booleanValue()) {
                    calculatePopFragments(record, transitioningFragments, isReordered);
                } else {
                    calculateFragments(record, transitioningFragments, isReordered);
                }
            }
            if (transitioningFragments.size() != 0) {
                View nonExistentView = new View(fragmentManager.mHost.getContext());
                int numContainers = transitioningFragments.size();
                for (int i2 = 0; i2 < numContainers; i2++) {
                    int containerId = transitioningFragments.keyAt(i2);
                    ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, records, isRecordPop, startIndex, endIndex);
                    FragmentContainerTransition containerTransition = (FragmentContainerTransition) transitioningFragments.valueAt(i2);
                    if (isReordered) {
                        configureTransitionsReordered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    } else {
                        configureTransitionsOrdered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int containerId, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        ArrayList<String> sources;
        ArrayList<String> targets;
        ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord record = records.get(recordNum);
            if (record.interactsWith(containerId)) {
                boolean isPop = isRecordPop.get(recordNum).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (isPop) {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    } else {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < numSharedElements; i++) {
                        String sourceName = (String) sources.get(i);
                        String targetName = (String) targets.get(i);
                        String previousTarget = nameOverrides.remove(targetName);
                        if (previousTarget != null) {
                            nameOverrides.put(sourceName, previousTarget);
                        } else {
                            nameOverrides.put(sourceName, targetName);
                        }
                    }
                }
            }
        }
        return nameOverrides;
    }

    /* JADX INFO: Multiple debug info for r8v1 java.lang.Object: [D('inIsPop' boolean), D('enterTransition' java.lang.Object)] */
    /* JADX WARN: Type inference failed for: r2v9, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsReordered(android.support.p001v4.app.FragmentManagerImpl r23, int r24, android.support.p001v4.app.FragmentTransition.FragmentContainerTransition r25, android.view.View r26, android.support.p001v4.util.ArrayMap<java.lang.String, java.lang.String> r27) {
        /*
            r0 = r23
            r10 = r25
            r11 = r26
            r1 = 0
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x001c
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            r12 = r24
            android.view.View r2 = r2.onFindViewById(r12)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r13 = r1
            goto L_0x001f
        L_0x001c:
            r12 = r24
            r13 = r1
        L_0x001f:
            if (r13 != 0) goto L_0x0022
            return
        L_0x0022:
            android.support.v4.app.Fragment r14 = r10.lastIn
            android.support.v4.app.Fragment r15 = r10.firstOut
            android.support.v4.app.FragmentTransitionImpl r9 = chooseImpl(r15, r14)
            if (r9 != 0) goto L_0x002d
            return
        L_0x002d:
            boolean r8 = r10.lastInIsPop
            boolean r7 = r10.firstOutIsPop
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r6 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r5 = r1
            java.lang.Object r4 = getEnterTransition(r9, r14, r8)
            java.lang.Object r3 = getExitTransition(r9, r15, r7)
            r1 = r9
            r2 = r13
            r16 = r3
            r3 = r26
            r17 = r4
            r4 = r27
            r18 = r5
            r5 = r25
            r19 = r6
            r6 = r18
            r20 = r7
            r7 = r19
            r21 = r8
            r8 = r17
            r0 = r9
            r9 = r16
            java.lang.Object r9 = configureSharedElementsReordered(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            if (r8 != 0) goto L_0x0070
            if (r9 != 0) goto L_0x0070
            r7 = r16
            if (r7 != 0) goto L_0x0072
            return
        L_0x0070:
            r7 = r16
        L_0x0072:
            r6 = r18
            java.util.ArrayList r5 = configureEnteringExitingViews(r0, r7, r15, r6, r11)
            r4 = r19
            java.util.ArrayList r3 = configureEnteringExitingViews(r0, r8, r14, r4, r11)
            r1 = 4
            setViewVisibility(r3, r1)
            r1 = r0
            r2 = r8
            r16 = r3
            r3 = r7
            r10 = r4
            r4 = r9
            r11 = r5
            r5 = r14
            r6 = r21
            java.lang.Object r6 = mergeTransitions(r1, r2, r3, r4, r5, r6)
            if (r6 == 0) goto L_0x00c7
            replaceHide(r0, r7, r15, r11)
            java.util.ArrayList r17 = r0.prepareSetNameOverridesReordered(r10)
            r1 = r0
            r2 = r6
            r3 = r8
            r4 = r16
            r5 = r7
            r12 = r6
            r6 = r11
            r19 = r7
            r7 = r9
            r22 = r8
            r8 = r10
            r1.scheduleRemoveTargets(r2, r3, r4, r5, r6, r7, r8)
            r0.beginDelayedTransition(r13, r12)
            r2 = r0
            r3 = r13
            r4 = r18
            r5 = r10
            r6 = r17
            r7 = r27
            r2.setNameOverridesReordered(r3, r4, r5, r6, r7)
            r1 = 0
            r2 = r16
            setViewVisibility(r2, r1)
            r1 = r18
            r0.swapSharedElementTargets(r9, r1, r10)
            goto L_0x00d0
        L_0x00c7:
            r12 = r6
            r19 = r7
            r22 = r8
            r2 = r16
            r1 = r18
        L_0x00d0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.app.FragmentTransition.configureTransitionsReordered(android.support.v4.app.FragmentManagerImpl, int, android.support.v4.app.FragmentTransition$FragmentContainerTransition, android.view.View, android.support.v4.util.ArrayMap):void");
    }

    private static void replaceHide(FragmentTransitionImpl impl, Object exitTransition, Fragment exitingFragment, final ArrayList<View> exitingViews) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            impl.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), exitingViews);
            OneShotPreDrawListener.add(exitingFragment.mContainer, new Runnable() {
                public void run() {
                    FragmentTransition.setViewVisibility(exitingViews, 4);
                }
            });
        }
    }

    /* JADX INFO: Multiple debug info for r25v1 java.util.ArrayList<android.view.View>: [D('exitingViews' java.util.ArrayList<android.view.View>), D('sharedElementsOut' java.util.ArrayList<android.view.View>)] */
    /* JADX WARN: Type inference failed for: r2v8, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsOrdered(android.support.p001v4.app.FragmentManagerImpl r30, int r31, android.support.p001v4.app.FragmentTransition.FragmentContainerTransition r32, android.view.View r33, android.support.p001v4.util.ArrayMap<java.lang.String, java.lang.String> r34) {
        /*
            r0 = r30
            r10 = r32
            r11 = r33
            r12 = r34
            r1 = 0
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x001e
            android.support.v4.app.FragmentContainer r2 = r0.mContainer
            r13 = r31
            android.view.View r2 = r2.onFindViewById(r13)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r14 = r1
            goto L_0x0021
        L_0x001e:
            r13 = r31
            r14 = r1
        L_0x0021:
            if (r14 != 0) goto L_0x0024
            return
        L_0x0024:
            android.support.v4.app.Fragment r15 = r10.lastIn
            android.support.v4.app.Fragment r9 = r10.firstOut
            android.support.v4.app.FragmentTransitionImpl r8 = chooseImpl(r9, r15)
            if (r8 != 0) goto L_0x002f
            return
        L_0x002f:
            boolean r7 = r10.lastInIsPop
            boolean r6 = r10.firstOutIsPop
            java.lang.Object r5 = getEnterTransition(r8, r15, r7)
            java.lang.Object r4 = getExitTransition(r8, r9, r6)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = r1
            r1 = r8
            r24 = r2
            r2 = r14
            r25 = r3
            r3 = r33
            r16 = r4
            r4 = r34
            r26 = r5
            r5 = r32
            r27 = r6
            r6 = r25
            r28 = r7
            r7 = r24
            r0 = r8
            r8 = r26
            r13 = r9
            r9 = r16
            java.lang.Object r29 = configureSharedElementsOrdered(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r9 = r26
            if (r9 != 0) goto L_0x0074
            if (r29 != 0) goto L_0x0074
            r1 = r16
            if (r1 != 0) goto L_0x0076
            return
        L_0x0074:
            r1 = r16
        L_0x0076:
            r8 = r25
            java.util.ArrayList r25 = configureEnteringExitingViews(r0, r1, r13, r8, r11)
            if (r25 == 0) goto L_0x0088
            boolean r2 = r25.isEmpty()
            if (r2 == 0) goto L_0x0085
            goto L_0x0088
        L_0x0085:
            r26 = r1
            goto L_0x008b
        L_0x0088:
            r1 = 0
            r26 = r1
        L_0x008b:
            r0.addTarget(r9, r11)
            boolean r6 = r10.lastInIsPop
            r1 = r0
            r2 = r9
            r3 = r26
            r4 = r29
            r5 = r15
            java.lang.Object r7 = mergeTransitions(r1, r2, r3, r4, r5, r6)
            if (r7 == 0) goto L_0x00d5
            java.util.ArrayList r19 = new java.util.ArrayList
            r19.<init>()
            r16 = r0
            r17 = r7
            r18 = r9
            r20 = r26
            r21 = r25
            r22 = r29
            r23 = r24
            r16.scheduleRemoveTargets(r17, r18, r19, r20, r21, r22, r23)
            r1 = r0
            r2 = r14
            r3 = r15
            r4 = r33
            r5 = r24
            r6 = r9
            r10 = r7
            r7 = r19
            r16 = r8
            r8 = r26
            r17 = r9
            r9 = r25
            scheduleTargetChange(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r1 = r24
            r0.setNameOverridesOrdered(r14, r1, r12)
            r0.beginDelayedTransition(r14, r10)
            r0.scheduleNameReset(r14, r1, r12)
            goto L_0x00dc
        L_0x00d5:
            r10 = r7
            r16 = r8
            r17 = r9
            r1 = r24
        L_0x00dc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.app.FragmentTransition.configureTransitionsOrdered(android.support.v4.app.FragmentManagerImpl, int, android.support.v4.app.FragmentTransition$FragmentContainerTransition, android.view.View, android.support.v4.util.ArrayMap):void");
    }

    private static void scheduleTargetChange(FragmentTransitionImpl impl, ViewGroup sceneRoot, Fragment inFragment, View nonExistentView, ArrayList<View> sharedElementsIn, Object enterTransition, ArrayList<View> enteringViews, Object exitTransition, ArrayList<View> exitingViews) {
        final Object obj = enterTransition;
        final FragmentTransitionImpl fragmentTransitionImpl = impl;
        final View view = nonExistentView;
        final Fragment fragment = inFragment;
        final ArrayList<View> arrayList = sharedElementsIn;
        final ArrayList<View> arrayList2 = enteringViews;
        final ArrayList<View> arrayList3 = exitingViews;
        final Object obj2 = exitTransition;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                Object obj = obj;
                if (obj != null) {
                    fragmentTransitionImpl.removeTarget(obj, view);
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, obj, fragment, arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList<View> tempExiting = new ArrayList<>();
                        tempExiting.add(view);
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, tempExiting);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static FragmentTransitionImpl chooseImpl(Fragment outFragment, Fragment inFragment) {
        ArrayList<Object> transitions = new ArrayList<>();
        if (outFragment != null) {
            Object exitTransition = outFragment.getExitTransition();
            if (exitTransition != null) {
                transitions.add(exitTransition);
            }
            Object returnTransition = outFragment.getReturnTransition();
            if (returnTransition != null) {
                transitions.add(returnTransition);
            }
            Object sharedReturnTransition = outFragment.getSharedElementReturnTransition();
            if (sharedReturnTransition != null) {
                transitions.add(sharedReturnTransition);
            }
        }
        if (inFragment != null) {
            Object enterTransition = inFragment.getEnterTransition();
            if (enterTransition != null) {
                transitions.add(enterTransition);
            }
            Object reenterTransition = inFragment.getReenterTransition();
            if (reenterTransition != null) {
                transitions.add(reenterTransition);
            }
            Object sharedEnterTransition = inFragment.getSharedElementEnterTransition();
            if (sharedEnterTransition != null) {
                transitions.add(sharedEnterTransition);
            }
        }
        if (transitions.isEmpty()) {
            return null;
        }
        FragmentTransitionImpl fragmentTransitionImpl = PLATFORM_IMPL;
        if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, transitions)) {
            return PLATFORM_IMPL;
        }
        FragmentTransitionImpl fragmentTransitionImpl2 = SUPPORT_IMPL;
        if (fragmentTransitionImpl2 != null && canHandleAll(fragmentTransitionImpl2, transitions)) {
            return SUPPORT_IMPL;
        }
        if (PLATFORM_IMPL == null && SUPPORT_IMPL == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static boolean canHandleAll(FragmentTransitionImpl impl, List<Object> transitions) {
        int size = transitions.size();
        for (int i = 0; i < size; i++) {
            if (!impl.canHandle(transitions.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl impl, Fragment inFragment, Fragment outFragment, boolean isPop) {
        Object obj;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getSharedElementReturnTransition();
        } else {
            obj = inFragment.getSharedElementEnterTransition();
        }
        return impl.wrapTransitionInSet(impl.cloneTransition(obj));
    }

    private static Object getEnterTransition(FragmentTransitionImpl impl, Fragment inFragment, boolean isPop) {
        Object obj;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            obj = inFragment.getReenterTransition();
        } else {
            obj = inFragment.getEnterTransition();
        }
        return impl.cloneTransition(obj);
    }

    private static Object getExitTransition(FragmentTransitionImpl impl, Fragment outFragment, boolean isPop) {
        Object obj;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getReturnTransition();
        } else {
            obj = outFragment.getExitTransition();
        }
        return impl.cloneTransition(obj);
    }

    private static Object configureSharedElementsReordered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Object sharedElementTransition3;
        Rect epicenter;
        View epicenterView;
        ArrayMap<String, View> inSharedElements;
        FragmentTransitionImpl fragmentTransitionImpl = impl;
        View view = nonExistentView;
        ArrayMap<String, String> arrayMap = nameOverrides;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        ArrayList<View> arrayList2 = sharedElementsIn;
        Object obj = enterTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment != null) {
            inFragment.requireView().setVisibility(0);
        }
        if (inFragment != null) {
            if (outFragment != null) {
                boolean inIsPop = fragmentContainerTransition.lastInIsPop;
                if (nameOverrides.isEmpty()) {
                    sharedElementTransition = null;
                } else {
                    sharedElementTransition = getSharedElementTransition(fragmentTransitionImpl, inFragment, outFragment, inIsPop);
                }
                ArrayMap<String, View> outSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
                ArrayMap<String, View> inSharedElements2 = captureInSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
                if (nameOverrides.isEmpty()) {
                    if (outSharedElements != null) {
                        outSharedElements.clear();
                    }
                    if (inSharedElements2 != null) {
                        inSharedElements2.clear();
                    }
                    sharedElementTransition2 = null;
                } else {
                    addSharedElementsWithMatchingNames(arrayList, outSharedElements, nameOverrides.keySet());
                    addSharedElementsWithMatchingNames(arrayList2, inSharedElements2, nameOverrides.values());
                    sharedElementTransition2 = sharedElementTransition;
                }
                if (obj == null && exitTransition == null && sharedElementTransition2 == null) {
                    return null;
                }
                callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
                if (sharedElementTransition2 != null) {
                    arrayList2.add(view);
                    fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition2, view, arrayList);
                    sharedElementTransition3 = sharedElementTransition2;
                    inSharedElements = inSharedElements2;
                    setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                    Rect epicenter2 = new Rect();
                    View epicenterView2 = getInEpicenterView(inSharedElements, fragmentContainerTransition, obj, inIsPop);
                    if (epicenterView2 != null) {
                        fragmentTransitionImpl.setEpicenter(obj, epicenter2);
                    }
                    epicenter = epicenter2;
                    epicenterView = epicenterView2;
                } else {
                    sharedElementTransition3 = sharedElementTransition2;
                    inSharedElements = inSharedElements2;
                    epicenter = null;
                    epicenterView = null;
                }
                final Fragment fragment = inFragment;
                final Fragment fragment2 = outFragment;
                final boolean z = inIsPop;
                final ArrayMap<String, View> arrayMap2 = inSharedElements;
                C00783 r8 = r0;
                final View view2 = epicenterView;
                final FragmentTransitionImpl fragmentTransitionImpl2 = impl;
                final Rect rect = epicenter;
                C00783 r0 = new Runnable() {
                    public void run() {
                        FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, arrayMap2, false);
                        View view = view2;
                        if (view != null) {
                            fragmentTransitionImpl2.getBoundsOnScreen(view, rect);
                        }
                    }
                };
                OneShotPreDrawListener.add(sceneRoot, r8);
                return sharedElementTransition3;
            }
        }
        return null;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> views, ArrayMap<String, View> sharedElements, Collection<String> nameOverridesSet) {
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = sharedElements.valueAt(i);
            if (nameOverridesSet.contains(ViewCompat.getTransitionName(view))) {
                views.add(view);
            }
        }
    }

    /* JADX INFO: Multiple debug info for r7v3 android.graphics.Rect: [D('inEpicenter' android.graphics.Rect), D('outSharedElements' android.support.v4.util.ArrayMap<java.lang.String, android.view.View>)] */
    private static Object configureSharedElementsOrdered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Rect inEpicenter;
        FragmentTransitionImpl fragmentTransitionImpl = impl;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        Object obj = enterTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment != null) {
            if (outFragment != null) {
                final boolean inIsPop = fragmentContainerTransition.lastInIsPop;
                if (nameOverrides.isEmpty()) {
                    sharedElementTransition = null;
                } else {
                    sharedElementTransition = getSharedElementTransition(fragmentTransitionImpl, inFragment, outFragment, inIsPop);
                }
                ArrayMap<String, View> outSharedElements = captureOutSharedElements(fragmentTransitionImpl, nameOverrides, sharedElementTransition, fragmentContainerTransition);
                if (nameOverrides.isEmpty()) {
                    sharedElementTransition2 = null;
                } else {
                    arrayList.addAll(outSharedElements.values());
                    sharedElementTransition2 = sharedElementTransition;
                }
                if (obj == null && exitTransition == null && sharedElementTransition2 == null) {
                    return null;
                }
                callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
                if (sharedElementTransition2 != null) {
                    Rect inEpicenter2 = new Rect();
                    fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition2, nonExistentView, arrayList);
                    Rect inEpicenter3 = inEpicenter2;
                    setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                    if (obj != null) {
                        fragmentTransitionImpl.setEpicenter(obj, inEpicenter3);
                    }
                    inEpicenter = inEpicenter3;
                } else {
                    inEpicenter = null;
                }
                final Object finalSharedElementTransition = sharedElementTransition2;
                final FragmentTransitionImpl fragmentTransitionImpl2 = impl;
                final ArrayMap<String, String> arrayMap = nameOverrides;
                final FragmentContainerTransition fragmentContainerTransition2 = fragments;
                final ArrayList<View> arrayList2 = sharedElementsIn;
                Object sharedElementTransition3 = sharedElementTransition2;
                final View view = nonExistentView;
                C00794 r13 = r0;
                final Fragment fragment = inFragment;
                final Fragment fragment2 = outFragment;
                final ArrayList<View> arrayList3 = sharedElementsOut;
                final Object obj2 = enterTransition;
                final Rect rect = inEpicenter;
                C00794 r0 = new Runnable() {
                    public void run() {
                        ArrayMap<String, View> inSharedElements = FragmentTransition.captureInSharedElements(fragmentTransitionImpl2, arrayMap, finalSharedElementTransition, fragmentContainerTransition2);
                        if (inSharedElements != null) {
                            arrayList2.addAll(inSharedElements.values());
                            arrayList2.add(view);
                        }
                        FragmentTransition.callSharedElementStartEnd(fragment, fragment2, inIsPop, inSharedElements, false);
                        Object obj = finalSharedElementTransition;
                        if (obj != null) {
                            fragmentTransitionImpl2.swapSharedElementTargets(obj, arrayList3, arrayList2);
                            View inEpicenterView = FragmentTransition.getInEpicenterView(inSharedElements, fragmentContainerTransition2, obj2, inIsPop);
                            if (inEpicenterView != null) {
                                fragmentTransitionImpl2.getBoundsOnScreen(inEpicenterView, rect);
                            }
                        }
                    }
                };
                OneShotPreDrawListener.add(sceneRoot, r13);
                return sharedElementTransition3;
            }
        }
        return null;
    }

    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        if (nameOverrides.isEmpty() || sharedElementTransition == null) {
            nameOverrides.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        impl.findNamedViews(outSharedElements, outFragment.requireView());
        BackStackRecord outTransaction = fragments.firstOutTransaction;
        if (fragments.firstOutIsPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
            names = outTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = outFragment.getExitTransitionCallback();
            names = outTransaction.mSharedElementSourceNames;
        }
        outSharedElements.retainAll(names);
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, outSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = (String) names.get(i);
                View view = outSharedElements.get(name);
                if (view == null) {
                    nameOverrides.remove(name);
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    nameOverrides.put(ViewCompat.getTransitionName(view), nameOverrides.remove(name));
                }
            }
        } else {
            nameOverrides.retainAll(outSharedElements.keySet());
        }
        return outSharedElements;
    }

    static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        String key;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        impl.findNamedViews(inSharedElements, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        }
        if (names != null) {
            inSharedElements.retainAll(names);
            inSharedElements.retainAll(nameOverrides.values());
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = (String) names.get(i);
                View view = inSharedElements.get(name);
                if (view == null) {
                    String key2 = findKeyForValue(nameOverrides, name);
                    if (key2 != null) {
                        nameOverrides.remove(key2);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view)) && (key = findKeyForValue(nameOverrides, name)) != null) {
                    nameOverrides.put(key, ViewCompat.getTransitionName(view));
                }
            }
        } else {
            retainValues(nameOverrides, inSharedElements);
        }
        return inSharedElements;
    }

    private static String findKeyForValue(ArrayMap<String, String> map, String value) {
        int numElements = map.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(map.valueAt(i))) {
                return map.keyAt(i);
            }
        }
        return null;
    }

    static View getInEpicenterView(ArrayMap<String, View> inSharedElements, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        String targetName;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition == null || inSharedElements == null || inTransaction.mSharedElementSourceNames == null || inTransaction.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (inIsPop) {
            targetName = (String) inTransaction.mSharedElementSourceNames.get(0);
        } else {
            targetName = (String) inTransaction.mSharedElementTargetNames.get(0);
        }
        return inSharedElements.get(targetName);
    }

    private static void setOutEpicenter(FragmentTransitionImpl impl, Object sharedElementTransition, Object exitTransition, ArrayMap<String, View> outSharedElements, boolean outIsPop, BackStackRecord outTransaction) {
        String sourceName;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (outIsPop) {
                sourceName = (String) outTransaction.mSharedElementTargetNames.get(0);
            } else {
                sourceName = (String) outTransaction.mSharedElementSourceNames.get(0);
            }
            View outEpicenterView = outSharedElements.get(sourceName);
            impl.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                impl.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    private static void retainValues(ArrayMap<String, String> nameOverrides, ArrayMap<String, View> namedViews) {
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            if (!namedViews.containsKey(nameOverrides.valueAt(i))) {
                nameOverrides.removeAt(i);
            }
        }
    }

    static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> sharedElements, boolean isStart) {
        SharedElementCallback sharedElementCallback;
        if (isPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            int count = sharedElements == null ? 0 : sharedElements.size();
            for (int i = 0; i < count; i++) {
                names.add(sharedElements.keyAt(i));
                views.add(sharedElements.valueAt(i));
            }
            if (isStart) {
                sharedElementCallback.onSharedElementStart(names, views, null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, null);
            }
        }
    }

    static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl impl, Object transition, Fragment fragment, ArrayList<View> sharedElements, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                impl.captureTransitioningViews(viewList, root);
            }
            if (sharedElements != null) {
                viewList.removeAll(sharedElements);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                impl.addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    static void setViewVisibility(ArrayList<View> views, int visibility) {
        if (views != null) {
            for (int i = views.size() - 1; i >= 0; i--) {
                views.get(i).setVisibility(visibility);
            }
        }
    }

    private static Object mergeTransitions(FragmentTransitionImpl impl, Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean z;
        boolean overlap = true;
        if (!(enterTransition == null || exitTransition == null || inFragment == null)) {
            if (isPop) {
                z = inFragment.getAllowReturnTransitionOverlap();
            } else {
                z = inFragment.getAllowEnterTransitionOverlap();
            }
            overlap = z;
        }
        if (overlap) {
            return impl.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
        }
        return impl.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            addToFirstInLastOut(transaction, (FragmentTransaction.C0075Op) transaction.mOps.get(opNum), transitioningFragments, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        if (transaction.mManager.mContainer.onHasView()) {
            for (int opNum = transaction.mOps.size() - 1; opNum >= 0; opNum--) {
                addToFirstInLastOut(transaction, (FragmentTransaction.C0075Op) transaction.mOps.get(opNum), transitioningFragments, true, isReordered);
            }
        }
    }

    static boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x012f A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:110:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00d6 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void addToFirstInLastOut(android.support.p001v4.app.BackStackRecord r22, android.support.p001v4.app.FragmentTransaction.C0075Op r23, android.util.SparseArray<android.support.p001v4.app.FragmentTransition.FragmentContainerTransition> r24, boolean r25, boolean r26) {
        /*
            r0 = r22
            r1 = r23
            r2 = r24
            r3 = r25
            android.support.v4.app.Fragment r10 = r1.mFragment
            if (r10 != 0) goto L_0x000d
            return
        L_0x000d:
            int r11 = r10.mContainerId
            if (r11 != 0) goto L_0x0012
            return
        L_0x0012:
            if (r3 == 0) goto L_0x001b
            int[] r4 = android.support.p001v4.app.FragmentTransition.INVERSE_OPS
            int r5 = r1.mCmd
            r4 = r4[r5]
            goto L_0x001d
        L_0x001b:
            int r4 = r1.mCmd
        L_0x001d:
            r12 = r4
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 1
            if (r12 == r9) goto L_0x00a8
            r13 = 3
            if (r12 == r13) goto L_0x0079
            r13 = 4
            if (r12 == r13) goto L_0x0057
            r13 = 5
            if (r12 == r13) goto L_0x003c
            r13 = 6
            if (r12 == r13) goto L_0x0079
            r13 = 7
            if (r12 == r13) goto L_0x00a8
            r13 = r4
            r15 = r5
            r16 = r6
            r14 = r7
            goto L_0x00bd
        L_0x003c:
            if (r26 == 0) goto L_0x004d
            boolean r13 = r10.mHiddenChanged
            if (r13 == 0) goto L_0x004b
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x004b
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x004b
            r8 = 1
        L_0x004b:
            r4 = r8
            goto L_0x004f
        L_0x004d:
            boolean r4 = r10.mHidden
        L_0x004f:
            r7 = 1
            r13 = r4
            r15 = r5
            r16 = r6
            r14 = r7
            goto L_0x00bd
        L_0x0057:
            if (r26 == 0) goto L_0x0068
            boolean r13 = r10.mHiddenChanged
            if (r13 == 0) goto L_0x0066
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0066
            boolean r13 = r10.mHidden
            if (r13 == 0) goto L_0x0066
            r8 = 1
        L_0x0066:
            r6 = r8
            goto L_0x0072
        L_0x0068:
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x0071
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x0071
            r8 = 1
        L_0x0071:
            r6 = r8
        L_0x0072:
            r5 = 1
            r13 = r4
            r15 = r5
            r16 = r6
            r14 = r7
            goto L_0x00bd
        L_0x0079:
            if (r26 == 0) goto L_0x0097
            boolean r13 = r10.mAdded
            if (r13 != 0) goto L_0x0094
            android.view.View r13 = r10.mView
            if (r13 == 0) goto L_0x0094
            android.view.View r13 = r10.mView
            int r13 = r13.getVisibility()
            if (r13 != 0) goto L_0x0094
            float r13 = r10.mPostponedAlpha
            r14 = 0
            int r13 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r13 < 0) goto L_0x0094
            r8 = 1
            goto L_0x0095
        L_0x0094:
        L_0x0095:
            r6 = r8
            goto L_0x00a1
        L_0x0097:
            boolean r13 = r10.mAdded
            if (r13 == 0) goto L_0x00a0
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x00a0
            r8 = 1
        L_0x00a0:
            r6 = r8
        L_0x00a1:
            r5 = 1
            r13 = r4
            r15 = r5
            r16 = r6
            r14 = r7
            goto L_0x00bd
        L_0x00a8:
            if (r26 == 0) goto L_0x00ad
            boolean r4 = r10.mIsNewlyAdded
            goto L_0x00b7
        L_0x00ad:
            boolean r13 = r10.mAdded
            if (r13 != 0) goto L_0x00b6
            boolean r13 = r10.mHidden
            if (r13 != 0) goto L_0x00b6
            r8 = 1
        L_0x00b6:
            r4 = r8
        L_0x00b7:
            r7 = 1
            r13 = r4
            r15 = r5
            r16 = r6
            r14 = r7
        L_0x00bd:
            java.lang.Object r4 = r2.get(r11)
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r4 = (android.support.p001v4.app.FragmentTransition.FragmentContainerTransition) r4
            if (r13 == 0) goto L_0x00d2
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r4 = ensureContainer(r4, r2, r11)
            r4.lastIn = r10
            r4.lastInIsPop = r3
            r4.lastInTransaction = r0
            r8 = r4
            goto L_0x00d3
        L_0x00d2:
            r8 = r4
        L_0x00d3:
            r7 = 0
            if (r26 != 0) goto L_0x0111
            if (r14 == 0) goto L_0x0111
            if (r8 == 0) goto L_0x00e0
            android.support.v4.app.Fragment r4 = r8.firstOut
            if (r4 != r10) goto L_0x00e0
            r8.firstOut = r7
        L_0x00e0:
            android.support.v4.app.FragmentManagerImpl r6 = r0.mManager
            int r4 = r10.mState
            if (r4 >= r9) goto L_0x010b
            int r4 = r6.mCurState
            if (r4 < r9) goto L_0x010b
            boolean r4 = r0.mReorderingAllowed
            if (r4 != 0) goto L_0x010b
            r6.makeActive(r10)
            r9 = 1
            r17 = 0
            r18 = 0
            r19 = 0
            r4 = r6
            r5 = r10
            r20 = r6
            r6 = r9
            r9 = r7
            r7 = r17
            r21 = r8
            r8 = r18
            r1 = r9
            r9 = r19
            r4.moveToState(r5, r6, r7, r8, r9)
            goto L_0x0114
        L_0x010b:
            r20 = r6
            r1 = r7
            r21 = r8
            goto L_0x0114
        L_0x0111:
            r1 = r7
            r21 = r8
        L_0x0114:
            if (r16 == 0) goto L_0x012b
            r4 = r21
            if (r4 == 0) goto L_0x011e
            android.support.v4.app.Fragment r5 = r4.firstOut
            if (r5 != 0) goto L_0x012d
        L_0x011e:
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r8 = ensureContainer(r4, r2, r11)
            r8.firstOut = r10
            r8.firstOutIsPop = r3
            r8.firstOutTransaction = r0
            r4 = r8
            goto L_0x012d
        L_0x012b:
            r4 = r21
        L_0x012d:
            if (r26 != 0) goto L_0x0139
            if (r15 == 0) goto L_0x0139
            if (r4 == 0) goto L_0x0139
            android.support.v4.app.Fragment r5 = r4.lastIn
            if (r5 != r10) goto L_0x0139
            r4.lastIn = r1
        L_0x0139:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.app.FragmentTransition.addToFirstInLastOut(android.support.v4.app.BackStackRecord, android.support.v4.app.FragmentTransaction$Op, android.util.SparseArray, boolean, boolean):void");
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> transitioningFragments, int containerId) {
        if (containerTransition != null) {
            return containerTransition;
        }
        FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
        transitioningFragments.put(containerId, containerTransition2);
        return containerTransition2;
    }

    /* renamed from: android.support.v4.app.FragmentTransition$FragmentContainerTransition */
    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }
}
