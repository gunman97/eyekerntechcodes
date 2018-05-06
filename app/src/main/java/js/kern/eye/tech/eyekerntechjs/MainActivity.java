package js.kern.eye.tech.eyekerntechjs;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebViewInterface mWebViewInterface;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebViewInterface = new WebViewInterface(MainActivity.this, mWebView); //JavascriptInterface 객체화
        mWebView.addJavascriptInterface(mWebViewInterface, "Android"); //웹뷰에 JavascriptInterface를 연결

        mWebView.loadUrl("file:///android_asset/result.html");

        Button btn = findViewById(R.id.callJS);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //안드로이드에서 html속 자바스크립트를 호출하는 곳
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:helloJS();");
                    }
                });
            }
        });
    }


    public class WebViewInterface {

        private WebView mAppView;
        private Activity mContext;

        public WebViewInterface(Activity activity, WebView view) {
            mAppView = view;
            mContext = activity;
        }

        //html의 JS에서 안드로이드의 코드를 호출하면 호출되는 곳^^
        @JavascriptInterface
        public void toastLong (String message) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }

        //html의 JS에서 안드로이드의 코드를 호출하면 호출되는 곳^^
        @JavascriptInterface
        public void toastShort (String message) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}
