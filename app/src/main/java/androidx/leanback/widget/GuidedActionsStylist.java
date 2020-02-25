package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.ContextCompat;
import android.support.p004v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.C0364R;
import androidx.leanback.transition.TransitionEpicenterCallback;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.widget.picker.DatePicker;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class GuidedActionsStylist implements FragmentAnimationProvider {
    public static final int VIEW_TYPE_DATE_PICKER = 1;
    public static final int VIEW_TYPE_DEFAULT = 0;
    static final ItemAlignmentFacet sGuidedActionItemAlignFacet = new ItemAlignmentFacet();
    private static final String TAG = "GuidedActionsStylist";

    static {
        ItemAlignmentFacet.ItemAlignmentDef alignedDef = new ItemAlignmentFacet.ItemAlignmentDef();
        alignedDef.setItemAlignmentViewId(C0364R.C0366id.guidedactions_item_title);
        alignedDef.setAlignedToTextViewBaseline(true);
        alignedDef.setItemAlignmentOffset(0);
        alignedDef.setItemAlignmentOffsetWithPadding(true);
        alignedDef.setItemAlignmentOffsetPercent(0.0f);
        sGuidedActionItemAlignFacet.setAlignmentDefs(new ItemAlignmentFacet.ItemAlignmentDef[]{alignedDef});
    }

    Object mExpandTransition;
    GuidedAction mExpandedAction = null;
    ViewGroup mMainView;
    VerticalGridView mSubActionsGridView;
    private VerticalGridView mActionsGridView;
    private boolean mBackToCollapseActivatorView = true;
    private boolean mBackToCollapseSubActions = true;
    private View mBgView;
    private boolean mButtonActions;
    private View mContentView;
    private int mDescriptionMinLines;
    private float mDisabledChevronAlpha;
    private float mDisabledDescriptionAlpha;
    private float mDisabledTextAlpha;
    private int mDisplayHeight;
    private GuidedActionAdapter.EditListener mEditListener;
    private float mEnabledChevronAlpha;
    private float mEnabledDescriptionAlpha;
    private float mEnabledTextAlpha;
    private float mKeyLinePercent;
    private View mSubActionsBackground;
    private int mTitleMaxLines;
    private int mTitleMinLines;
    private int mVerticalPadding;

    private static void setMaxLines(TextView view, int maxLines) {
        if (maxLines == 1) {
            view.setSingleLine(true);
            return;
        }
        view.setSingleLine(false);
        view.setMaxLines(maxLines);
    }

    private static float getFloat(Context ctx, TypedValue typedValue, int attrId) {
        ctx.getTheme().resolveAttribute(attrId, typedValue, true);
        return typedValue.getFloat();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
     arg types: [int, android.util.TypedValue, int]
     candidates:
      ClspMth{android.content.res.Resources.getValue(java.lang.String, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
      ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException} */
    private static float getFloatValue(Resources resources, TypedValue typedValue, int resId) {
        resources.getValue(resId, typedValue, true);
        return typedValue.getFloat();
    }

    private static int getInteger(Context ctx, TypedValue typedValue, int attrId) {
        ctx.getTheme().resolveAttribute(attrId, typedValue, true);
        return ctx.getResources().getInteger(typedValue.resourceId);
    }

    private static int getDimension(Context ctx, TypedValue typedValue, int attrId) {
        ctx.getTheme().resolveAttribute(attrId, typedValue, true);
        return ctx.getResources().getDimensionPixelSize(typedValue.resourceId);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        float keylinePercent = inflater.getContext().getTheme().obtainStyledAttributes(C0364R.styleable.LeanbackGuidedStepTheme).getFloat(C0364R.styleable.LeanbackGuidedStepTheme_guidedStepKeyline, 40.0f);
        this.mMainView = (ViewGroup) inflater.inflate(onProvideLayoutId(), container, false);
        this.mContentView = this.mMainView.findViewById(this.mButtonActions ? C0364R.C0366id.guidedactions_content2 : C0364R.C0366id.guidedactions_content);
        this.mBgView = this.mMainView.findViewById(this.mButtonActions ? C0364R.C0366id.guidedactions_list_background2 : C0364R.C0366id.guidedactions_list_background);
        ViewGroup viewGroup = this.mMainView;
        if (viewGroup instanceof VerticalGridView) {
            this.mActionsGridView = (VerticalGridView) viewGroup;
        } else {
            this.mActionsGridView = (VerticalGridView) viewGroup.findViewById(this.mButtonActions ? C0364R.C0366id.guidedactions_list2 : C0364R.C0366id.guidedactions_list);
            VerticalGridView verticalGridView = this.mActionsGridView;
            if (verticalGridView != null) {
                verticalGridView.setWindowAlignmentOffsetPercent(keylinePercent);
                this.mActionsGridView.setWindowAlignment(0);
                if (!this.mButtonActions) {
                    this.mSubActionsGridView = (VerticalGridView) this.mMainView.findViewById(C0364R.C0366id.guidedactions_sub_list);
                    this.mSubActionsBackground = this.mMainView.findViewById(C0364R.C0366id.guidedactions_sub_list_background);
                }
            } else {
                throw new IllegalStateException("No ListView exists.");
            }
        }
        this.mActionsGridView.setFocusable(false);
        this.mActionsGridView.setFocusableInTouchMode(false);
        Context ctx = this.mMainView.getContext();
        TypedValue val = new TypedValue();
        this.mEnabledChevronAlpha = getFloat(ctx, val, C0364R.attr.guidedActionEnabledChevronAlpha);
        this.mDisabledChevronAlpha = getFloat(ctx, val, C0364R.attr.guidedActionDisabledChevronAlpha);
        this.mTitleMinLines = getInteger(ctx, val, C0364R.attr.guidedActionTitleMinLines);
        this.mTitleMaxLines = getInteger(ctx, val, C0364R.attr.guidedActionTitleMaxLines);
        this.mDescriptionMinLines = getInteger(ctx, val, C0364R.attr.guidedActionDescriptionMinLines);
        this.mVerticalPadding = getDimension(ctx, val, C0364R.attr.guidedActionVerticalPadding);
        this.mDisplayHeight = ((WindowManager) ctx.getSystemService("window")).getDefaultDisplay().getHeight();
        this.mEnabledTextAlpha = getFloatValue(ctx.getResources(), val, C0364R.dimen.lb_guidedactions_item_unselected_text_alpha);
        this.mDisabledTextAlpha = getFloatValue(ctx.getResources(), val, C0364R.dimen.lb_guidedactions_item_disabled_text_alpha);
        this.mEnabledDescriptionAlpha = getFloatValue(ctx.getResources(), val, C0364R.dimen.lb_guidedactions_item_unselected_description_text_alpha);
        this.mDisabledDescriptionAlpha = getFloatValue(ctx.getResources(), val, C0364R.dimen.lb_guidedactions_item_disabled_description_text_alpha);
        this.mKeyLinePercent = GuidanceStylingRelativeLayout.getKeyLinePercent(ctx);
        View view = this.mContentView;
        if (view instanceof GuidedActionsRelativeLayout) {
            ((GuidedActionsRelativeLayout) view).setInterceptKeyEventListener(new GuidedActionsRelativeLayout.InterceptKeyEventListener() {
                public boolean onInterceptKeyEvent(KeyEvent event) {
                    if (event.getKeyCode() != 4 || event.getAction() != 1 || GuidedActionsStylist.this.mExpandedAction == null) {
                        return false;
                    }
                    if ((!GuidedActionsStylist.this.mExpandedAction.hasSubActions() || !GuidedActionsStylist.this.isBackKeyToCollapseSubActions()) && (!GuidedActionsStylist.this.mExpandedAction.hasEditableActivatorView() || !GuidedActionsStylist.this.isBackKeyToCollapseActivatorView())) {
                        return false;
                    }
                    GuidedActionsStylist.this.collapseAction(true);
                    return true;
                }
            });
        }
        return this.mMainView;
    }

    public void setAsButtonActions() {
        if (this.mMainView == null) {
            this.mButtonActions = true;
            return;
        }
        throw new IllegalStateException("setAsButtonActions() must be called before creating views");
    }

    public boolean isButtonActions() {
        return this.mButtonActions;
    }

    public void onDestroyView() {
        this.mExpandedAction = null;
        this.mExpandTransition = null;
        this.mActionsGridView = null;
        this.mSubActionsGridView = null;
        this.mSubActionsBackground = null;
        this.mContentView = null;
        this.mBgView = null;
        this.mMainView = null;
    }

    public VerticalGridView getActionsGridView() {
        return this.mActionsGridView;
    }

    public VerticalGridView getSubActionsGridView() {
        return this.mSubActionsGridView;
    }

    public int onProvideLayoutId() {
        return this.mButtonActions ? C0364R.layout.lb_guidedbuttonactions : C0364R.layout.lb_guidedactions;
    }

    public int getItemViewType(GuidedAction action) {
        if (action instanceof GuidedDatePickerAction) {
            return 1;
        }
        return 0;
    }

    public int onProvideItemLayoutId() {
        return C0364R.layout.lb_guidedactions_item;
    }

    public int onProvideItemLayoutId(int viewType) {
        if (viewType == 0) {
            return onProvideItemLayoutId();
        }
        if (viewType == 1) {
            return C0364R.layout.lb_guidedactions_datepicker_item;
        }
        throw new RuntimeException("ViewType " + viewType + " not supported in GuidedActionsStylist");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        boolean z = false;
        View v = LayoutInflater.from(parent.getContext()).inflate(onProvideItemLayoutId(), parent, false);
        if (parent == this.mSubActionsGridView) {
            z = true;
        }
        return new ViewHolder(v, z);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return onCreateViewHolder(parent);
        }
        boolean z = false;
        View v = LayoutInflater.from(parent.getContext()).inflate(onProvideItemLayoutId(viewType), parent, false);
        if (parent == this.mSubActionsGridView) {
            z = true;
        }
        return new ViewHolder(v, z);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void
     arg types: [androidx.leanback.widget.GuidedActionsStylist$ViewHolder, int, int]
     candidates:
      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, androidx.leanback.widget.GuidedAction, boolean):void
      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void */
    public void onBindViewHolder(ViewHolder vh, GuidedAction action) {
        vh.mAction = action;
        if (vh.mTitleView != null) {
            vh.mTitleView.setInputType(action.getInputType());
            vh.mTitleView.setText(action.getTitle());
            vh.mTitleView.setAlpha(action.isEnabled() ? this.mEnabledTextAlpha : this.mDisabledTextAlpha);
            vh.mTitleView.setFocusable(false);
            vh.mTitleView.setClickable(false);
            vh.mTitleView.setLongClickable(false);
            if (Build.VERSION.SDK_INT >= 28) {
                if (action.isEditable()) {
                    vh.mTitleView.setAutofillHints(action.getAutofillHints());
                } else {
                    vh.mTitleView.setAutofillHints(null);
                }
            } else if (Build.VERSION.SDK_INT >= 26) {
                vh.mTitleView.setImportantForAutofill(2);
            }
        }
        if (vh.mDescriptionView != null) {
            vh.mDescriptionView.setInputType(action.getDescriptionInputType());
            vh.mDescriptionView.setText(action.getDescription());
            vh.mDescriptionView.setVisibility(TextUtils.isEmpty(action.getDescription()) ? 8 : 0);
            vh.mDescriptionView.setAlpha(action.isEnabled() ? this.mEnabledDescriptionAlpha : this.mDisabledDescriptionAlpha);
            vh.mDescriptionView.setFocusable(false);
            vh.mDescriptionView.setClickable(false);
            vh.mDescriptionView.setLongClickable(false);
            if (Build.VERSION.SDK_INT >= 28) {
                if (action.isDescriptionEditable()) {
                    vh.mDescriptionView.setAutofillHints(action.getAutofillHints());
                } else {
                    vh.mDescriptionView.setAutofillHints(null);
                }
            } else if (Build.VERSION.SDK_INT >= 26) {
                vh.mTitleView.setImportantForAutofill(2);
            }
        }
        if (vh.mCheckmarkView != null) {
            onBindCheckMarkView(vh, action);
        }
        setIcon(vh.mIconView, action);
        if (!action.hasMultilineDescription()) {
            if (vh.mTitleView != null) {
                setMaxLines(vh.mTitleView, this.mTitleMinLines);
            }
            if (vh.mDescriptionView != null) {
                setMaxLines(vh.mDescriptionView, this.mDescriptionMinLines);
            }
        } else if (vh.mTitleView != null) {
            setMaxLines(vh.mTitleView, this.mTitleMaxLines);
            vh.mTitleView.setInputType(vh.mTitleView.getInputType() | 131072);
            if (vh.mDescriptionView != null) {
                vh.mDescriptionView.setInputType(vh.mDescriptionView.getInputType() | 131072);
                vh.mDescriptionView.setMaxHeight(getDescriptionMaxHeight(vh.itemView.getContext(), vh.mTitleView));
            }
        }
        if (vh.mActivatorView != null) {
            onBindActivatorView(vh, action);
        }
        setEditingMode(vh, false, false);
        if (action.isFocusable()) {
            vh.itemView.setFocusable(true);
            ((ViewGroup) vh.itemView).setDescendantFocusability(131072);
        } else {
            vh.itemView.setFocusable(false);
            ((ViewGroup) vh.itemView).setDescendantFocusability(393216);
        }
        setupImeOptions(vh, action);
        updateChevronAndVisibility(vh);
    }

    public void openInEditMode(GuidedAction action) {
        final GuidedActionAdapter guidedActionAdapter = (GuidedActionAdapter) getActionsGridView().getAdapter();
        int actionIndex = guidedActionAdapter.getActions().indexOf(action);
        if (actionIndex >= 0 && action.isEditable()) {
            getActionsGridView().setSelectedPosition(actionIndex, new ViewHolderTask() {
                public void run(RecyclerView.ViewHolder viewHolder) {
                    guidedActionAdapter.mGroup.openIme(guidedActionAdapter, (ViewHolder) viewHolder);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void setupImeOptions(ViewHolder vh, GuidedAction action) {
        setupNextImeOptions(vh.getEditableTitleView());
        setupNextImeOptions(vh.getEditableDescriptionView());
    }

    private void setupNextImeOptions(EditText edit) {
        if (edit != null) {
            edit.setImeOptions(5);
        }
    }

    @Deprecated
    public void setEditingMode(ViewHolder vh, GuidedAction action, boolean editing) {
        if (editing != vh.isInEditing() && isInExpandTransition()) {
            onEditingModeChange(vh, action, editing);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void
     arg types: [androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, int]
     candidates:
      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, androidx.leanback.widget.GuidedAction, boolean):void
      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void */
    /* access modifiers changed from: package-private */
    public void setEditingMode(ViewHolder vh, boolean editing) {
        setEditingMode(vh, editing, true);
    }

    /* access modifiers changed from: package-private */
    public void setEditingMode(ViewHolder vh, boolean editing, boolean withTransition) {
        if (editing != vh.isInEditing() && !isInExpandTransition()) {
            onEditingModeChange(vh, editing, withTransition);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onEditingModeChange(ViewHolder vh, GuidedAction action, boolean editing) {
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onEditingModeChange(ViewHolder vh, boolean editing, boolean withTransition) {
        GuidedAction action = vh.getAction();
        TextView titleView = vh.getTitleView();
        TextView descriptionView = vh.getDescriptionView();
        if (editing) {
            CharSequence editTitle = action.getEditTitle();
            if (!(titleView == null || editTitle == null)) {
                titleView.setText(editTitle);
            }
            CharSequence editDescription = action.getEditDescription();
            if (!(descriptionView == null || editDescription == null)) {
                descriptionView.setText(editDescription);
            }
            if (action.isDescriptionEditable()) {
                if (descriptionView != null) {
                    descriptionView.setVisibility(0);
                    descriptionView.setInputType(action.getDescriptionEditInputType());
                }
                vh.mEditingMode = 2;
            } else if (action.isEditable()) {
                if (titleView != null) {
                    titleView.setInputType(action.getEditInputType());
                }
                vh.mEditingMode = 1;
            } else if (vh.mActivatorView != null) {
                onEditActivatorView(vh, editing, withTransition);
                vh.mEditingMode = 3;
            }
        } else {
            if (titleView != null) {
                titleView.setText(action.getTitle());
            }
            if (descriptionView != null) {
                descriptionView.setText(action.getDescription());
            }
            if (vh.mEditingMode == 2) {
                if (descriptionView != null) {
                    descriptionView.setVisibility(TextUtils.isEmpty(action.getDescription()) ? 8 : 0);
                    descriptionView.setInputType(action.getDescriptionInputType());
                }
            } else if (vh.mEditingMode == 1) {
                if (titleView != null) {
                    titleView.setInputType(action.getInputType());
                }
            } else if (vh.mEditingMode == 3 && vh.mActivatorView != null) {
                onEditActivatorView(vh, editing, withTransition);
            }
            vh.mEditingMode = 0;
        }
        onEditingModeChange(vh, action, editing);
    }

    public void onAnimateItemFocused(ViewHolder vh, boolean focused) {
    }

    public void onAnimateItemPressed(ViewHolder vh, boolean pressed) {
        vh.press(pressed);
    }

    public void onAnimateItemPressedCancelled(ViewHolder vh) {
        vh.press(false);
    }

    public void onAnimateItemChecked(ViewHolder vh, boolean checked) {
        if (vh.mCheckmarkView instanceof Checkable) {
            ((Checkable) vh.mCheckmarkView).setChecked(checked);
        }
    }

    public void onBindCheckMarkView(ViewHolder vh, GuidedAction action) {
        if (action.getCheckSetId() != 0) {
            vh.mCheckmarkView.setVisibility(0);
            int attrId = action.getCheckSetId() == -1 ? 16843290 : 16843289;
            Context context = vh.mCheckmarkView.getContext();
            Drawable drawable = null;
            TypedValue typedValue = new TypedValue();
            if (context.getTheme().resolveAttribute(attrId, typedValue, true)) {
                drawable = ContextCompat.getDrawable(context, typedValue.resourceId);
            }
            vh.mCheckmarkView.setImageDrawable(drawable);
            if (vh.mCheckmarkView instanceof Checkable) {
                ((Checkable) vh.mCheckmarkView).setChecked(action.isChecked());
                return;
            }
            return;
        }
        vh.mCheckmarkView.setVisibility(8);
    }

    public void onBindActivatorView(ViewHolder vh, GuidedAction action) {
        if (action instanceof GuidedDatePickerAction) {
            GuidedDatePickerAction dateAction = (GuidedDatePickerAction) action;
            DatePicker dateView = (DatePicker) vh.mActivatorView;
            dateView.setDatePickerFormat(dateAction.getDatePickerFormat());
            if (dateAction.getMinDate() != Long.MIN_VALUE) {
                dateView.setMinDate(dateAction.getMinDate());
            }
            if (dateAction.getMaxDate() != Long.MAX_VALUE) {
                dateView.setMaxDate(dateAction.getMaxDate());
            }
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(dateAction.getDate());
            dateView.setDate(c.get(1), c.get(2), c.get(5), false);
        }
    }

    public boolean onUpdateActivatorView(ViewHolder vh, GuidedAction action) {
        if (!(action instanceof GuidedDatePickerAction)) {
            return false;
        }
        GuidedDatePickerAction dateAction = (GuidedDatePickerAction) action;
        DatePicker dateView = (DatePicker) vh.mActivatorView;
        if (dateAction.getDate() == dateView.getDate()) {
            return false;
        }
        dateAction.setDate(dateView.getDate());
        return true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setEditListener(GuidedActionAdapter.EditListener listener) {
        this.mEditListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void onEditActivatorView(final ViewHolder vh, boolean editing, boolean withTransition) {
        GuidedActionAdapter.EditListener editListener;
        if (editing) {
            startExpanded(vh, withTransition);
            vh.itemView.setFocusable(false);
            vh.mActivatorView.requestFocus();
            vh.mActivatorView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!GuidedActionsStylist.this.isInExpandTransition()) {
                        ((GuidedActionAdapter) GuidedActionsStylist.this.getActionsGridView().getAdapter()).performOnActionClick(vh);
                    }
                }
            });
            return;
        }
        if (onUpdateActivatorView(vh, vh.getAction()) && (editListener = this.mEditListener) != null) {
            editListener.onGuidedActionEditedAndProceed(vh.getAction());
        }
        vh.itemView.setFocusable(true);
        vh.itemView.requestFocus();
        startExpanded(null, withTransition);
        vh.mActivatorView.setOnClickListener(null);
        vh.mActivatorView.setClickable(false);
    }

    public void onBindChevronView(ViewHolder vh, GuidedAction action) {
        boolean hasNext = action.hasNext();
        boolean hasSubActions = action.hasSubActions();
        if (hasNext || hasSubActions) {
            vh.mChevronView.setVisibility(0);
            vh.mChevronView.setAlpha(action.isEnabled() ? this.mEnabledChevronAlpha : this.mDisabledChevronAlpha);
            if (hasNext) {
                ViewGroup viewGroup = this.mMainView;
                vh.mChevronView.setRotation((viewGroup == null || viewGroup.getLayoutDirection() != 1) ? 0.0f : 180.0f);
            } else if (action == this.mExpandedAction) {
                vh.mChevronView.setRotation(270.0f);
            } else {
                vh.mChevronView.setRotation(90.0f);
            }
        } else {
            vh.mChevronView.setVisibility(8);
        }
    }

    @Deprecated
    public void setExpandedViewHolder(ViewHolder avh) {
        expandAction(avh == null ? null : avh.getAction(), isExpandTransitionSupported());
    }

    public boolean isInExpandTransition() {
        return this.mExpandTransition != null;
    }

    public boolean isExpandTransitionSupported() {
        return Build.VERSION.SDK_INT >= 21;
    }

    @Deprecated
    public void startExpandedTransition(ViewHolder avh) {
        expandAction(avh == null ? null : avh.getAction(), isExpandTransitionSupported());
    }

    public final boolean isBackKeyToCollapseSubActions() {
        return this.mBackToCollapseSubActions;
    }

    public final void setBackKeyToCollapseSubActions(boolean backToCollapse) {
        this.mBackToCollapseSubActions = backToCollapse;
    }

    public final boolean isBackKeyToCollapseActivatorView() {
        return this.mBackToCollapseActivatorView;
    }

    public final void setBackKeyToCollapseActivatorView(boolean backToCollapse) {
        this.mBackToCollapseActivatorView = backToCollapse;
    }

    public void expandAction(GuidedAction action, boolean withTransition) {
        int actionPosition;
        if (!isInExpandTransition() && this.mExpandedAction == null && (actionPosition = ((GuidedActionAdapter) getActionsGridView().getAdapter()).indexOf(action)) >= 0) {
            if (!(isExpandTransitionSupported() && withTransition)) {
                getActionsGridView().setSelectedPosition(actionPosition, new ViewHolderTask() {
                    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
                     method: androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void
                     arg types: [androidx.leanback.widget.GuidedActionsStylist$ViewHolder, int, int]
                     candidates:
                      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, androidx.leanback.widget.GuidedAction, boolean):void
                      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void */
                    public void run(RecyclerView.ViewHolder vh) {
                        ViewHolder avh = (ViewHolder) vh;
                        if (avh.getAction().hasEditableActivatorView()) {
                            GuidedActionsStylist.this.setEditingMode(avh, true, false);
                        } else {
                            GuidedActionsStylist.this.onUpdateExpandedViewHolder(avh);
                        }
                    }
                });
                if (action.hasSubActions()) {
                    onUpdateSubActionsGridView(action, true);
                    return;
                }
                return;
            }
            getActionsGridView().setSelectedPosition(actionPosition, new ViewHolderTask() {
                /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
                 method: androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void
                 arg types: [androidx.leanback.widget.GuidedActionsStylist$ViewHolder, int, int]
                 candidates:
                  androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, androidx.leanback.widget.GuidedAction, boolean):void
                  androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void */
                public void run(RecyclerView.ViewHolder vh) {
                    ViewHolder avh = (ViewHolder) vh;
                    if (avh.getAction().hasEditableActivatorView()) {
                        GuidedActionsStylist.this.setEditingMode(avh, true, true);
                    } else {
                        GuidedActionsStylist.this.startExpanded(avh, true);
                    }
                }
            });
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void
     arg types: [androidx.leanback.widget.GuidedActionsStylist$ViewHolder, int, boolean]
     candidates:
      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, androidx.leanback.widget.GuidedAction, boolean):void
      androidx.leanback.widget.GuidedActionsStylist.setEditingMode(androidx.leanback.widget.GuidedActionsStylist$ViewHolder, boolean, boolean):void */
    public void collapseAction(boolean withTransition) {
        if (!isInExpandTransition() && this.mExpandedAction != null) {
            boolean runTransition = isExpandTransitionSupported() && withTransition;
            int actionPosition = ((GuidedActionAdapter) getActionsGridView().getAdapter()).indexOf(this.mExpandedAction);
            if (actionPosition >= 0) {
                if (this.mExpandedAction.hasEditableActivatorView()) {
                    setEditingMode((ViewHolder) getActionsGridView().findViewHolderForPosition(actionPosition), false, runTransition);
                } else {
                    startExpanded(null, runTransition);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getKeyLine() {
        return (int) ((this.mKeyLinePercent * ((float) this.mActionsGridView.getHeight())) / 100.0f);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: androidx.leanback.transition.TransitionHelper.exclude(java.lang.Object, android.view.View, boolean):void
     arg types: [java.lang.Object, android.view.View, int]
     candidates:
      androidx.leanback.transition.TransitionHelper.exclude(java.lang.Object, int, boolean):void
      androidx.leanback.transition.TransitionHelper.exclude(java.lang.Object, android.view.View, boolean):void */
    /* access modifiers changed from: package-private */
    public void startExpanded(ViewHolder avh, boolean withTransition) {
        float slideDistance;
        Object changeGridBounds;
        int count;
        ViewHolder viewHolder = avh;
        ViewHolder focusAvh = null;
        int count2 = this.mActionsGridView.getChildCount();
        int i = 0;
        while (true) {
            if (i >= count2) {
                break;
            }
            VerticalGridView verticalGridView = this.mActionsGridView;
            ViewHolder vh = (ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i));
            if (viewHolder != null || vh.itemView.getVisibility() != 0) {
                if (viewHolder != null && vh.getAction() == avh.getAction()) {
                    focusAvh = vh;
                    break;
                }
                i++;
            } else {
                focusAvh = vh;
                break;
            }
        }
        if (focusAvh != null) {
            boolean isExpand = viewHolder != null;
            boolean isSubActionTransition = focusAvh.getAction().hasSubActions();
            if (withTransition) {
                Object set = TransitionHelper.createTransitionSet(false);
                View view = focusAvh.itemView;
                if (isSubActionTransition) {
                    slideDistance = (float) view.getHeight();
                } else {
                    slideDistance = ((float) view.getHeight()) * 0.5f;
                }
                Object slideAndFade = TransitionHelper.createFadeAndShortSlide(112, slideDistance);
                TransitionHelper.setEpicenterCallback(slideAndFade, new TransitionEpicenterCallback() {
                    Rect mRect = new Rect();

                    public Rect onGetEpicenter(Object transition) {
                        int centerY = GuidedActionsStylist.this.getKeyLine();
                        this.mRect.set(0, centerY, 0, centerY);
                        return this.mRect;
                    }
                });
                Object changeFocusItemTransform = TransitionHelper.createChangeTransform();
                Object changeFocusItemBounds = TransitionHelper.createChangeBounds(false);
                Object fade = TransitionHelper.createFadeTransition(3);
                Object changeGridBounds2 = TransitionHelper.createChangeBounds(false);
                if (viewHolder == null) {
                    TransitionHelper.setStartDelay(slideAndFade, 150);
                    TransitionHelper.setStartDelay(changeFocusItemTransform, 100);
                    TransitionHelper.setStartDelay(changeFocusItemBounds, 100);
                    changeGridBounds = changeGridBounds2;
                    TransitionHelper.setStartDelay(changeGridBounds, 100);
                } else {
                    changeGridBounds = changeGridBounds2;
                    TransitionHelper.setStartDelay(fade, 100);
                    TransitionHelper.setStartDelay(changeGridBounds, 50);
                    TransitionHelper.setStartDelay(changeFocusItemTransform, 50);
                    TransitionHelper.setStartDelay(changeFocusItemBounds, 50);
                }
                int i2 = 0;
                while (i2 < count2) {
                    VerticalGridView verticalGridView2 = this.mActionsGridView;
                    ViewHolder vh2 = (ViewHolder) verticalGridView2.getChildViewHolder(verticalGridView2.getChildAt(i2));
                    if (vh2 != focusAvh) {
                        TransitionHelper.include(slideAndFade, vh2.itemView);
                        count = count2;
                        TransitionHelper.exclude(fade, vh2.itemView, true);
                    } else if (isSubActionTransition) {
                        TransitionHelper.include(changeFocusItemTransform, vh2.itemView);
                        TransitionHelper.include(changeFocusItemBounds, vh2.itemView);
                        count = count2;
                    } else {
                        count = count2;
                    }
                    i2++;
                    count2 = count;
                }
                TransitionHelper.include(changeGridBounds, this.mSubActionsGridView);
                TransitionHelper.include(changeGridBounds, this.mSubActionsBackground);
                TransitionHelper.addTransition(set, slideAndFade);
                if (isSubActionTransition) {
                    TransitionHelper.addTransition(set, changeFocusItemTransform);
                    TransitionHelper.addTransition(set, changeFocusItemBounds);
                }
                TransitionHelper.addTransition(set, fade);
                TransitionHelper.addTransition(set, changeGridBounds);
                this.mExpandTransition = set;
                TransitionHelper.addTransitionListener(this.mExpandTransition, new TransitionListener() {
                    public void onTransitionEnd(Object transition) {
                        GuidedActionsStylist.this.mExpandTransition = null;
                    }
                });
                if (isExpand && isSubActionTransition) {
                    int startY = viewHolder.itemView.getBottom();
                    VerticalGridView verticalGridView3 = this.mSubActionsGridView;
                    verticalGridView3.offsetTopAndBottom(startY - verticalGridView3.getTop());
                    View view2 = this.mSubActionsBackground;
                    view2.offsetTopAndBottom(startY - view2.getTop());
                }
                TransitionHelper.beginDelayedTransition(this.mMainView, this.mExpandTransition);
            }
            onUpdateExpandedViewHolder(avh);
            if (isSubActionTransition) {
                onUpdateSubActionsGridView(focusAvh.getAction(), isExpand);
            }
        }
    }

    public boolean isSubActionsExpanded() {
        GuidedAction guidedAction = this.mExpandedAction;
        return guidedAction != null && guidedAction.hasSubActions();
    }

    public boolean isExpanded() {
        return this.mExpandedAction != null;
    }

    public GuidedAction getExpandedAction() {
        return this.mExpandedAction;
    }

    public void onUpdateExpandedViewHolder(ViewHolder avh) {
        if (avh == null) {
            this.mExpandedAction = null;
            this.mActionsGridView.setPruneChild(true);
        } else if (avh.getAction() != this.mExpandedAction) {
            this.mExpandedAction = avh.getAction();
            this.mActionsGridView.setPruneChild(false);
        }
        this.mActionsGridView.setAnimateChildLayout(false);
        int count = this.mActionsGridView.getChildCount();
        for (int i = 0; i < count; i++) {
            VerticalGridView verticalGridView = this.mActionsGridView;
            updateChevronAndVisibility((ViewHolder) verticalGridView.getChildViewHolder(verticalGridView.getChildAt(i)));
        }
    }

    /* access modifiers changed from: package-private */
    public void onUpdateSubActionsGridView(GuidedAction action, boolean expand) {
        VerticalGridView verticalGridView = this.mSubActionsGridView;
        if (verticalGridView != null) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) verticalGridView.getLayoutParams();
            GuidedActionAdapter adapter = (GuidedActionAdapter) this.mSubActionsGridView.getAdapter();
            if (expand) {
                lp.topMargin = -2;
                lp.height = -1;
                this.mSubActionsGridView.setLayoutParams(lp);
                this.mSubActionsGridView.setVisibility(0);
                this.mSubActionsBackground.setVisibility(0);
                this.mSubActionsGridView.requestFocus();
                adapter.setActions(action.getSubActions());
                return;
            }
            lp.topMargin = this.mActionsGridView.getLayoutManager().findViewByPosition(((GuidedActionAdapter) this.mActionsGridView.getAdapter()).indexOf(action)).getBottom();
            lp.height = 0;
            this.mSubActionsGridView.setVisibility(4);
            this.mSubActionsBackground.setVisibility(4);
            this.mSubActionsGridView.setLayoutParams(lp);
            adapter.setActions(Collections.emptyList());
            this.mActionsGridView.requestFocus();
        }
    }

    private void updateChevronAndVisibility(ViewHolder vh) {
        if (!vh.isSubAction()) {
            if (this.mExpandedAction == null) {
                vh.itemView.setVisibility(0);
                vh.itemView.setTranslationY(0.0f);
                if (vh.mActivatorView != null) {
                    vh.setActivated(false);
                }
            } else if (vh.getAction() == this.mExpandedAction) {
                vh.itemView.setVisibility(0);
                if (vh.getAction().hasSubActions()) {
                    vh.itemView.setTranslationY((float) (getKeyLine() - vh.itemView.getBottom()));
                } else if (vh.mActivatorView != null) {
                    vh.itemView.setTranslationY(0.0f);
                    vh.setActivated(true);
                }
            } else {
                vh.itemView.setVisibility(4);
                vh.itemView.setTranslationY(0.0f);
            }
        }
        if (vh.mChevronView != null) {
            onBindChevronView(vh, vh.getAction());
        }
    }

    public void onImeAppearing(@NonNull List<Animator> list) {
    }

    public void onImeDisappearing(@NonNull List<Animator> list) {
    }

    private boolean setIcon(ImageView iconView, GuidedAction action) {
        Drawable icon = null;
        if (iconView != null) {
            icon = action.getIcon();
            if (icon != null) {
                iconView.setImageLevel(icon.getLevel());
                iconView.setImageDrawable(icon);
                iconView.setVisibility(0);
            } else {
                iconView.setVisibility(8);
            }
        }
        if (icon != null) {
            return true;
        }
        return false;
    }

    private int getDescriptionMaxHeight(Context context, TextView title) {
        return (this.mDisplayHeight - (this.mVerticalPadding * 2)) - ((this.mTitleMaxLines * 2) * title.getLineHeight());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements FacetProvider {
        final View.AccessibilityDelegate mDelegate;
        private final boolean mIsSubAction;
        GuidedAction mAction;
        View mActivatorView;
        ImageView mCheckmarkView;
        ImageView mChevronView;
        TextView mDescriptionView;
        int mEditingMode;
        ImageView mIconView;
        Animator mPressAnimator;
        TextView mTitleView;
        private View mContentView;

        public ViewHolder(View v) {
            this(v, false);
        }

        public ViewHolder(View v, boolean isSubAction) {
            super(v);
            this.mEditingMode = 0;
            this.mDelegate = new View.AccessibilityDelegate() {
                public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
                    super.onInitializeAccessibilityEvent(host, event);
                    event.setChecked(ViewHolder.this.mAction != null && ViewHolder.this.mAction.isChecked());
                }

                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    boolean z = true;
                    info.setCheckable((ViewHolder.this.mAction == null || ViewHolder.this.mAction.getCheckSetId() == 0) ? false : true);
                    if (ViewHolder.this.mAction == null || !ViewHolder.this.mAction.isChecked()) {
                        z = false;
                    }
                    info.setChecked(z);
                }
            };
            this.mContentView = v.findViewById(C0364R.C0366id.guidedactions_item_content);
            this.mTitleView = (TextView) v.findViewById(C0364R.C0366id.guidedactions_item_title);
            this.mActivatorView = v.findViewById(C0364R.C0366id.guidedactions_activator_item);
            this.mDescriptionView = (TextView) v.findViewById(C0364R.C0366id.guidedactions_item_description);
            this.mIconView = (ImageView) v.findViewById(C0364R.C0366id.guidedactions_item_icon);
            this.mCheckmarkView = (ImageView) v.findViewById(C0364R.C0366id.guidedactions_item_checkmark);
            this.mChevronView = (ImageView) v.findViewById(C0364R.C0366id.guidedactions_item_chevron);
            this.mIsSubAction = isSubAction;
            v.setAccessibilityDelegate(this.mDelegate);
        }

        public View getContentView() {
            return this.mContentView;
        }

        public TextView getTitleView() {
            return this.mTitleView;
        }

        public EditText getEditableTitleView() {
            TextView textView = this.mTitleView;
            if (textView instanceof EditText) {
                return (EditText) textView;
            }
            return null;
        }

        public TextView getDescriptionView() {
            return this.mDescriptionView;
        }

        public EditText getEditableDescriptionView() {
            TextView textView = this.mDescriptionView;
            if (textView instanceof EditText) {
                return (EditText) textView;
            }
            return null;
        }

        public ImageView getIconView() {
            return this.mIconView;
        }

        public ImageView getCheckmarkView() {
            return this.mCheckmarkView;
        }

        public ImageView getChevronView() {
            return this.mChevronView;
        }

        public boolean isInEditing() {
            return this.mEditingMode != 0;
        }

        public boolean isInEditingText() {
            int i = this.mEditingMode;
            return i == 1 || i == 2;
        }

        public boolean isInEditingTitle() {
            return this.mEditingMode == 1;
        }

        public boolean isInEditingDescription() {
            return this.mEditingMode == 2;
        }

        public boolean isInEditingActivatorView() {
            return this.mEditingMode == 3;
        }

        public View getEditingView() {
            int i = this.mEditingMode;
            if (i == 1) {
                return this.mTitleView;
            }
            if (i == 2) {
                return this.mDescriptionView;
            }
            if (i != 3) {
                return null;
            }
            return this.mActivatorView;
        }

        public boolean isSubAction() {
            return this.mIsSubAction;
        }

        public GuidedAction getAction() {
            return this.mAction;
        }

        /* access modifiers changed from: package-private */
        public void setActivated(boolean activated) {
            this.mActivatorView.setActivated(activated);
            if (this.itemView instanceof GuidedActionItemContainer) {
                ((GuidedActionItemContainer) this.itemView).setFocusOutAllowed(!activated);
            }
        }

        public Object getFacet(Class<?> facetClass) {
            if (facetClass == ItemAlignmentFacet.class) {
                return GuidedActionsStylist.sGuidedActionItemAlignFacet;
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public void press(boolean pressed) {
            Animator animator = this.mPressAnimator;
            if (animator != null) {
                animator.cancel();
                this.mPressAnimator = null;
            }
            int themeAttrId = pressed ? C0364R.attr.guidedActionPressedAnimation : C0364R.attr.guidedActionUnpressedAnimation;
            Context ctx = this.itemView.getContext();
            TypedValue typedValue = new TypedValue();
            if (ctx.getTheme().resolveAttribute(themeAttrId, typedValue, true)) {
                this.mPressAnimator = AnimatorInflater.loadAnimator(ctx, typedValue.resourceId);
                this.mPressAnimator.setTarget(this.itemView);
                this.mPressAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        ViewHolder.this.mPressAnimator = null;
                    }
                });
                this.mPressAnimator.start();
            }
        }
    }
}
