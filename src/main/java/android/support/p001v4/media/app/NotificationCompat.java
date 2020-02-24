package android.support.p001v4.media.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.mediacompat.C0044R;
import android.support.p001v4.app.BundleCompat;
import android.support.p001v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.p001v4.app.NotificationCompat;
import android.support.p001v4.media.session.MediaSessionCompat;
import android.widget.RemoteViews;

/* renamed from: android.support.v4.media.app.NotificationCompat */
public class NotificationCompat {
    private NotificationCompat() {
    }

    /* renamed from: android.support.v4.media.app.NotificationCompat$MediaStyle */
    public static class MediaStyle extends NotificationCompat.Style {
        private static final int MAX_MEDIA_BUTTONS = 5;
        private static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
        int[] mActionsToShowInCompact = null;
        PendingIntent mCancelButtonIntent;
        boolean mShowCancelButton;
        MediaSessionCompat.Token mToken;

        public static MediaSessionCompat.Token getMediaSession(Notification notification) {
            Bundle extras = android.support.p001v4.app.NotificationCompat.getExtras(notification);
            if (extras == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                Object tokenInner = extras.getParcelable(android.support.p001v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
                if (tokenInner != null) {
                    return MediaSessionCompat.Token.fromToken(tokenInner);
                }
                return null;
            }
            IBinder tokenInner2 = BundleCompat.getBinder(extras, android.support.p001v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
            if (tokenInner2 == null) {
                return null;
            }
            Parcel p = Parcel.obtain();
            p.writeStrongBinder(tokenInner2);
            p.setDataPosition(0);
            MediaSessionCompat.Token token = MediaSessionCompat.Token.CREATOR.createFromParcel(p);
            p.recycle();
            return token;
        }

        public MediaStyle() {
        }

        public MediaStyle(NotificationCompat.Builder builder) {
            setBuilder(builder);
        }

        public MediaStyle setShowActionsInCompactView(int... actions) {
            this.mActionsToShowInCompact = actions;
            return this;
        }

        public MediaStyle setMediaSession(MediaSessionCompat.Token token) {
            this.mToken = token;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean show) {
            if (Build.VERSION.SDK_INT < 21) {
                this.mShowCancelButton = show;
            }
            return this;
        }

        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            this.mCancelButtonIntent = pendingIntent;
            return this;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 21) {
                builder.getBuilder().setStyle(fillInMediaStyle(new Notification.MediaStyle()));
            } else if (this.mShowCancelButton) {
                builder.getBuilder().setOngoing(true);
            }
        }

        /* access modifiers changed from: package-private */
        @RequiresApi(21)
        public Notification.MediaStyle fillInMediaStyle(Notification.MediaStyle style) {
            int[] iArr = this.mActionsToShowInCompact;
            if (iArr != null) {
                style.setShowActionsInCompactView(iArr);
            }
            MediaSessionCompat.Token token = this.mToken;
            if (token != null) {
                style.setMediaSession((MediaSession.Token) token.getToken());
            }
            return style;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null;
            }
            return generateContentView();
        }

        /* access modifiers changed from: package-private */
        public RemoteViews generateContentView() {
            int numActionsInCompact;
            RemoteViews view = applyStandardTemplate(false, getContentViewLayoutResource(), true);
            int numActions = this.mBuilder.mActions.size();
            int[] iArr = this.mActionsToShowInCompact;
            if (iArr == null) {
                numActionsInCompact = 0;
            } else {
                numActionsInCompact = Math.min(iArr.length, 3);
            }
            view.removeAllViews(C0044R.C0046id.media_actions);
            if (numActionsInCompact > 0) {
                int i = 0;
                while (i < numActionsInCompact) {
                    if (i < numActions) {
                        view.addView(C0044R.C0046id.media_actions, generateMediaActionButton(this.mBuilder.mActions.get(this.mActionsToShowInCompact[i])));
                        i++;
                    } else {
                        throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(i), Integer.valueOf(numActions - 1)));
                    }
                }
            }
            if (this.mShowCancelButton) {
                view.setViewVisibility(C0044R.C0046id.end_padder, 8);
                view.setViewVisibility(C0044R.C0046id.cancel_action, 0);
                view.setOnClickPendingIntent(C0044R.C0046id.cancel_action, this.mCancelButtonIntent);
                view.setInt(C0044R.C0046id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(C0044R.integer.cancel_button_image_alpha));
            } else {
                view.setViewVisibility(C0044R.C0046id.end_padder, 0);
                view.setViewVisibility(C0044R.C0046id.cancel_action, 8);
            }
            return view;
        }

        private RemoteViews generateMediaActionButton(NotificationCompat.Action action) {
            boolean tombstone = action.getActionIntent() == null;
            RemoteViews button = new RemoteViews(this.mBuilder.mContext.getPackageName(), C0044R.layout.notification_media_action);
            button.setImageViewResource(C0044R.C0046id.action0, action.getIcon());
            if (!tombstone) {
                button.setOnClickPendingIntent(C0044R.C0046id.action0, action.getActionIntent());
            }
            if (Build.VERSION.SDK_INT >= 15) {
                button.setContentDescription(C0044R.C0046id.action0, action.getTitle());
            }
            return button;
        }

        /* access modifiers changed from: package-private */
        public int getContentViewLayoutResource() {
            return C0044R.layout.notification_template_media;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null;
            }
            return generateBigContentView();
        }

        /* access modifiers changed from: package-private */
        public RemoteViews generateBigContentView() {
            int actionCount = Math.min(this.mBuilder.mActions.size(), 5);
            RemoteViews big = applyStandardTemplate(false, getBigContentViewLayoutResource(actionCount), false);
            big.removeAllViews(C0044R.C0046id.media_actions);
            if (actionCount > 0) {
                for (int i = 0; i < actionCount; i++) {
                    big.addView(C0044R.C0046id.media_actions, generateMediaActionButton(this.mBuilder.mActions.get(i)));
                }
            }
            if (this.mShowCancelButton != 0) {
                big.setViewVisibility(C0044R.C0046id.cancel_action, 0);
                big.setInt(C0044R.C0046id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(C0044R.integer.cancel_button_image_alpha));
                big.setOnClickPendingIntent(C0044R.C0046id.cancel_action, this.mCancelButtonIntent);
            } else {
                big.setViewVisibility(C0044R.C0046id.cancel_action, 8);
            }
            return big;
        }

        /* access modifiers changed from: package-private */
        public int getBigContentViewLayoutResource(int actionCount) {
            return actionCount <= 3 ? C0044R.layout.notification_template_big_media_narrow : C0044R.layout.notification_template_big_media;
        }
    }

    /* renamed from: android.support.v4.media.app.NotificationCompat$DecoratedMediaCustomViewStyle */
    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 24) {
                builder.getBuilder().setStyle(fillInMediaStyle(new Notification.DecoratedMediaCustomViewStyle()));
            } else {
                super.apply(builder);
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            boolean createCustomContent = true;
            boolean hasContentView = this.mBuilder.getContentView() != null;
            if (Build.VERSION.SDK_INT >= 21) {
                if (!hasContentView && this.mBuilder.getBigContentView() == null) {
                    createCustomContent = false;
                }
                if (createCustomContent) {
                    RemoteViews contentView = generateContentView();
                    if (hasContentView) {
                        buildIntoRemoteViews(contentView, this.mBuilder.getContentView());
                    }
                    setBackgroundColor(contentView);
                    return contentView;
                }
            } else {
                RemoteViews contentView2 = generateContentView();
                if (hasContentView) {
                    buildIntoRemoteViews(contentView2, this.mBuilder.getContentView());
                    return contentView2;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public int getContentViewLayoutResource() {
            if (this.mBuilder.getContentView() != null) {
                return C0044R.layout.notification_template_media_custom;
            }
            return super.getContentViewLayoutResource();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            RemoteViews innerView;
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            if (this.mBuilder.getBigContentView() != null) {
                innerView = this.mBuilder.getBigContentView();
            } else {
                innerView = this.mBuilder.getContentView();
            }
            if (innerView == null) {
                return null;
            }
            RemoteViews bigContentView = generateBigContentView();
            buildIntoRemoteViews(bigContentView, innerView);
            if (Build.VERSION.SDK_INT >= 21) {
                setBackgroundColor(bigContentView);
            }
            return bigContentView;
        }

        /* access modifiers changed from: package-private */
        public int getBigContentViewLayoutResource(int actionCount) {
            return actionCount <= 3 ? C0044R.layout.notification_template_big_media_narrow_custom : C0044R.layout.notification_template_big_media_custom;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor builder) {
            RemoteViews innerView;
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            if (this.mBuilder.getHeadsUpContentView() != null) {
                innerView = this.mBuilder.getHeadsUpContentView();
            } else {
                innerView = this.mBuilder.getContentView();
            }
            if (innerView == null) {
                return null;
            }
            RemoteViews headsUpContentView = generateBigContentView();
            buildIntoRemoteViews(headsUpContentView, innerView);
            if (Build.VERSION.SDK_INT >= 21) {
                setBackgroundColor(headsUpContentView);
            }
            return headsUpContentView;
        }

        private void setBackgroundColor(RemoteViews views) {
            int color;
            if (this.mBuilder.getColor() != 0) {
                color = this.mBuilder.getColor();
            } else {
                color = this.mBuilder.mContext.getResources().getColor(C0044R.color.notification_material_background_media_default_color);
            }
            views.setInt(C0044R.C0046id.status_bar_latest_event_content, "setBackgroundColor", color);
        }
    }
}
