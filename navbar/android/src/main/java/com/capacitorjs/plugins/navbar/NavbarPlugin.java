package com.capacitorjs.plugins.navbar;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.IOException;
import java.io.InputStream;

@CapacitorPlugin(name = "Navbar")
public class NavbarPlugin extends Plugin {

    private Navbar implementation = new Navbar();
    private TextView titleLabel;
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private LinearLayout navbar;
    int _navigation_bar_height = 44;


    @Override
    public void load() {
        super.load();
        leftBtn = new ImageButton(this.getContext());
        titleLabel = new TextView(this.getContext());
        rightBtn = new ImageButton(this.getContext());
        navbar = new LinearLayout(this.getContext());
        navbar.addView(leftBtn);
        navbar.addView(titleLabel);
        navbar.addView(rightBtn);

    }
    @PluginMethod
    public void setup(PluginCall call) {
        this.getActivity().runOnUiThread(() -> {
            if (navbar.getParent() == null) {
                FrameLayout content = (FrameLayout) this.getActivity().findViewById(android.R.id.content);
                _navigation_bar_height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, content.getResources().getDisplayMetrics());

                WebView webView = this.bridge.getWebView();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) webView.getLayoutParams();
                params.setMargins(0, _navigation_bar_height, 0, 0);
                webView.setLayoutParams(params);

                navbar.setOrientation(LinearLayout.HORIZONTAL);
                navbar.setBackgroundColor(Color.WHITE);
                FrameLayout.LayoutParams containerParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, _navigation_bar_height);
                navbar.setLayoutParams(containerParams);
                leftBtn.setBackgroundColor(Color.TRANSPARENT);
                leftBtn.setLayoutParams(new LinearLayout.LayoutParams(_navigation_bar_height, LinearLayout.LayoutParams.MATCH_PARENT));
                leftBtn.setVisibility(View.INVISIBLE);
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (webView.canGoBack()) {
                            webView.goBack();
                        }
                    }
                });

                titleLabel.setBackgroundColor(Color.WHITE);
                titleLabel.setTextColor(Color.BLACK);
                titleLabel.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                titleLabel.setGravity(Gravity.CENTER);
                titleLabel.setSingleLine(true);
                titleLabel.setEllipsize(TextUtils.TruncateAt.END);

                rightBtn.setBackgroundColor(Color.TRANSPARENT);
                rightBtn.setLayoutParams(new LinearLayout.LayoutParams(_navigation_bar_height, LinearLayout.LayoutParams.MATCH_PARENT));
                rightBtn.setVisibility(View.INVISIBLE);
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        notifyListeners("onRightClick", null);
                    }
                });
                content.addView(navbar);
            }
            call.resolve();
        });
    }

    @PluginMethod
    public void setTitle(PluginCall call) {
        String value = call.getString("value");
        this.getActivity().runOnUiThread(() -> {
            this.titleLabel.setText(value);
            call.resolve();
        });
    }
    @PluginMethod
    public void setLeftIcon(PluginCall call) {
        String normal = call.getString("normal");
        this.getActivity().runOnUiThread(() -> {
            try {
                AssetManager assetManager = getActivity().getAssets();
                InputStream inputStream = assetManager.open(normal);
                Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, _navigation_bar_height / 3, _navigation_bar_height / 3, true);
                this.leftBtn.setImageBitmap(resizedBitmap);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            call.resolve();
        });

    }
    @PluginMethod
    public void setLeftVisibility(PluginCall call) {
        Boolean value = call.getBoolean("value");
        this.getActivity().runOnUiThread(() -> {
            this.leftBtn.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
        });
        call.resolve();
    }
    @PluginMethod
    public void setRightIcon(PluginCall call) {
        String normal = call.getString("normal");
        this.getActivity().runOnUiThread(() -> {
            try {
                AssetManager assetManager = getActivity().getAssets();
                InputStream inputStream = assetManager.open(normal);
                Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, _navigation_bar_height / 3, _navigation_bar_height / 3, true);
                this.rightBtn.setImageBitmap(resizedBitmap);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            call.resolve();
        });
    }
    @PluginMethod
    public void setRightVisibility(PluginCall call) {
        Boolean value = call.getBoolean("value");
        this.getActivity().runOnUiThread(() -> {
            this.rightBtn.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
            call.resolve();
        });
    }
    
    @PluginMethod
    public void allowsBackForwardNavigationGestures(PluginCall call) {
        call.resolve();
    }
    @PluginMethod
    public void exitApp(PluginCall call) {
        System.exit(0);
        call.resolve();
    }
}