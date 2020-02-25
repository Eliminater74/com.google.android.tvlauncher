package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.util.ArrayList;

public class Action {
    public static final long NO_ID = -1;
    private Drawable mIcon;
    private long mId;
    private ArrayList<Integer> mKeyCodes;
    private CharSequence mLabel1;
    private CharSequence mLabel2;

    public Action(long id) {
        this(id, "");
    }

    public Action(long id, CharSequence label) {
        this(id, label, null);
    }

    public Action(long id, CharSequence label1, CharSequence label2) {
        this(id, label1, label2, null);
    }

    public Action(long id, CharSequence label1, CharSequence label2, Drawable icon) {
        this.mId = -1;
        this.mKeyCodes = new ArrayList<>();
        setId(id);
        setLabel1(label1);
        setLabel2(label2);
        setIcon(icon);
    }

    public final long getId() {
        return this.mId;
    }

    public final void setId(long id) {
        this.mId = id;
    }

    public final CharSequence getLabel1() {
        return this.mLabel1;
    }

    public final void setLabel1(CharSequence label) {
        this.mLabel1 = label;
    }

    public final CharSequence getLabel2() {
        return this.mLabel2;
    }

    public final void setLabel2(CharSequence label) {
        this.mLabel2 = label;
    }

    public final Drawable getIcon() {
        return this.mIcon;
    }

    public final void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public final void addKeyCode(int keyCode) {
        this.mKeyCodes.add(Integer.valueOf(keyCode));
    }

    public final void removeKeyCode(int keyCode) {
        this.mKeyCodes.remove(keyCode);
    }

    public final boolean respondsToKeyCode(int keyCode) {
        return this.mKeyCodes.contains(Integer.valueOf(keyCode));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.mLabel1)) {
            sb.append(this.mLabel1);
        }
        if (!TextUtils.isEmpty(this.mLabel2)) {
            if (!TextUtils.isEmpty(this.mLabel1)) {
                sb.append(" ");
            }
            sb.append(this.mLabel2);
        }
        if (this.mIcon != null && sb.length() == 0) {
            sb.append("(action icon)");
        }
        return sb.toString();
    }
}
