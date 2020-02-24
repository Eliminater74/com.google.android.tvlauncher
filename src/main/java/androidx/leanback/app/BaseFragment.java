package androidx.leanback.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;
import androidx.leanback.util.StateMachine;

@Deprecated
public class BaseFragment extends BrandedFragment {
    final StateMachine.Condition COND_TRANSITION_NOT_SUPPORTED = new StateMachine.Condition("EntranceTransitionNotSupport") {
        public boolean canProceed() {
            return !TransitionHelper.systemSupportsEntranceTransitions();
        }
    };
    final StateMachine.Event EVT_ENTRANCE_END = new StateMachine.Event("onEntranceTransitionEnd");
    final StateMachine.Event EVT_ON_CREATE = new StateMachine.Event("onCreate");
    final StateMachine.Event EVT_ON_CREATEVIEW = new StateMachine.Event("onCreateView");
    final StateMachine.Event EVT_PREPARE_ENTRANCE = new StateMachine.Event("prepareEntranceTransition");
    final StateMachine.Event EVT_START_ENTRANCE = new StateMachine.Event("startEntranceTransition");
    final StateMachine.State STATE_ENTRANCE_COMPLETE = new StateMachine.State("ENTRANCE_COMPLETE", true, false);
    final StateMachine.State STATE_ENTRANCE_INIT = new StateMachine.State("ENTRANCE_INIT");
    final StateMachine.State STATE_ENTRANCE_ON_ENDED = new StateMachine.State("ENTRANCE_ON_ENDED") {
        public void run() {
            BaseFragment.this.onEntranceTransitionEnd();
        }
    };
    final StateMachine.State STATE_ENTRANCE_ON_PREPARED = new StateMachine.State("ENTRANCE_ON_PREPARED", true, false) {
        public void run() {
            BaseFragment.this.mProgressBarManager.show();
        }
    };
    final StateMachine.State STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW = new StateMachine.State("ENTRANCE_ON_PREPARED_ON_CREATEVIEW") {
        public void run() {
            BaseFragment.this.onEntranceTransitionPrepare();
        }
    };
    final StateMachine.State STATE_ENTRANCE_PERFORM = new StateMachine.State("STATE_ENTRANCE_PERFORM") {
        public void run() {
            BaseFragment.this.mProgressBarManager.hide();
            BaseFragment.this.onExecuteEntranceTransition();
        }
    };
    final StateMachine.State STATE_START = new StateMachine.State("START", true, false);
    Object mEntranceTransition;
    final ProgressBarManager mProgressBarManager = new ProgressBarManager();
    final StateMachine mStateMachine = new StateMachine();

    @SuppressLint({"ValidFragment"})
    BaseFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        createStateMachineStates();
        createStateMachineTransitions();
        this.mStateMachine.start();
        super.onCreate(savedInstanceState);
        this.mStateMachine.fireEvent(this.EVT_ON_CREATE);
    }

    /* access modifiers changed from: package-private */
    public void createStateMachineStates() {
        this.mStateMachine.addState(this.STATE_START);
        this.mStateMachine.addState(this.STATE_ENTRANCE_INIT);
        this.mStateMachine.addState(this.STATE_ENTRANCE_ON_PREPARED);
        this.mStateMachine.addState(this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW);
        this.mStateMachine.addState(this.STATE_ENTRANCE_PERFORM);
        this.mStateMachine.addState(this.STATE_ENTRANCE_ON_ENDED);
        this.mStateMachine.addState(this.STATE_ENTRANCE_COMPLETE);
    }

    /* access modifiers changed from: package-private */
    public void createStateMachineTransitions() {
        this.mStateMachine.addTransition(this.STATE_START, this.STATE_ENTRANCE_INIT, this.EVT_ON_CREATE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_ENTRANCE_COMPLETE, this.COND_TRANSITION_NOT_SUPPORTED);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_ENTRANCE_COMPLETE, this.EVT_ON_CREATEVIEW);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_INIT, this.STATE_ENTRANCE_ON_PREPARED, this.EVT_PREPARE_ENTRANCE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW, this.EVT_ON_CREATEVIEW);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED, this.STATE_ENTRANCE_PERFORM, this.EVT_START_ENTRANCE);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_PREPARED_ON_CREATEVIEW, this.STATE_ENTRANCE_PERFORM);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_PERFORM, this.STATE_ENTRANCE_ON_ENDED, this.EVT_ENTRANCE_END);
        this.mStateMachine.addTransition(this.STATE_ENTRANCE_ON_ENDED, this.STATE_ENTRANCE_COMPLETE);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mStateMachine.fireEvent(this.EVT_ON_CREATEVIEW);
    }

    public void prepareEntranceTransition() {
        this.mStateMachine.fireEvent(this.EVT_PREPARE_ENTRANCE);
    }

    /* access modifiers changed from: protected */
    public Object createEntranceTransition() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void runEntranceTransition(Object entranceTransition) {
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionPrepare() {
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionStart() {
    }

    /* access modifiers changed from: protected */
    public void onEntranceTransitionEnd() {
    }

    public void startEntranceTransition() {
        this.mStateMachine.fireEvent(this.EVT_START_ENTRANCE);
    }

    /* access modifiers changed from: package-private */
    public void onExecuteEntranceTransition() {
        final View view = getView();
        if (view != null) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (FragmentUtil.getContext(BaseFragment.this) == null || BaseFragment.this.getView() == null) {
                        return true;
                    }
                    BaseFragment.this.internalCreateEntranceTransition();
                    BaseFragment.this.onEntranceTransitionStart();
                    if (BaseFragment.this.mEntranceTransition != null) {
                        BaseFragment baseFragment = BaseFragment.this;
                        baseFragment.runEntranceTransition(baseFragment.mEntranceTransition);
                        return false;
                    }
                    BaseFragment.this.mStateMachine.fireEvent(BaseFragment.this.EVT_ENTRANCE_END);
                    return false;
                }
            });
            view.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void internalCreateEntranceTransition() {
        this.mEntranceTransition = createEntranceTransition();
        Object obj = this.mEntranceTransition;
        if (obj != null) {
            TransitionHelper.addTransitionListener(obj, new TransitionListener() {
                public void onTransitionEnd(Object transition) {
                    BaseFragment baseFragment = BaseFragment.this;
                    baseFragment.mEntranceTransition = null;
                    baseFragment.mStateMachine.fireEvent(BaseFragment.this.EVT_ENTRANCE_END);
                }
            });
        }
    }

    public final ProgressBarManager getProgressBarManager() {
        return this.mProgressBarManager;
    }
}
