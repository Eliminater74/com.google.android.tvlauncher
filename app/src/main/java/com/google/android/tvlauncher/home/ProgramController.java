package com.google.android.tvlauncher.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.tvlauncher.BackHomeControllerListeners;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.application.TvLauncherApplicationBase;
import com.google.android.tvlauncher.data.TvDataManager;
import com.google.android.tvlauncher.home.util.ImageViewTargetWithTrace;
import com.google.android.tvlauncher.home.util.ProgramPreviewImageData;
import com.google.android.tvlauncher.home.util.ProgramPreviewImageTranscoder;
import com.google.android.tvlauncher.home.util.ProgramSettings;
import com.google.android.tvlauncher.home.util.ProgramUtil;
import com.google.android.tvlauncher.home.view.ProgramView;
import com.google.android.tvlauncher.instantvideo.widget.InstantVideoView;
import com.google.android.tvlauncher.model.Program;
import com.google.android.tvlauncher.util.AddBackgroundColorTransformation;
import com.google.android.tvlauncher.util.ContextMenu;
import com.google.android.tvlauncher.util.ContextMenuItem;
import com.google.android.tvlauncher.util.IntentLaunchDispatcher;
import com.google.android.tvlauncher.util.LauncherAudioPlayer;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.util.ScaleFocusHandler;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvlauncher.util.palette.PaletteBitmapContainer;
import com.google.android.tvrecommendations.shared.util.Constants;
import com.google.logs.tvlauncher.config.TvLauncherConstants;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class ProgramController implements View.OnClickListener, ContextMenu.OnItemClickListener, View.OnLongClickListener, ProgramView.OnWindowVisibilityChangedListener, BackHomeControllerListeners.OnHomePressedListener {
    static final boolean ENABLE_DOUBLE_CLICK_ADS = true;
    @VisibleForTesting
    static final int MENU_ADD_TO_WATCH_NEXT = 2;
    @VisibleForTesting
    static final int MENU_PRIMARY_ACTION = 1;
    @VisibleForTesting
    static final int MENU_REMOVE_PREVIEW_PROGRAM = 3;
    private static final boolean DEBUG = false;
    private static final double EPS = 0.001d;
    private static final long LIVE_PLAYBACK_PROGRESS_UPDATE_INTERVAL_MS = 60000;
    private static final int MENU_REMOVE_FROM_WATCH_NEXT = 4;
    private static final int PREVIEW_IMAGE_FADE_DURATION_MILLIS = 300;
    private static final int PREVIEW_MEDIA_START_DELAY_MILLIS = 1250;
    private static final int SCALING_ERROR_MARGIN = 20;
    private static final int STATE_PLAYBACK_ENDED = 1;
    private static final int STATE_PLAYBACK_ERROR = 2;
    private static final int STATE_PLAYBACK_STOPPED = 0;
    private static final String TAG = "ProgramController";
    private static boolean sPreviewImageTranscoderRegistered = false;
    /* access modifiers changed from: private */
    public final View mPreviewAudioContainer;
    /* access modifiers changed from: private */
    public final View mPreviewDelayOverlay;
    /* access modifiers changed from: private */
    public final InstantVideoView mPreviewVideo;
    /* access modifiers changed from: private */
    public final ImageView mThumbnail;
    /* access modifiers changed from: private */
    public final ProgramView mView;
    private final MeasureFormat mA11yDurationFormat;
    private final EventLogger mEventLogger;
    private final RequestOptions mImageRequestOptions;
    private final ColorStateList mLiveProgressBarForegroundColor;
    private final int mPreviewImageExpandedVerticalMargin;
    private final int mProgramDefaultBackgroundColor;
    private final ColorDrawable mProgramDefaultBackgroundDrawable;
    private final String mProgramMenuAddToWatchNextNotAvailableText;
    private final String mProgramMenuAddToWatchNextText;
    private final String mProgramMenuAlreadyInWatchNextText;
    private final String mProgramMenuRemoveText;
    private final ProgramSettings mProgramSettings;
    private final ColorStateList mWatchNextProgressBarForegroundColor;
    /* access modifiers changed from: private */
    public BitmapDrawable mBlurredPreviewImageDrawable;
    /* access modifiers changed from: private */
    public boolean mIsWatchNextProgram;
    /* access modifiers changed from: private */
    public LauncherAudioPlayer mLauncherAudioPlayer;
    /* access modifiers changed from: private */
    public OnProgramViewFocusChangedListener mOnProgramViewFocusChangedListener;
    /* access modifiers changed from: private */
    public String mPreviewAudioUri;
    /* access modifiers changed from: private */
    public Palette mPreviewImagePalette;
    /* access modifiers changed from: private */
    public String mPreviewVideoUri;
    /* access modifiers changed from: private */
    public ContextMenu mProgramMenu;
    /* access modifiers changed from: private */
    public boolean mProgramSelected;
    /* access modifiers changed from: private */
    public ProgramViewLiveProgressUpdateCallback mProgramViewLiveProgressUpdateCallback;
    /* access modifiers changed from: private */
    public SponsoredProgramControllerHelper mSponsoredProgramControllerHelper;
    /* access modifiers changed from: private */
    public long mStartedPreviewVideoMillis;
    /* access modifiers changed from: private */
    public String mThumbnailUri;
    /* access modifiers changed from: private */
    public InstantVideoView.VideoCallback mVideoCallback;
    @VisibleForTesting
    View.OnFocusChangeListener mOnFocusChangeListener;
    private String mActionUri;
    private boolean mCanAddToWatchNext;
    private boolean mCanRemoveProgram;
    private long mChannelId;
    private String mContentId;
    private String mDebugTitle;
    private ScaleFocusHandler mFocusHandler;
    private Double mFocusedAspectRatio;
    private RecyclerViewStateProvider mHomeListStateProvider;
    private IntentLaunchDispatcher mIntentLauncher;
    private boolean mIsLegacy;
    private boolean mIsSponsored;
    private boolean mIsSponsoredBranded;
    private RecyclerViewStateProvider mListStateProvider;
    private Runnable mLiveProgressUpdateRunnable;
    private String mLogoContentDescription;
    private String mLogoUri;
    private BackHomeControllerListeners.OnHomeNotHandledListener mOnHomeNotHandledListener;
    private ImageViewTargetWithTrace<ProgramPreviewImageData> mPreviewImageBlurGlideTarget;
    private ValueAnimator mPreviewImageFadeInAnimator;
    private Animator.AnimatorListener mPreviewImageFadeInAnimatorListener;
    private ValueAnimator mPreviewImageFadeOutAnimator;
    private Animator.AnimatorListener mPreviewImageFadeOutAnimatorListener;
    private ValueAnimator.AnimatorUpdateListener mPreviewImageFadeUpdateListener;
    private boolean mPreviewImageNeedsTreatment;
    private ImageViewTargetWithTrace<PaletteBitmapContainer> mPreviewImagePaletteGlideTarget;
    private float mPreviewImageVisibilityValue;
    private SharedPreferences mPreviewMediaPref;
    private long mProgramDuration;
    private long mProgramId;
    private boolean mProgramIsLive;
    private long mProgramLiveEndTime;
    private long mProgramLiveStartTime;
    private String mProgramPackageName;
    private int mProgramState;
    private int mProgramType;
    private String mSponsoredProgramContentDescription;
    private Runnable mStartPreviewAudioRunnable;
    private Runnable mStartPreviewVideoRunnable;
    private ImageViewTargetWithTrace<Drawable> mThumbnailImageGlideTarget;
    private Double mUnfocusedAspectRatio;
    private int mWatchedPreviewVideoSeconds;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    ProgramController(ProgramView v, EventLogger eventLogger, boolean isSponsored, boolean isSponsoredBranded) {
        this(v, eventLogger, isSponsored, isSponsoredBranded, isSponsored ? new SponsoredProgramControllerHelper(v) : null);
    }

    @VisibleForTesting
    ProgramController(ProgramView v, EventLogger eventLogger, boolean isSponsored, boolean isSponsoredBranded, SponsoredProgramControllerHelper sponsoredProgramControllerHelper) {
        ProgramSettings programSettings;
        this.mIsWatchNextProgram = false;
        this.mPreviewImageVisibilityValue = 1.0f;
        this.mVideoCallback = new InstantVideoView.VideoCallback() {
            public void onVideoStarted(InstantVideoView view) {
                long unused = ProgramController.this.mStartedPreviewVideoMillis = SystemClock.elapsedRealtime();
                ProgramController.this.fadePreviewImageOut();
                ProgramController.this.mPreviewDelayOverlay.setVisibility(4);
                if (ProgramController.this.mSponsoredProgramControllerHelper != null) {
                    ProgramController.this.mSponsoredProgramControllerHelper.onVideoStarted();
                }
            }

            public void onVideoEnded(InstantVideoView view) {
                ProgramController.this.stopPreviewVideo(true, 1);
            }

            public void onVideoError(InstantVideoView view) {
                String valueOf = String.valueOf(view.getVideoUri());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
                sb.append("onVideoError: uri=[");
                sb.append(valueOf);
                sb.append("]");
                Log.e(ProgramController.TAG, sb.toString());
                ProgramController.this.stopPreviewVideo(true, 2);
            }
        };
        this.mStartPreviewAudioRunnable = new Runnable() {
            public void run() {
                if (!ProgramController.this.mIsWatchNextProgram && ProgramController.this.mPreviewAudioUri != null && ProgramController.this.mProgramSelected) {
                    ProgramController.this.mLauncherAudioPlayer.setCallBacks(new LauncherAudioPlayer.CallBacks() {
                        public void onStarted() {
                            ProgramController.this.mPreviewDelayOverlay.setVisibility(4);
                            ProgramController.this.mPreviewAudioContainer.setVisibility(0);
                        }

                        public void onCompleted() {
                            ProgramController.this.stopPreviewAudio();
                        }

                        public void onError() {
                            ProgramController.this.mPreviewDelayOverlay.setVisibility(4);
                            ProgramController.this.stopPreviewAudio();
                        }

                        public void onPrepared() {
                            if (ProgramController.this.mProgramSelected) {
                                ProgramController.this.mLauncherAudioPlayer.start();
                            }
                        }
                    });
                    ProgramController.this.mLauncherAudioPlayer.prepare();
                }
            }
        };
        this.mStartPreviewVideoRunnable = new Runnable() {
            public void run() {
                if (!ProgramController.this.mIsWatchNextProgram && ProgramController.this.mPreviewVideoUri != null) {
                    if (ProgramController.this.mProgramSelected) {
                        ProgramController.this.mPreviewVideo.setVisibility(0);
                        if (!ProgramController.this.allowPreviewAudioPlaying()) {
                            ProgramController.this.mPreviewVideo.setVolume(0.0f);
                        }
                        ProgramController.this.mPreviewVideo.start(ProgramController.this.mVideoCallback);
                        return;
                    }
                    ProgramController.this.startPreviewVideoDelayed();
                }
            }
        };
        this.mLiveProgressUpdateRunnable = new Runnable() {
            public void run() {
                if (ProgramController.this.mProgramViewLiveProgressUpdateCallback != null) {
                    ProgramController.this.mProgramViewLiveProgressUpdateCallback.updateProgramViewLiveProgress();
                }
            }
        };
        this.mOnFocusChangeListener = new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (ProgramController.this.mProgramMenu != null && ProgramController.this.mProgramMenu.isShowing()) {
                    ProgramController.this.mProgramMenu.forceDismiss();
                }
                if (!ProgramController.this.mIsWatchNextProgram) {
                    int i = 0;
                    if (ProgramController.this.mPreviewVideoUri == null) {
                        if (ProgramController.this.mThumbnailUri != null) {
                            ProgramController.this.mThumbnail.setVisibility(hasFocus ? 0 : 8);
                            View previewImageContainer = ProgramController.this.mView.getPreviewImageContainer();
                            if (hasFocus) {
                                i = 8;
                            }
                            previewImageContainer.setVisibility(i);
                        }
                        if (ProgramController.this.mPreviewAudioUri != null) {
                            if (!hasFocus || !v.hasWindowFocus() || ActiveMediaSessionManager.getInstance(v.getContext()).hasActiveMediaSession() || !ProgramController.this.allowPreviewAudioPlaying()) {
                                ProgramController.this.stopPreviewAudio();
                            } else {
                                ProgramController.this.startPreviewAudioDelayed();
                            }
                        }
                    } else if (!hasFocus || !v.hasWindowFocus() || ActiveMediaSessionManager.getInstance(v.getContext()).hasActiveMediaSession() || !ProgramController.this.allowPreviewVideoPlaying()) {
                        ProgramController.this.stopPreviewVideo(true, 0);
                    } else {
                        ProgramController.this.startPreviewVideoDelayed();
                    }
                }
                if (ProgramController.this.mOnProgramViewFocusChangedListener != null) {
                    ProgramController.this.mOnProgramViewFocusChangedListener.onProgramViewFocusChanged(hasFocus);
                }
            }
        };
        this.mView = v;
        this.mEventLogger = eventLogger;
        this.mIsSponsored = isSponsored;
        this.mIsSponsoredBranded = isSponsoredBranded;
        this.mSponsoredProgramControllerHelper = sponsoredProgramControllerHelper;
        Context context = this.mView.getContext();
        this.mIntentLauncher = ((TvLauncherApplicationBase) context.getApplicationContext()).getIntentLauncher();
        v.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) view.getResources().getDimensionPixelSize(C1188R.dimen.card_rounded_corner_radius));
            }
        });
        v.setClipToOutline(true);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        this.mView.setOnWindowVisibilityChangedListener(this);
        this.mPreviewMediaPref = context.getSharedPreferences(TvDataManager.PREVIEW_MEDIA_PREF_FILE_NAME, 0);
        if (this.mIsSponsored) {
            programSettings = ProgramUtil.getSponsoredProgramSettings(v.getContext());
        } else {
            programSettings = ProgramUtil.getProgramSettings(v.getContext());
        }
        this.mProgramSettings = programSettings;
        if (!sPreviewImageTranscoderRegistered) {
            Glide.get(context).getRegistry().register(Bitmap.class, ProgramPreviewImageData.class, new ProgramPreviewImageTranscoder(context));
            sPreviewImageTranscoderRegistered = true;
        }
        this.mPreviewVideo = (InstantVideoView) v.findViewById(C1188R.C1191id.preview_video_view);
        this.mPreviewVideo.setImageViewEnabled(false);
        this.mPreviewAudioContainer = v.findViewById(C1188R.C1191id.preview_audio_container);
        this.mThumbnail = (ImageView) v.findViewById(C1188R.C1191id.thumbnail);
        this.mA11yDurationFormat = MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE);
        this.mPreviewDelayOverlay = v.findViewById(C1188R.C1191id.preview_delay_overlay);
        if (Util.areHomeScreenAnimationsEnabled(context)) {
            this.mView.setOnFocusChangeListener(this.mOnFocusChangeListener);
        } else {
            this.mFocusHandler = new ScaleFocusHandler(this.mProgramSettings.focusedAnimationDuration, this.mProgramSettings.focusedScale, this.mProgramSettings.focusedElevation, 1);
            this.mFocusHandler.setView(v);
            this.mFocusHandler.setOnFocusChangeListener(this.mOnFocusChangeListener);
        }
        this.mProgramDefaultBackgroundColor = context.getColor(C1188R.color.program_default_background);
        this.mProgramDefaultBackgroundDrawable = new ColorDrawable(this.mProgramDefaultBackgroundColor);
        this.mPreviewImageExpandedVerticalMargin = this.mView.getResources().getDimensionPixelOffset(C1188R.dimen.program_preview_image_expanded_vertical_margin);
        int maxHeight = this.mProgramSettings.selectedHeight;
        double d = (double) this.mProgramSettings.selectedHeight;
        Double.isNaN(d);
        this.mImageRequestOptions = (RequestOptions) ((RequestOptions) ((RequestOptions) new RequestOptions().override(((int) (d * 1.7777777777777777d)) + 20, maxHeight)).centerInside()).transform(new AddBackgroundColorTransformation(this.mProgramDefaultBackgroundColor, false));
        this.mProgramMenuAddToWatchNextText = context.getString(C1188R.string.program_menu_add_to_play_next_text);
        this.mProgramMenuRemoveText = context.getString(C1188R.string.program_menu_remove_text);
        this.mProgramMenuAddToWatchNextNotAvailableText = context.getString(C1188R.string.program_menu_add_to_play_next_not_available_text);
        this.mProgramMenuAlreadyInWatchNextText = context.getString(C1188R.string.program_menu_already_in_play_next_text);
        this.mLiveProgressBarForegroundColor = ColorStateList.valueOf(this.mView.getContext().getColor(C1188R.color.program_playback_live_progress_bar_foreground_color));
        this.mWatchNextProgressBarForegroundColor = ColorStateList.valueOf(this.mView.getContext().getColor(C1188R.color.program_playback_watch_next_progress_bar_foreground_color));
        if (!this.mIsSponsored) {
            return;
        }
        if (this.mIsSponsoredBranded) {
            this.mSponsoredProgramContentDescription = context.getString(C1188R.string.sponsored_channel_branding, "Google Play");
            return;
        }
        this.mSponsoredProgramContentDescription = context.getString(C1188R.string.sponsored_channel_unbranded_logo_title);
    }

    /* access modifiers changed from: private */
    public boolean allowPreviewVideoPlaying() {
        return !this.mIsLegacy && this.mPreviewMediaPref.getBoolean(TvDataManager.ENABLE_PREVIEW_VIDEO_KEY, true);
    }

    /* access modifiers changed from: private */
    public boolean allowPreviewAudioPlaying() {
        return !this.mIsLegacy && this.mPreviewMediaPref.getBoolean(TvDataManager.ENABLE_PREVIEW_AUDIO_KEY, true);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public View getPreviewDelayOverlay() {
        return this.mPreviewDelayOverlay;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public ContextMenu getProgramMenu() {
        return this.mProgramMenu;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setProgramMenu(ContextMenu contextMenu) {
        this.mProgramMenu = contextMenu;
    }

    /* access modifiers changed from: package-private */
    public void stopPreviewMedia() {
        if (this.mPreviewVideoUri != null) {
            stopPreviewVideo(true, 0);
        }
        if (this.mPreviewAudioUri != null) {
            stopPreviewAudio();
        }
    }

    /* access modifiers changed from: private */
    public void startPreviewAudioDelayed() {
        this.mView.removeCallbacks(this.mStartPreviewAudioRunnable);
        this.mView.postDelayed(this.mStartPreviewAudioRunnable, 1250);
        this.mPreviewDelayOverlay.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void stopPreviewAudio() {
        this.mView.removeCallbacks(this.mStartPreviewAudioRunnable);
        LauncherAudioPlayer launcherAudioPlayer = this.mLauncherAudioPlayer;
        if (launcherAudioPlayer != null) {
            launcherAudioPlayer.stopAndRelease();
        }
        this.mPreviewDelayOverlay.setVisibility(4);
        this.mPreviewAudioContainer.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void startPreviewVideoDelayed() {
        this.mView.removeCallbacks(this.mStartPreviewVideoRunnable);
        this.mView.postDelayed(this.mStartPreviewVideoRunnable, 1250);
        this.mPreviewDelayOverlay.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void finishStoppingPreviewVideo(int videoPlaybackStoppedState) {
        SponsoredProgramControllerHelper sponsoredProgramControllerHelper = this.mSponsoredProgramControllerHelper;
        if (sponsoredProgramControllerHelper != null) {
            if (videoPlaybackStoppedState == 0) {
                sponsoredProgramControllerHelper.onVideoStopped();
            } else if (videoPlaybackStoppedState == 1) {
                sponsoredProgramControllerHelper.onVideoEnded();
            } else if (videoPlaybackStoppedState == 2) {
                sponsoredProgramControllerHelper.onVideoError();
            }
        }
        this.mPreviewVideo.setVisibility(8);
        this.mPreviewVideo.stop();
    }

    /* access modifiers changed from: private */
    public void stopPreviewVideo(boolean animated, int videoPlaybackStoppedState) {
        logStopVideo();
        this.mView.removeCallbacks(this.mStartPreviewVideoRunnable);
        this.mPreviewDelayOverlay.setVisibility(4);
        if (this.mPreviewImageVisibilityValue == 1.0f) {
            finishStoppingPreviewVideo(videoPlaybackStoppedState);
        } else if (animated) {
            fadePreviewImageIn(videoPlaybackStoppedState);
        } else {
            this.mView.getPreviewImageContainer().setVisibility(0);
            setPreviewImageVisibilityValue(1.0f);
            finishStoppingPreviewVideo(videoPlaybackStoppedState);
        }
    }

    private void logStopVideo() {
        if (this.mStartedPreviewVideoMillis != 0) {
            this.mWatchedPreviewVideoSeconds = (int) ((SystemClock.elapsedRealtime() - this.mStartedPreviewVideoMillis) / 1000);
            if (this.mWatchedPreviewVideoSeconds > 0) {
                LogEvent event = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.WATCH_PREVIEW);
                event.getProgram().setPreview(TvlauncherClientLog.Program.Preview.newBuilder().setPlayedTimestamp(this.mStartedPreviewVideoMillis).setPlayedDurationSeconds(this.mWatchedPreviewVideoSeconds));
                this.mEventLogger.log(event);
            }
            this.mStartedPreviewVideoMillis = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void setListStateProvider(RecyclerViewStateProvider listStateProvider) {
        this.mListStateProvider = listStateProvider;
    }

    /* access modifiers changed from: package-private */
    public void setHomeListStateProvider(RecyclerViewStateProvider homeListStateProvider) {
        this.mHomeListStateProvider = homeListStateProvider;
    }

    /* access modifiers changed from: package-private */
    public void setOnProgramViewFocusChangedListener(OnProgramViewFocusChangedListener listener) {
        this.mOnProgramViewFocusChangedListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void setProgramViewLiveProgressUpdateCallback(ProgramViewLiveProgressUpdateCallback callback) {
        this.mProgramViewLiveProgressUpdateCallback = callback;
    }

    /* access modifiers changed from: package-private */
    public void setOnHomeNotHandledListener(BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener) {
        this.mOnHomeNotHandledListener = onHomeNotHandledListener;
    }

    /* access modifiers changed from: package-private */
    public void setIsWatchNextProgram(boolean isWatchNextProgram) {
        this.mIsWatchNextProgram = isWatchNextProgram;
    }

    /* access modifiers changed from: package-private */
    public void bind(Program program, String packageName, int programState, boolean canAddToWatchNext, boolean canRemoveProgram, boolean isLegacy) {
        Program program2 = program;
        ContextMenu contextMenu = this.mProgramMenu;
        if (contextMenu != null && contextMenu.isShowing()) {
            this.mProgramMenu.forceDismiss();
        }
        this.mProgramPackageName = this.mIsWatchNextProgram ? program.getPackageName() : packageName;
        updateProgramStateAndSelection(programState);
        this.mCanAddToWatchNext = canAddToWatchNext;
        this.mCanRemoveProgram = canRemoveProgram;
        this.mIsLegacy = isLegacy;
        this.mProgramId = program.getId();
        this.mChannelId = program.getChannelId();
        this.mContentId = program.getContentId();
        this.mProgramType = program.getType();
        int i = this.mProgramType;
        if ((i == 1002 || i == 1001) && !this.mIsSponsored) {
            this.mProgramType = 4;
        }
        boolean dataReadyForBinding = true;
        int i2 = this.mProgramType;
        if (i2 == 1002) {
            dataReadyForBinding = this.mSponsoredProgramControllerHelper.bindDoubleClickAdProgram(program2);
        } else if (i2 == 1001) {
            this.mSponsoredProgramControllerHelper.bindDirectAdProgram(program2);
        }
        this.mPreviewVideoUri = program.getPreviewVideoUri();
        String currentPreviewVideoUri = this.mPreviewVideo.getVideoUri() != null ? this.mPreviewVideo.getVideoUri().toString() : null;
        if (currentPreviewVideoUri != null && !currentPreviewVideoUri.equals(this.mPreviewVideoUri)) {
            stopPreviewVideo(false, 0);
        }
        String str = this.mPreviewVideoUri;
        if (str != null) {
            this.mPreviewVideo.setVideoUri(Uri.parse(str));
        } else {
            this.mPreviewVideo.setVideoUri(null);
        }
        int i3 = this.mProgramType;
        if (i3 == 7 || i3 == 8 || i3 == 9 || i3 == 10 || i3 == 11) {
            this.mPreviewAudioUri = program.getPreviewAudioUri();
        } else {
            this.mPreviewAudioUri = null;
        }
        if (this.mPreviewAudioUri != null) {
            if (this.mLauncherAudioPlayer == null) {
                this.mLauncherAudioPlayer = new LauncherAudioPlayer();
            }
            if (!this.mPreviewAudioUri.equals(this.mLauncherAudioPlayer.getDataSource())) {
                if (this.mLauncherAudioPlayer.getDataSource() != null) {
                    stopPreviewAudio();
                }
                this.mLauncherAudioPlayer.setDataSource(this.mPreviewAudioUri);
            }
        } else {
            LauncherAudioPlayer launcherAudioPlayer = this.mLauncherAudioPlayer;
            if (launcherAudioPlayer != null) {
                launcherAudioPlayer.setDataSource(null);
            }
        }
        this.mWatchedPreviewVideoSeconds = 0;
        this.mActionUri = program.getActionUri();
        this.mProgramDuration = program.getDuration();
        this.mProgramIsLive = program.isLive();
        long j = 0;
        this.mProgramLiveStartTime = this.mProgramIsLive ? program.getLiveStartTime() : 0;
        if (this.mProgramIsLive) {
            j = program.getLiveEndTime();
        }
        this.mProgramLiveEndTime = j;
        this.mUnfocusedAspectRatio = Double.valueOf(ProgramUtil.getAspectRatio(program.getPreviewImageAspectRatio()));
        this.mThumbnailUri = null;
        this.mFocusedAspectRatio = null;
        if (!this.mIsWatchNextProgram) {
            if (this.mPreviewVideoUri != null) {
                int videoWidth = program.getVideoWidth();
                int videoHeight = program.getVideoHeight();
                if (videoWidth > 0 && videoHeight > 0) {
                    double d = (double) videoWidth;
                    double d2 = (double) videoHeight;
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    double ratio = d / d2;
                    if (ratio < this.mUnfocusedAspectRatio.doubleValue()) {
                        this.mFocusedAspectRatio = this.mUnfocusedAspectRatio;
                    } else if (this.mUnfocusedAspectRatio.doubleValue() <= ratio && ratio <= 4.0d) {
                        this.mFocusedAspectRatio = Double.valueOf(ratio);
                    }
                }
                if (this.mFocusedAspectRatio == null) {
                    this.mFocusedAspectRatio = Double.valueOf(1.7777777777777777d);
                }
            } else {
                this.mThumbnailUri = program.getThumbnailUri();
                if (this.mThumbnailUri != null) {
                    this.mFocusedAspectRatio = Double.valueOf(ProgramUtil.getAspectRatio(program.getThumbnailAspectRatio()));
                    if (this.mFocusedAspectRatio.doubleValue() < this.mUnfocusedAspectRatio.doubleValue()) {
                        this.mFocusedAspectRatio = this.mUnfocusedAspectRatio;
                    }
                }
            }
        }
        if (this.mFocusedAspectRatio == null) {
            this.mFocusedAspectRatio = this.mUnfocusedAspectRatio;
        }
        this.mPreviewImageNeedsTreatment = this.mPreviewVideoUri != null && Math.abs(this.mUnfocusedAspectRatio.doubleValue() - this.mFocusedAspectRatio.doubleValue()) > EPS;
        this.mBlurredPreviewImageDrawable = null;
        if (!Util.areHomeScreenAnimationsEnabled(this.mView.getContext())) {
            this.mFocusHandler.resetFocusedState();
        }
        updateSize();
        updateFocusedState();
        if (dataReadyForBinding) {
            this.mView.getPreviewImage().setContentDescription(program.getTitle());
            loadPreviewImage(program.getPreviewImageUri());
        } else {
            setPreviewImagePlaceholder();
        }
        if (this.mIsSponsored) {
            this.mView.getPreviewImage().setContentDescription(this.mSponsoredProgramContentDescription);
        }
        updateProgramDimmingFactor();
        this.mThumbnail.setVisibility(8);
        if (this.mThumbnailUri != null) {
            loadThumbnailImage();
            if (this.mProgramSelected) {
                this.mThumbnail.setVisibility(0);
                this.mView.getPreviewImageContainer().setVisibility(8);
            }
        } else {
            this.mThumbnail.setImageDrawable(null);
            this.mView.getPreviewImageContainer().setVisibility(0);
        }
        this.mLogoUri = program.getLogoUri();
        this.mLogoContentDescription = program.getLogoContentDescription();
        bindLogoAndBadges();
        updateLogoAndBadgesVisibility(this.mProgramSelected);
        updateProgressState(program);
    }

    private void updateProgramStateAndSelection(int programState) {
        this.mProgramState = programState;
        this.mProgramSelected = this.mProgramState == 3 && this.mView.isFocused();
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        stopUpdateProgress();
        SponsoredProgramControllerHelper sponsoredProgramControllerHelper = this.mSponsoredProgramControllerHelper;
        if (sponsoredProgramControllerHelper != null) {
            sponsoredProgramControllerHelper.onStop();
        }
    }

    /* access modifiers changed from: package-private */
    public void recycle() {
        stopUpdateProgress();
        if (Util.isValidContextForGlide(this.mView.getContext())) {
            Glide.with(this.mView.getContext()).clear(this.mView.getPreviewImage());
        }
        this.mView.getPreviewImage().setImageDrawable(null);
        SponsoredProgramControllerHelper sponsoredProgramControllerHelper = this.mSponsoredProgramControllerHelper;
        if (sponsoredProgramControllerHelper != null) {
            sponsoredProgramControllerHelper.onStop();
        }
    }

    private void stopUpdateProgress() {
        this.mView.removeCallbacks(this.mLiveProgressUpdateRunnable);
    }

    private void bindLogoAndBadges() {
        if (needLogo()) {
            loadLogoImage();
            this.mView.getLogo().setContentDescription(this.mLogoContentDescription);
        } else {
            this.mView.getLogo().setContentDescription(null);
        }
        if (needDurationBadge()) {
            TextView durationBadge = this.mView.getDurationBadge();
            durationBadge.setText(formatDurationInHoursMinutesAndSeconds(this.mProgramDuration));
            durationBadge.setContentDescription(formatDurationForAccessibility(this.mProgramDuration));
        }
    }

    private String formatDurationInHoursMinutesAndSeconds(long milliseconds) {
        return DateUtils.formatElapsedTime(milliseconds / 1000);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public CharSequence formatDurationForAccessibility(long milliseconds) {
        long milliseconds2;
        long hours = 0;
        long minutes = 0;
        if (milliseconds >= 3600000) {
            hours = milliseconds / 3600000;
            milliseconds2 = milliseconds - (3600000 * hours);
        } else {
            milliseconds2 = milliseconds;
        }
        if (milliseconds2 >= 60000) {
            minutes = milliseconds2 / 60000;
            milliseconds2 -= 60000 * minutes;
        }
        long seconds = milliseconds2 / 1000;
        if (hours > 0) {
            return this.mA11yDurationFormat.formatMeasures(new Measure(Long.valueOf(hours), MeasureUnit.HOUR), new Measure(Long.valueOf(minutes), MeasureUnit.MINUTE), new Measure(Long.valueOf(seconds), MeasureUnit.SECOND));
        }
        return this.mA11yDurationFormat.formatMeasures(new Measure(Long.valueOf(minutes), MeasureUnit.MINUTE), new Measure(Long.valueOf(seconds), MeasureUnit.SECOND));
    }

    private boolean needLogo() {
        if (this.mIsLegacy || this.mLogoUri == null) {
            return false;
        }
        int i = this.mProgramType;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 5 || i == 6) {
            return true;
        }
        return false;
    }

    private boolean needLiveBadge() {
        return !this.mIsLegacy && this.mProgramIsLive;
    }

    private boolean needDurationBadge() {
        if (this.mIsLegacy) {
            return false;
        }
        long j = this.mProgramDuration;
        long hours = j / 3600000;
        if (j < 1000 || hours > 99 || this.mProgramType != 4) {
            return false;
        }
        return true;
    }

    private void updateLogoAndBadgesVisibility(boolean programSelected) {
        boolean isSelectedRow = this.mProgramState == 3;
        if (!isSelectedRow || !needLogo()) {
            this.mView.setLogoVisibility(4);
            this.mView.setLogoDimmerVisibility(4);
        } else {
            this.mView.setLogoVisibility(0);
            this.mView.setLogoDimmerVisibility(0);
        }
        boolean hasLiveIcon = false;
        if (needLiveBadge()) {
            double aspectRatio = (programSelected ? this.mFocusedAspectRatio : this.mUnfocusedAspectRatio).doubleValue();
            if (!isSelectedRow) {
                this.mView.setLiveBadgeVisibility(4);
                this.mView.setLiveIconVisibility(4);
            } else if (Double.compare(aspectRatio, 0.6666666666666666d) > 0) {
                this.mView.setLiveBadgeVisibility(0);
                this.mView.setLiveIconVisibility(4);
            } else {
                hasLiveIcon = true;
                this.mView.setLiveBadgeVisibility(4);
                this.mView.setLiveIconVisibility(0);
            }
        } else {
            this.mView.setLiveBadgeVisibility(8);
            this.mView.setLiveIconVisibility(8);
        }
        if (!needDurationBadge()) {
            this.mView.setDurationBadgeVisibility(8);
        } else if (!isSelectedRow || hasLiveIcon) {
            this.mView.setDurationBadgeVisibility(4);
        } else {
            this.mView.setDurationBadgeVisibility(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void bindState(int programState) {
        updateProgramStateAndSelection(programState);
        updateProgramDimmingFactor();
        updateLogoAndBadgesVisibility(this.mProgramSelected);
        updateSize();
    }

    private void updateProgramDimmingFactor() {
        ProgramView programView = this.mView;
        int i = this.mProgramState;
        boolean z = true;
        if (!(i == 0 || i == 12 || i == 1 || i == 5 || i == 7 || i == 8 || i == 10)) {
            z = false;
        }
        programView.setPreviewImageDimmed(z);
    }

    /* access modifiers changed from: package-private */
    public void updateProgressState(Program program) {
        boolean enableProgressBar = updateLiveProgressIfNeeded();
        if (!enableProgressBar) {
            enableProgressBar = updateWatchNextProgressIfNeeded(program);
        }
        int i = 0;
        this.mView.getPlaybackProgressDimmer().setVisibility((!enableProgressBar || needLogo()) ? 8 : 0);
        ProgressBar playbackProgress = this.mView.getPlaybackProgress();
        if (!enableProgressBar) {
            i = 8;
        }
        playbackProgress.setVisibility(i);
    }

    private boolean updateLiveProgressIfNeeded() {
        long currentTime = System.currentTimeMillis();
        if (this.mProgramIsLive) {
            long j = this.mProgramLiveStartTime;
            if (j > 0) {
                long j2 = this.mProgramLiveEndTime;
                if (j2 > 0 && j < currentTime && currentTime < j2) {
                    ProgressBar playbackProgress = this.mView.getPlaybackProgress();
                    playbackProgress.setMin(0);
                    playbackProgress.setMax((int) (this.mProgramLiveEndTime - this.mProgramLiveStartTime));
                    playbackProgress.setProgress((int) (currentTime - this.mProgramLiveStartTime));
                    playbackProgress.setProgressTintList(this.mLiveProgressBarForegroundColor);
                    this.mView.removeCallbacks(this.mLiveProgressUpdateRunnable);
                    this.mView.postDelayed(this.mLiveProgressUpdateRunnable, 60000);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean updateWatchNextProgressIfNeeded(Program program) {
        if (!this.mIsWatchNextProgram || program.getDuration() <= 0 || program.getPlaybackPosition() <= 0 || program.getWatchNextType() != 0) {
            return false;
        }
        ProgressBar playbackProgress = this.mView.getPlaybackProgress();
        playbackProgress.setMin(0);
        playbackProgress.setMax((int) program.getDuration());
        playbackProgress.setProgress((int) program.getPlaybackPosition());
        playbackProgress.setProgressTintList(this.mWatchNextProgressBarForegroundColor);
        return true;
    }

    /* access modifiers changed from: private */
    public void setPreviewImageSpecialBackground() {
        this.mView.getPreviewImageBackground().setVisibility(0);
        if (this.mBlurredPreviewImageDrawable == null || this.mView.getPreviewImage().getDrawable() == null) {
            this.mView.getPreviewImageBackground().setImageDrawable(this.mProgramDefaultBackgroundDrawable);
        } else {
            this.mView.getPreviewImageBackground().setImageDrawable(this.mBlurredPreviewImageDrawable);
        }
    }

    public void clearPreviewImageBackgroundIfPossible() {
        if (this.mView.getPreviewImage().getDrawable() != null) {
            this.mView.getPreviewImageBackground().setVisibility(4);
            this.mView.getPreviewImageBackground().setImageDrawable(null);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void loadPreviewImage(String previewImageUri) {
        setPreviewImagePlaceholder();
        this.mPreviewImagePalette = null;
        this.mBlurredPreviewImageDrawable = null;
        if (this.mPreviewImageNeedsTreatment) {
            loadPreviewImageWithBlur(previewImageUri);
        } else {
            loadPreviewImageWithoutBlur(previewImageUri);
        }
    }

    private void setPreviewImagePlaceholder() {
        this.mView.getPreviewImageBackground().setVisibility(0);
        this.mView.getPreviewImageBackground().setImageDrawable(this.mProgramDefaultBackgroundDrawable);
    }

    private void loadPreviewImageWithBlur(String previewImageUri) {
        RequestBuilder<ProgramPreviewImageData> builder = Glide.with(this.mView.getContext()).mo11716as(ProgramPreviewImageData.class).load(previewImageUri).apply((BaseRequestOptions<?>) this.mImageRequestOptions);
        if (this.mPreviewImageBlurGlideTarget == null) {
            this.mPreviewImageBlurGlideTarget = new ImageViewTargetWithTrace<ProgramPreviewImageData>(this.mView.getPreviewImage(), "LoadProgramImageWithBlur") {
                /* access modifiers changed from: protected */
                public void setResource(ProgramPreviewImageData resource) {
                    if (resource != null) {
                        ((ImageView) this.view).setImageBitmap(resource.getBitmap());
                        Palette unused = ProgramController.this.mPreviewImagePalette = resource.getPalette();
                        ProgramController programController = ProgramController.this;
                        BitmapDrawable unused2 = programController.mBlurredPreviewImageDrawable = new BitmapDrawable(programController.mView.getResources(), resource.getBlurredBitmap());
                    } else {
                        ((ImageView) this.view).setImageDrawable(null);
                    }
                    if (ProgramController.this.mProgramSelected) {
                        ProgramController.this.setPreviewImageSpecialBackground();
                    } else {
                        ProgramController.this.clearPreviewImageBackgroundIfPossible();
                    }
                }
            };
        }
        builder.into(this.mPreviewImageBlurGlideTarget);
    }

    private void loadPreviewImageWithoutBlur(String previewImageUri) {
        RequestBuilder<PaletteBitmapContainer> builder = Glide.with(this.mView.getContext()).mo11716as(PaletteBitmapContainer.class).load(previewImageUri).apply((BaseRequestOptions<?>) this.mImageRequestOptions);
        if (this.mPreviewImagePaletteGlideTarget == null) {
            this.mPreviewImagePaletteGlideTarget = new ImageViewTargetWithTrace<PaletteBitmapContainer>(this.mView.getPreviewImage(), "LoadProgramImage") {
                /* access modifiers changed from: protected */
                public void setResource(PaletteBitmapContainer resource) {
                    if (resource != null) {
                        ((ImageView) this.view).setImageBitmap(resource.getBitmap());
                        Palette unused = ProgramController.this.mPreviewImagePalette = resource.getPalette();
                    } else {
                        ((ImageView) this.view).setImageDrawable(null);
                    }
                    ProgramController.this.clearPreviewImageBackgroundIfPossible();
                }
            };
        }
        builder.into(this.mPreviewImagePaletteGlideTarget);
    }

    private void loadThumbnailImage() {
        this.mThumbnail.setImageDrawable(null);
        this.mThumbnail.setBackground(this.mProgramDefaultBackgroundDrawable);
        RequestBuilder<Drawable> builder = Glide.with(this.mThumbnail.getContext()).load(this.mThumbnailUri).apply((BaseRequestOptions<?>) this.mImageRequestOptions);
        if (this.mThumbnailImageGlideTarget == null) {
            this.mThumbnailImageGlideTarget = new ImageViewTargetWithTrace<Drawable>(this, this.mThumbnail, "LoadFocusedProgramImage") {
                /* access modifiers changed from: protected */
                public void setResource(Drawable resource) {
                    ((ImageView) this.view).setImageDrawable(resource);
                    if (resource != null) {
                        ((ImageView) this.view).setBackground(null);
                    }
                }
            };
        }
        builder.into(this.mThumbnailImageGlideTarget);
    }

    private void loadLogoImage() {
        this.mView.getLogo().setImageDrawable(null);
        Glide.with(this.mView.getContext()).load(this.mLogoUri).into(this.mView.getLogo());
    }

    private void updateSize() {
        ProgramUtil.updateSize(this.mView, this.mProgramState, this.mUnfocusedAspectRatio.doubleValue(), this.mProgramSettings);
    }

    /* access modifiers changed from: package-private */
    public void updateFocusedState() {
        updateProgramStateAndSelection(this.mProgramState);
        updateAspectRatio(this.mProgramSelected);
        updatePreviewImageSize(this.mProgramSelected);
        updatePreviewImageBackground(this.mProgramSelected);
        updateZoom(this.mProgramSelected);
        updateLogoAndBadgesVisibility(this.mProgramSelected);
    }

    private void updatePreviewImageBackground(boolean programSelected) {
        if (programSelected && this.mPreviewImageNeedsTreatment) {
            setPreviewImageSpecialBackground();
        } else if (!Util.areHomeScreenAnimationsEnabled(this.mView.getContext())) {
            clearPreviewImageBackgroundIfPossible();
        }
    }

    private void updateZoom(boolean programSelected) {
        int pivotX;
        if (Util.areHomeScreenAnimationsEnabled(this.mView.getContext())) {
            int width = this.mView.getLayoutParams().width;
            int height = this.mView.getLayoutParams().height;
            if (width <= 0 || height <= 0) {
                width = this.mView.getWidth();
                height = this.mView.getHeight();
            }
            if (width > 0 && height > 0) {
                if (this.mView.getLayoutDirection() == 1) {
                    pivotX = width;
                } else {
                    pivotX = 0;
                }
                this.mView.setPivotX((float) pivotX);
                this.mView.setPivotY((float) (height / 2));
            }
            float scale = programSelected ? this.mProgramSettings.focusedScale : 1.0f;
            float elevation = programSelected ? this.mProgramSettings.focusedElevation : 0.0f;
            this.mView.setScaleX(scale);
            this.mView.setScaleY(scale);
            this.mView.setElevation(elevation);
        }
    }

    private void updateAspectRatio(boolean programSelected) {
        int newHeight;
        ViewGroup.MarginLayoutParams containerLayoutParams = (ViewGroup.MarginLayoutParams) this.mView.getLayoutParams();
        double targetAspectRatio = (programSelected ? this.mFocusedAspectRatio : this.mUnfocusedAspectRatio).doubleValue();
        double currentAspectRatio = 0.0d;
        if (containerLayoutParams.height > 0) {
            double d = (double) containerLayoutParams.width;
            double d2 = (double) containerLayoutParams.height;
            Double.isNaN(d);
            Double.isNaN(d2);
            currentAspectRatio = d / d2;
        }
        if (Math.abs(targetAspectRatio - currentAspectRatio) > EPS) {
            double d3 = (double) containerLayoutParams.height;
            Double.isNaN(d3);
            containerLayoutParams.width = (int) Math.round(d3 * targetAspectRatio);
            this.mView.setLayoutParams(containerLayoutParams);
        }
        if (containerLayoutParams.width > 0 && this.mUnfocusedAspectRatio.doubleValue() != 0.0d) {
            if (programSelected) {
                double d4 = (double) containerLayoutParams.width;
                double doubleValue = this.mUnfocusedAspectRatio.doubleValue();
                Double.isNaN(d4);
                newHeight = (int) Math.round(d4 / doubleValue);
            } else {
                newHeight = -1;
            }
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mView.getPreviewImageBackground().getLayoutParams();
            if (newHeight != lp.height) {
                lp.height = newHeight;
                this.mView.getPreviewImageBackground().setLayoutParams(lp);
            }
        }
    }

    private void updatePreviewImageSize(boolean programSelected) {
        ViewGroup.MarginLayoutParams containerLayoutParams = (ViewGroup.MarginLayoutParams) this.mView.getLayoutParams();
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mView.getPreviewImage().getLayoutParams();
        int newHeight = lp.height;
        int newWidth = lp.width;
        if (!programSelected || !this.mPreviewImageNeedsTreatment) {
            newHeight = -1;
            newWidth = -1;
        } else if (containerLayoutParams.height > 0) {
            newHeight = containerLayoutParams.height - (this.mPreviewImageExpandedVerticalMargin * 2);
            double d = (double) newHeight;
            double doubleValue = this.mUnfocusedAspectRatio.doubleValue();
            Double.isNaN(d);
            newWidth = (int) Math.round(d * doubleValue);
        }
        if (lp.height != newHeight || lp.width != newWidth) {
            lp.height = newHeight;
            lp.width = newWidth;
            this.mView.getPreviewImage().setLayoutParams(lp);
        }
    }

    /* access modifiers changed from: private */
    public void fadePreviewImageOut() {
        ValueAnimator valueAnimator = this.mPreviewImageFadeOutAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            ValueAnimator valueAnimator2 = this.mPreviewImageFadeInAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.mPreviewImageFadeInAnimator.cancel();
                this.mPreviewImageFadeInAnimator = null;
            }
            if (this.mPreviewImageFadeOutAnimatorListener == null) {
                this.mPreviewImageFadeOutAnimatorListener = new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
                        ProgramController.this.mPreviewVideo.setVisibility(0);
                    }

                    public void onAnimationEnd(Animator animation) {
                        ProgramController.this.mView.getPreviewImageContainer().setVisibility(8);
                    }
                };
            }
            this.mPreviewImageFadeOutAnimator = ValueAnimator.ofFloat(this.mPreviewImageVisibilityValue, 0.0f);
            this.mPreviewImageFadeOutAnimator.addUpdateListener(getPreviewImageFadeUpdateListener());
            this.mPreviewImageFadeOutAnimator.addListener(this.mPreviewImageFadeOutAnimatorListener);
            this.mPreviewImageFadeOutAnimator.setDuration(300L);
            this.mPreviewImageFadeOutAnimator.start();
        }
    }

    private void fadePreviewImageIn(final int videoPlaybackStoppedState) {
        ValueAnimator valueAnimator = this.mPreviewImageFadeInAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            ValueAnimator valueAnimator2 = this.mPreviewImageFadeOutAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.mPreviewImageFadeOutAnimator.cancel();
                this.mPreviewImageFadeOutAnimator = null;
            }
            if (this.mPreviewImageFadeInAnimatorListener == null) {
                this.mPreviewImageFadeInAnimatorListener = new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
                        ProgramController.this.mView.getPreviewImageContainer().setVisibility(0);
                    }

                    public void onAnimationEnd(Animator animation) {
                        ProgramController.this.finishStoppingPreviewVideo(videoPlaybackStoppedState);
                    }
                };
            }
            this.mPreviewImageFadeInAnimator = ValueAnimator.ofFloat(this.mPreviewImageVisibilityValue, 1.0f);
            this.mPreviewImageFadeInAnimator.addUpdateListener(getPreviewImageFadeUpdateListener());
            this.mPreviewImageFadeInAnimator.addListener(this.mPreviewImageFadeInAnimatorListener);
            this.mPreviewImageFadeInAnimator.setDuration(300L);
            this.mPreviewImageFadeInAnimator.start();
        }
    }

    private ValueAnimator.AnimatorUpdateListener getPreviewImageFadeUpdateListener() {
        if (this.mPreviewImageFadeUpdateListener == null) {
            this.mPreviewImageFadeUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    ProgramController.this.setPreviewImageVisibilityValue(((Float) animation.getAnimatedValue()).floatValue());
                }
            };
        }
        return this.mPreviewImageFadeUpdateListener;
    }

    /* access modifiers changed from: private */
    public void setPreviewImageVisibilityValue(float visibilityValue) {
        this.mPreviewImageVisibilityValue = visibilityValue;
        this.mView.getPreviewImageContainer().setAlpha(this.mPreviewImageVisibilityValue);
        this.mView.getLogoAndBadgesContainer().setAlpha(this.mPreviewImageVisibilityValue);
        this.mView.getPlaybackProgressDimmer().setAlpha(this.mPreviewImageVisibilityValue);
        this.mView.getPlaybackProgress().setAlpha(this.mPreviewImageVisibilityValue);
        if (allowPreviewAudioPlaying()) {
            this.mPreviewVideo.setVolume(1.0f - this.mPreviewImageVisibilityValue);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isViewFocused() {
        return this.mView.isFocused();
    }

    /* access modifiers changed from: package-private */
    public boolean isProgramSelected() {
        return this.mProgramSelected;
    }

    /* access modifiers changed from: package-private */
    public Palette getPreviewImagePalette() {
        return this.mPreviewImagePalette;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public LauncherAudioPlayer getLauncherAudioPlayer() {
        return this.mLauncherAudioPlayer;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setLauncherAudioPlayer(LauncherAudioPlayer launcherAudioPlayer) {
        this.mLauncherAudioPlayer = launcherAudioPlayer;
    }

    public void onClick(View v) {
        if (Util.isAccessibilityEnabled(v.getContext())) {
            onLongClick(v);
        } else {
            onPrimaryAction(v);
        }
    }

    private void onPrimaryAction(View view) {
        int i;
        if (this.mActionUri != null || (i = this.mProgramType) == 1002 || i == 1001) {
            long timestamp = System.currentTimeMillis();
            boolean hasPreviewVideo = this.mPreviewVideoUri != null && !this.mIsWatchNextProgram;
            boolean isPlayingVideo = this.mStartedPreviewVideoMillis != 0;
            LogEvent event = new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.START_PROGRAM).setVisualElementTag(TvLauncherConstants.PROGRAM_ITEM);
            event.getProgram().setPackageName(this.mProgramPackageName);
            TvlauncherClientLog.Program.Type type = LogEvent.programType(this.mProgramType);
            if (type != null) {
                event.getProgram().setType(type);
            }
            if (hasPreviewVideo) {
                stopPreviewVideo(true, 0);
                TvlauncherClientLog.Program.Preview.Builder preview = TvlauncherClientLog.Program.Preview.newBuilder();
                int i2 = this.mWatchedPreviewVideoSeconds;
                if (i2 != 0) {
                    preview.setPlayedDurationSeconds(i2);
                }
                event.getProgram().setPreview(preview);
            }
            if (!this.mIsWatchNextProgram && this.mPreviewAudioUri != null) {
                stopPreviewAudio();
            }
            this.mEventLogger.log(event);
            String launchedMediaActionUri = null;
            int i3 = this.mProgramType;
            if (i3 == 1002 || i3 == 1001) {
                launchedMediaActionUri = this.mSponsoredProgramControllerHelper.launchMediaIntent(this.mActionUri);
                if (this.mProgramType == 1002) {
                    this.mSponsoredProgramControllerHelper.recordClickIfDoubleClickAd();
                }
            } else if (this.mIntentLauncher.launchChannelIntentFromUriWithAnimation(this.mProgramPackageName, this.mActionUri, true, view)) {
                launchedMediaActionUri = this.mActionUri;
            }
            if (!TextUtils.isEmpty(launchedMediaActionUri)) {
                Intent intent = new Intent(Constants.ACTION_PROGRAM_LAUNCH_LOG_EVENT).putExtra("timestamp", timestamp).putExtra(Constants.EXTRA_URI, launchedMediaActionUri).putExtra(Constants.EXTRA_HAS_PREVIEW, hasPreviewVideo).putExtra(Constants.EXTRA_IS_PLAYING_PREVIEW, isPlayingVideo);
                intent.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
                this.mView.getContext().sendBroadcast(intent);
            }
        }
    }

    public boolean onLongClick(View v) {
        RecyclerViewStateProvider recyclerViewStateProvider;
        RecyclerViewStateProvider recyclerViewStateProvider2;
        String str;
        if (!v.hasFocus() || (((recyclerViewStateProvider = this.mListStateProvider) != null && recyclerViewStateProvider.isAnimating()) || ((recyclerViewStateProvider2 = this.mHomeListStateProvider) != null && recyclerViewStateProvider2.isAnimating()))) {
            return true;
        }
        boolean shouldShowAddToWatchNext = shouldShowAddToWatchNextInProgramMenu();
        boolean shouldShowRemoveProgram = shouldShowRemoveProgramInProgramMenu();
        this.mProgramMenu = new ContextMenu((Activity) v.getContext(), v, v.getResources().getDimensionPixelSize(C1188R.dimen.card_rounded_corner_radius));
        boolean z = false;
        if (Util.isAccessibilityEnabled(v.getContext())) {
            ContextMenuItem openItem = new ContextMenuItem(1, v.getContext().getString(C1188R.string.context_menu_primary_action_text), v.getContext().getDrawable(C1188R.C1189drawable.ic_context_menu_open_black));
            openItem.setAutoDismiss(false);
            this.mProgramMenu.addItem(openItem);
        } else if (!this.mIsWatchNextProgram && !shouldShowAddToWatchNext && !shouldShowRemoveProgram) {
            onPrimaryAction(v);
            return true;
        }
        if (!this.mIsWatchNextProgram) {
            if (shouldShowAddToWatchNext) {
                boolean isInWatchNext = TvDataManager.getInstance(v.getContext()).isInWatchNext(this.mContentId, this.mProgramPackageName);
                ContextMenuItem addToWatchNextItem = new ContextMenuItem(2, null, v.getContext().getDrawable(C1188R.C1189drawable.ic_context_menu_add_to_watch_next_black));
                this.mProgramMenu.addItem(addToWatchNextItem);
                if (this.mCanAddToWatchNext && !isInWatchNext) {
                    z = true;
                }
                addToWatchNextItem.setEnabled(z);
                if (isInWatchNext) {
                    addToWatchNextItem.setTitle(this.mProgramMenuAlreadyInWatchNextText);
                } else {
                    if (this.mCanAddToWatchNext) {
                        str = this.mProgramMenuAddToWatchNextText;
                    } else {
                        str = this.mProgramMenuAddToWatchNextNotAvailableText;
                    }
                    addToWatchNextItem.setTitle(str);
                }
            }
            if (shouldShowRemoveProgram) {
                ContextMenuItem removeProgramItem = new ContextMenuItem(3, null, v.getContext().getDrawable(C1188R.C1189drawable.ic_context_menu_uninstall_black));
                this.mProgramMenu.addItem(removeProgramItem);
                removeProgramItem.setTitle(this.mProgramMenuRemoveText);
            }
        } else {
            this.mProgramMenu.addItem(new ContextMenuItem(4, v.getContext().getString(C1188R.string.program_menu_remove_for_play_next_text), v.getContext().getDrawable(C1188R.C1189drawable.ic_context_menu_uninstall_black)));
        }
        this.mProgramMenu.setOnMenuItemClickListener(this);
        this.mProgramMenu.show();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldShowAddToWatchNextInProgramMenu() {
        return OemConfiguration.get(this.mView.getContext()).shouldShowAddToWatchNextFromProgramMenu();
    }

    /* access modifiers changed from: package-private */
    public boolean shouldShowRemoveProgramInProgramMenu() {
        return this.mCanRemoveProgram && OemConfiguration.get(this.mView.getContext()).shouldShowRemoveProgramFromProgramMenu();
    }

    public void onItemClick(ContextMenuItem item) {
        TvDataManager tvDataManager = TvDataManager.getInstance(this.mView.getContext());
        int id = item.getId();
        if (id == 1) {
            onPrimaryAction(this.mView);
        } else if (id == 2) {
            tvDataManager.addProgramToWatchlist(this.mProgramId, this.mProgramPackageName);
            tvDataManager.addProgramToWatchNextCache(this.mContentId, this.mProgramPackageName);
            LogEvent userEvent = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.ADD_PROGRAM_TO_WATCH_NEXT);
            if (this.mProgramPackageName != null) {
                userEvent.getProgram().setPackageName(this.mProgramPackageName);
            }
            this.mEventLogger.log(userEvent);
        } else if (id == 3) {
            tvDataManager.removePreviewProgram(this.mProgramId, this.mChannelId, this.mProgramPackageName);
            LogEvent userEvent2 = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.REMOVE_PROGRAM_FROM_CHANNEL);
            TvlauncherClientLog.Program.Type type = LogEvent.programType(this.mProgramType);
            if (type != null) {
                userEvent2.getProgram().setType(type);
            }
            if (this.mProgramPackageName != null) {
                userEvent2.getProgram().setPackageName(this.mProgramPackageName);
            }
            this.mEventLogger.log(userEvent2);
        } else if (id == 4) {
            tvDataManager.removeProgramFromWatchlist(this.mProgramId, this.mProgramPackageName);
            tvDataManager.removeProgramFromWatchNextCache(this.mContentId, this.mProgramPackageName);
            LogEvent userEvent3 = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.REMOVE_PROGRAM_FROM_WATCH_NEXT);
            if (!TextUtils.isEmpty(this.mProgramPackageName)) {
                userEvent3.getProgram().setPackageName(this.mProgramPackageName);
            }
            this.mEventLogger.log(userEvent3);
        }
    }

    public void onWindowVisibilityChanged(int visibility) {
        ContextMenu contextMenu;
        if ((visibility == 4 || visibility == 8) && (contextMenu = this.mProgramMenu) != null && contextMenu.isShowing()) {
            this.mProgramMenu.forceDismiss();
        }
    }

    public void onHomePressed(Context c) {
        ContextMenu contextMenu = this.mProgramMenu;
        if (contextMenu == null || !contextMenu.isShowing()) {
            BackHomeControllerListeners.OnHomeNotHandledListener onHomeNotHandledListener = this.mOnHomeNotHandledListener;
            if (onHomeNotHandledListener != null) {
                onHomeNotHandledListener.onHomeNotHandled(c);
                return;
            }
            return;
        }
        this.mProgramMenu.forceDismiss();
    }

    public String toString() {
        String programView = this.mView.toString();
        String str = this.mDebugTitle;
        StringBuilder sb = new StringBuilder(String.valueOf(programView).length() + 12 + String.valueOf(str).length());
        sb.append('{');
        sb.append(programView);
        sb.append(", title='");
        sb.append(str);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface PlaybackStoppedState {
    }
}
