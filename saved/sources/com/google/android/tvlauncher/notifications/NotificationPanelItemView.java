package com.google.android.tvlauncher.notifications;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

public class NotificationPanelItemView extends LinearLayout {
    private boolean mAccessibilityEnabled;
    private int mDescriptionTextMaxWidth;
    private NotificationDismissButton mDismissButton;
    protected EventLogger mEventLogger;
    private ImageView mIcon;
    private boolean mIsDismissible;
    private boolean mIsRtl;
    private View mItemContainer;
    private int mItemContainerDismissButtonFocusedMarginStart;
    protected View mMainContainer;
    private int mMeasuredTextWidth;
    private TvNotification mNotification;
    protected String mNotificationKey;
    private int mProgress;
    private RectF mProgressBounds;
    private int mProgressColor;
    private int mProgressDiameter;
    private int mProgressMax;
    private int mProgressMaxColor;
    private Paint mProgressMaxPaint;
    private int mProgressPaddingStart;
    private int mProgressPaddingTop;
    private Paint mProgressPaint;
    private int mProgressStrokeWidth;
    private TextView mText;
    private TextView mTitle;

    public NotificationPanelItemView(Context context) {
        super(context);
    }

    public NotificationPanelItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mIcon = (ImageView) findViewById(C1188R.C1191id.notification_icon);
        this.mTitle = (TextView) findViewById(C1188R.C1191id.notification_title);
        this.mText = (TextView) findViewById(C1188R.C1191id.notification_text);
        this.mMainContainer = findViewById(C1188R.C1191id.main_container);
        this.mItemContainer = findViewById(C1188R.C1191id.item_container);
        this.mDismissButton = (NotificationDismissButton) findViewById(C1188R.C1191id.dismiss_button);
        this.mIsRtl = isRtl();
        this.mAccessibilityEnabled = Util.isAccessibilityEnabled(getContext());
        Resources res = getResources();
        this.mProgressStrokeWidth = res.getDimensionPixelSize(C1188R.dimen.notification_progress_stroke_width);
        this.mProgressColor = res.getColor(C1188R.color.notification_progress_stroke_color, null);
        this.mProgressMaxColor = res.getColor(C1188R.color.notification_progress_stroke_max_color, null);
        this.mProgressDiameter = res.getDimensionPixelSize(C1188R.dimen.notification_progress_circle_size);
        this.mProgressPaddingTop = res.getDimensionPixelOffset(C1188R.dimen.notification_progress_circle_padding_top);
        this.mProgressPaddingStart = res.getDimensionPixelOffset(C1188R.dimen.notification_progress_circle_padding_start);
        this.mProgressPaint = new Paint();
        this.mProgressPaint.setAntiAlias(true);
        this.mProgressPaint.setStyle(Paint.Style.STROKE);
        this.mProgressPaint.setColor(this.mProgressColor);
        this.mProgressPaint.setStrokeWidth((float) this.mProgressStrokeWidth);
        this.mDescriptionTextMaxWidth = res.getDimensionPixelSize(C1188R.dimen.notification_panel_item_text_width);
        this.mProgressMaxPaint = new Paint();
        this.mProgressMaxPaint.setAntiAlias(true);
        this.mProgressMaxPaint.setStyle(Paint.Style.STROKE);
        this.mProgressMaxPaint.setColor(this.mProgressMaxColor);
        this.mProgressMaxPaint.setStrokeWidth((float) this.mProgressStrokeWidth);
        this.mItemContainerDismissButtonFocusedMarginStart = res.getDimensionPixelSize(C1188R.dimen.notification_panel_item_dismiss_focus_margin_start);
        this.mMainContainer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (NotificationPanelItemView.this.mNotificationKey != null) {
                    NotificationsUtils.openNotification(NotificationPanelItemView.this.getContext(), NotificationPanelItemView.this.mNotificationKey);
                    NotificationPanelItemView.this.logClickEvent(TvlauncherLogEnum.TvLauncherEventCode.OPEN_NOTIFICATION);
                }
            }
        });
    }

    public boolean isRtl() {
        return getContext().getResources().getConfiguration().getLayoutDirection() == 1;
    }

    public void setNotification(TvNotification notif, EventLogger logger) {
        this.mEventLogger = logger;
        this.mNotification = notif;
        this.mNotificationKey = notif.getNotificationKey();
        this.mTitle.setText(notif.getTitle());
        this.mText.setText(notif.getText());
        this.mIsDismissible = notif.isDismissible() && !notif.isOngoing();
        this.mDismissButton.setVisibility(this.mIsDismissible ? 0 : 8);
        this.mDismissButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NotificationsUtils.dismissNotification(NotificationPanelItemView.this.getContext(), NotificationPanelItemView.this.mNotificationKey);
                NotificationPanelItemView.this.logClickEvent(TvlauncherLogEnum.TvLauncherEventCode.DISMISS_NOTIFICATION);
            }
        });
        if (TextUtils.isEmpty(notif.getTitle())) {
            this.mMainContainer.setContentDescription(notif.getText());
        } else if (!TextUtils.isEmpty(notif.getText())) {
            this.mMainContainer.setContentDescription(String.format(getResources().getString(C1188R.string.notification_content_description_format), notif.getTitle(), notif.getText()));
        } else {
            this.mMainContainer.setContentDescription(notif.getTitle());
        }
        this.mIcon.setImageIcon(notif.getSmallIcon());
        setProgress(notif.getProgress(), notif.getProgressMax());
        this.mMainContainer.setVisibility(0);
        measureLength();
        this.mMeasuredTextWidth = this.mText.getMeasuredWidth();
        bind();
    }

    @VisibleForTesting(otherwise = 2)
    public void measureLength() {
        this.mText.measure(0, 0);
    }

    public TextView getTitleView() {
        return this.mTitle;
    }

    public TextView getTextView() {
        return this.mText;
    }

    public void setProgress(int progress, int progressMax) {
        this.mProgress = progress;
        this.mProgressMax = progressMax;
        if (this.mProgressMax != 0) {
            if (this.mProgressBounds == null) {
                this.mProgressBounds = new RectF();
            }
            setWillNotDraw(false);
        } else {
            this.mProgressBounds = null;
            setWillNotDraw(true);
        }
        requestLayout();
    }

    /* JADX INFO: Multiple debug info for r3v2 int: [D('right' int), D('left' int)] */
    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int right;
        int left;
        super.onLayout(changed, l, t, r, b);
        if (this.mProgressBounds != null) {
            int top = this.mProgressPaddingTop;
            int i = this.mProgressDiameter;
            int bottom = top + i;
            if (this.mIsRtl) {
                right = r - this.mProgressPaddingStart;
                left = right - i;
            } else {
                int left2 = this.mProgressPaddingStart;
                int i2 = left2;
                right = i + left2;
                left = i2;
            }
            this.mProgressBounds.set((float) left, (float) top, (float) right, (float) bottom);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.mProgressMax;
        if (i != 0) {
            float sweepAngle = (((float) this.mProgress) * 360.0f) / ((float) i);
            if (this.mIsRtl) {
                canvas.drawArc(this.mProgressBounds, -90.0f, -sweepAngle, false, this.mProgressPaint);
                canvas.drawArc(this.mProgressBounds, -90.0f, 360.0f - sweepAngle, false, this.mProgressMaxPaint);
                return;
            }
            canvas.drawArc(this.mProgressBounds, -90.0f, sweepAngle, false, this.mProgressPaint);
            canvas.drawArc(this.mProgressBounds, sweepAngle - 90.0f, 360.0f - sweepAngle, false, this.mProgressMaxPaint);
        }
    }

    private boolean isContentTextCutOff() {
        return this.mMeasuredTextWidth > this.mDescriptionTextMaxWidth;
    }

    /* access modifiers changed from: protected */
    public void expandText() {
        this.mText.setMaxLines(Integer.MAX_VALUE);
        this.mTitle.setMaxLines(2);
        setBackgroundColor(getResources().getColor(C1188R.color.notification_expanded_text_background));
    }

    /* access modifiers changed from: protected */
    public void collapseText() {
        this.mTitle.setMaxLines(1);
        this.mText.setMaxLines(1);
        setBackgroundColor(0);
    }

    public void bind() {
        View currentFocus = getFocusedChild();
        if (currentFocus == null || !isContentTextCutOff()) {
            collapseText();
        } else {
            expandText();
        }
        boolean z = false;
        if (this.mDismissButton.hasFocus()) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mItemContainer.getLayoutParams();
            params.setMarginStart(this.mItemContainerDismissButtonFocusedMarginStart);
            this.mItemContainer.setLayoutParams(params);
            this.mItemContainer.setAlpha(0.4f);
        } else {
            ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) this.mItemContainer.getLayoutParams();
            params2.setMarginStart(0);
            this.mItemContainer.setLayoutParams(params2);
            this.mItemContainer.setAlpha(1.0f);
        }
        NotificationDismissButton notificationDismissButton = this.mDismissButton;
        if (currentFocus != null) {
            z = true;
        }
        notificationDismissButton.bind(z);
    }

    /* access modifiers changed from: protected */
    public void logClickEvent(TvlauncherLogEnum.TvLauncherEventCode eventCode) {
        LogEvent event = new ClickEvent(eventCode).setVisualElementTag(TvLauncherConstants.NOTIFICATION);
        event.getNotification().setPackageName(this.mNotification.getPackageName()).setImportance(LogEvent.notificationImportance(this.mNotification.getChannel()));
        if (!TextUtils.isEmpty(this.mNotification.getTitle())) {
            event.getNotification().setSummary(this.mNotification.getTitle());
        }
        this.mEventLogger.log(event);
    }

    public View focusSearch(View focused, int direction) {
        boolean z = true;
        if (getResources().getConfiguration().getLayoutDirection() != 1) {
            z = false;
        }
        boolean isRTL = z;
        if (this.mAccessibilityEnabled) {
            if (focused.getId() == C1188R.C1191id.main_container && this.mIsDismissible && direction == 130) {
                return this.mDismissButton;
            }
            if (focused.getId() == C1188R.C1191id.dismiss_button && direction == 33) {
                this.mDismissButton.clearFocus();
                return this.mMainContainer;
            }
        } else if (focused.getId() == C1188R.C1191id.dismiss_button && ((isRTL && direction == 66) || (!isRTL && direction == 17))) {
            return this.mMainContainer;
        } else {
            if (focused.getId() == C1188R.C1191id.main_container && (((isRTL && direction == 17) || (!isRTL && direction == 66)) && this.mIsDismissible)) {
                return this.mDismissButton;
            }
        }
        return super.focusSearch(focused, direction);
    }

    public View getItemContainer() {
        return this.mItemContainer;
    }
}
