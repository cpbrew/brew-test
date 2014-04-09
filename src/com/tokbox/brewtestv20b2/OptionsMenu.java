package com.tokbox.brewtestv20b2;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Presents a menu of options that pertain to a publisher or subscriber.
 *
 * @author cbrew
 */
public class OptionsMenu extends LinearLayout {
    
    /**
     * Represents a single item within an options menu.
     */
    public abstract static class MenuItem {

        /**
         * The name of the option controlled by this menu item.
         */
        public abstract String getKey();
        
        /**
         * The the view that controls operation of a given option.
         */
        public abstract View getControl();
        
        /**
         * Represents an option that can only be either true or false.
         *
         * @author cbrew
         */
        public static class BoolItem extends MenuItem {

            private String mKey;
            private Switch mSwitch;
            private OptionsMenu.Listener mListener;
            
            /**
             * Create a new BoolItem with the specified key, initial value, and
             * listener.
             * @param context
             * @param key
             * @param value
             * @param listener
             */
            public BoolItem(Context context, String key, boolean value,
                    OptionsMenu.Listener listener) {
                mKey = key;
                mListener = listener;
                
                mSwitch = new Switch(context);
                mSwitch.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                mSwitch.setTextOn("On");
                mSwitch.setTextOff("Off");
                mSwitch.setChecked(value);
                mSwitch.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            mListener.onOptionSwitched(mKey,
                                    mSwitch.isChecked());
                        }
                    }
                });
            }
            
            public String getKey() {
                return mKey;
            }
            
            public View getControl() {
                return mSwitch;
            }
        }
        
        /**
         * Represents an option that has only one input method.
         *
         * @author cbrew
         */
        public static class ActiveItem extends MenuItem {
            
            private String mKey;
            private Button mButton;
            private OptionsMenu.Listener mListener;
            
            /**
             * Create a new ActiveItem with the specified key and listener.
             * @param context
             * @param key
             * @param listener
             */
            public ActiveItem(Context context, String key,
                    OptionsMenu.Listener listener) {
                mKey = key;
                mListener = listener;
                
                mButton = new Button(context);
                mButton.setText(R.string.toggle_button_text);
                mButton.setOnClickListener(new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            mListener.onOptionSwitched(mKey, null);
                        }
                    }
                });
            }
            
            public String getKey() {
                return mKey;
            }
            
            public View getControl() {
                return mButton;
            }
        }
        
    }
    
    /**
     * Listener interface for an object to receive notifications that something
     * changed on the options menu.
     *
     * @author cbrew
     */
    public interface Listener {
        
        /**
         * Called when the user changes one of the menu's available options.
         * @param option
         * @param value
         */
        void onOptionSwitched(String option, Object value);
        
    }
    
    private static final int DEFAULT_CELL_HEIGHT = 90;
    
    /**
     * Don't use this constructor.
     * @param context
     * @throws Exception
     */
    public OptionsMenu(Context context) throws Exception {
        super(context);
        throw new Exception("don't use this constructor");
    }
    
    /**
     * Creates a new OptionsMenu with the specified list of options.
     * @param context
     * @param options
     */
    public OptionsMenu(Context context, List<MenuItem> options) {
        super(context);
        
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(Color.WHITE);
        
        for (MenuItem menuItem : options) {
            addView(createView(context, menuItem),
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            DEFAULT_CELL_HEIGHT));
        }
    }

    private View createView(Context context, MenuItem item) {
        TextView title = new TextView(context);
        title.setText(item.getKey());
        
        View control = item.getControl();

        RelativeLayout.LayoutParams titleParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        titleParams.addRule(RelativeLayout.CENTER_VERTICAL);
        
        RelativeLayout.LayoutParams controlParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        controlParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        controlParams.addRule(RelativeLayout.CENTER_VERTICAL);
        
        RelativeLayout cell = new RelativeLayout(context);

        ViewGroup p = (ViewGroup) title.getParent();
        if (null != p) {
            p.removeView(title);
        }
        p = (ViewGroup) control.getParent();
        if (null != p) {
            p.removeView(control);
        }
        
        cell.addView(title, titleParams);
        cell.addView(control, controlParams);
        
        return cell;
    }

}