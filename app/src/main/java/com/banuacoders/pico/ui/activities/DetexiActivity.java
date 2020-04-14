package com.banuacoders.pico.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.banuacoders.pico.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetexiActivity extends AppCompatActivity {

    @BindView(R.id.webview_detexi)
    WebView webView;

    @BindView(R.id.progress_detexi)
    ProgressBar progressBar;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detexi);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        unbinder = ButterKnife.bind(this);
        WebView webView = new WebView(this);
        WebSettings ws = webView.getSettings();
        ws.setSaveFormData(true);
        LoadWeb("https://corona.detexi.id/");
    }

    private void LoadWeb(String Url) {
        Sprite sprite = new Wave();
        sprite.setColor(getResources().getColor(R.color.colorPrimary));
        progressBar.setIndeterminateDrawable(sprite);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(Url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                progressBar.setVisibility(View.GONE);
                String svgIcon = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"64pt\" height=\"64pt\" viewBox=\"0 0 64 64\" version=\"1.1\">\n" +
                        "<g id=\"surface1\">\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(92.941176%,91.372549%,84.705882%);fill-opacity:1;\" d=\"M 62.125 48.761719 L 1.875 48.761719 C 0.839844 48.761719 0 47.921875 0 46.886719 L 0 1.875 C 0 0.839844 0.839844 0 1.875 0 L 62.125 0 C 63.160156 0 64 0.839844 64 1.875 L 64 46.886719 C 64 47.921875 63.160156 48.761719 62.125 48.761719 Z M 62.125 48.761719 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(83.921569%,82.745098%,77.254902%);fill-opacity:1;\" d=\"M 62.125 0 L 32 0 L 32 48.761719 L 62.125 48.761719 C 63.160156 48.761719 64 47.921875 64 46.886719 L 64 1.875 C 64 0.839844 63.160156 0 62.125 0 Z M 62.125 0 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(100%,36.078431%,25.882353%);fill-opacity:1;\" d=\"M 62.125 0 L 1.875 0 C 0.839844 0 0 0.839844 0 1.875 L 0 9.375 L 64 9.375 L 64 1.875 C 64 0.839844 63.160156 0 62.125 0 Z M 62.125 0 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(85.098039%,19.215686%,10.980392%);fill-opacity:1;\" d=\"M 62.125 0 L 32 0 L 32 9.378906 L 64 9.378906 L 64 1.875 C 64 0.839844 63.160156 0 62.125 0 Z M 62.125 0 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(63.137255%,61.568627%,55.294118%);fill-opacity:1;\" d=\"M 49 64 C 48.503906 64 48.027344 63.800781 47.675781 63.449219 L 42.554688 58.332031 L 40.367188 60.523438 C 39.917969 60.972656 39.269531 61.164062 38.648438 61.03125 C 38.027344 60.898438 37.515625 60.460938 37.289062 59.867188 L 29.664062 39.898438 C 29.398438 39.207031 29.566406 38.425781 30.089844 37.90625 C 30.613281 37.382812 31.394531 37.214844 32.085938 37.476562 L 52.046875 45.105469 C 52.640625 45.332031 53.078125 45.84375 53.210938 46.464844 C 53.34375 47.085938 53.152344 47.734375 52.703125 48.183594 L 50.511719 50.375 L 55.632812 55.496094 C 56.363281 56.226562 56.363281 57.414062 55.632812 58.144531 L 50.328125 63.449219 C 49.976562 63.800781 49.5 64 49 64 Z M 49 64 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(52.941176%,51.764706%,46.666667%);fill-opacity:1;\" d=\"M 29.664062 39.898438 L 37.289062 59.867188 C 37.515625 60.460938 38.027344 60.898438 38.648438 61.03125 C 39.269531 61.164062 39.917969 60.972656 40.367188 60.523438 L 42.554688 58.332031 L 47.675781 63.449219 C 48.027344 63.800781 48.503906 64 49 64 C 49.5 64 49.976562 63.800781 50.328125 63.449219 L 52.980469 60.796875 L 30.089844 37.902344 C 29.566406 38.425781 29.398438 39.207031 29.664062 39.898438 Z M 29.664062 39.898438 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(52.941176%,51.764706%,46.666667%);fill-opacity:1;\" d=\"M 10.761719 20.425781 L 13.199219 20.425781 C 13.6875 20.425781 13.960938 20.894531 13.960938 21.414062 C 13.960938 21.855469 13.734375 22.375 13.199219 22.375 L 10.761719 22.375 L 10.761719 25.136719 L 15.117188 25.136719 C 15.605469 25.136719 15.878906 25.65625 15.878906 26.257812 C 15.878906 26.777344 15.652344 27.347656 15.117188 27.347656 L 9.332031 27.347656 C 8.777344 27.347656 8.226562 27.085938 8.226562 26.566406 L 8.226562 16.25 C 8.226562 15.726562 8.777344 15.46875 9.332031 15.46875 L 15.117188 15.46875 C 15.652344 15.46875 15.878906 16.039062 15.878906 16.554688 C 15.878906 17.15625 15.605469 17.679688 15.117188 17.679688 L 10.761719 17.679688 Z M 10.761719 20.425781 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(52.941176%,51.764706%,46.666667%);fill-opacity:1;\" d=\"M 17.632812 16.230469 C 17.632812 15.824219 17.945312 15.46875 18.414062 15.46875 L 21.777344 15.46875 C 24.003906 15.46875 25.777344 16.296875 25.777344 18.945312 C 25.777344 20.765625 24.949219 21.804688 23.808594 22.246094 L 25.792969 25.835938 C 25.859375 25.933594 25.875 26.046875 25.875 26.128906 C 25.875 26.761719 25.027344 27.445312 24.28125 27.445312 C 23.957031 27.445312 23.648438 27.316406 23.46875 26.972656 L 21.242188 22.652344 L 20.171875 22.652344 L 20.171875 26.566406 C 20.171875 27.085938 19.535156 27.347656 18.902344 27.347656 C 18.269531 27.347656 17.632812 27.085938 17.632812 26.566406 Z M 20.171875 17.679688 L 20.171875 20.699219 L 21.777344 20.699219 C 22.6875 20.699219 23.242188 20.328125 23.242188 19.191406 C 23.242188 18.050781 22.6875 17.679688 21.777344 17.679688 Z M 20.171875 17.679688 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(52.941176%,51.764706%,46.666667%);fill-opacity:1;\" d=\"M 27.773438 16.230469 C 27.773438 15.824219 28.085938 15.46875 28.554688 15.46875 L 31.917969 15.46875 C 34.144531 15.46875 35.917969 16.296875 35.917969 18.945312 C 35.917969 20.765625 35.085938 21.804688 33.949219 22.246094 L 35.933594 25.835938 C 35.996094 25.933594 36.011719 26.046875 36.011719 26.128906 C 36.011719 26.761719 35.167969 27.445312 34.421875 27.445312 C 34.097656 27.445312 33.789062 27.316406 33.609375 26.972656 L 31.382812 22.652344 L 30.308594 22.652344 L 30.308594 26.566406 C 30.308594 27.085938 29.675781 27.347656 29.042969 27.347656 C 28.410156 27.347656 27.773438 27.085938 27.773438 26.566406 Z M 30.308594 17.679688 L 30.308594 20.699219 L 31.917969 20.699219 C 32.828125 20.699219 33.382812 20.328125 33.382812 19.191406 C 33.382812 18.050781 32.828125 17.679688 31.917969 17.679688 Z M 30.308594 17.679688 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(29.803922%,29.019608%,25.098039%);fill-opacity:1;\" d=\"M 33.949219 22.242188 C 35.085938 21.804688 35.917969 20.765625 35.917969 18.945312 C 35.917969 16.296875 34.144531 15.46875 31.917969 15.46875 L 31.847656 15.46875 L 31.847656 17.679688 L 31.917969 17.679688 C 32.828125 17.679688 33.382812 18.050781 33.382812 19.1875 C 33.382812 20.328125 32.828125 20.699219 31.917969 20.699219 L 31.847656 20.699219 L 31.847656 23.550781 L 33.609375 26.972656 C 33.789062 27.3125 34.097656 27.445312 34.421875 27.445312 C 35.167969 27.445312 36.015625 26.761719 36.015625 26.128906 C 36.015625 26.046875 36 25.933594 35.933594 25.835938 Z M 33.949219 22.242188 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(29.803922%,29.019608%,25.098039%);fill-opacity:1;\" d=\"M 37.785156 23.609375 L 37.785156 19.304688 C 37.785156 16.507812 39.523438 15.46875 41.765625 15.46875 C 44.007812 15.46875 45.761719 16.507812 45.761719 19.304688 L 45.761719 23.609375 C 45.761719 26.40625 44.007812 27.445312 41.765625 27.445312 C 39.523438 27.445312 37.785156 26.40625 37.785156 23.609375 Z M 43.230469 19.304688 C 43.230469 18.179688 42.675781 17.679688 41.765625 17.679688 C 40.855469 17.679688 40.320312 18.179688 40.320312 19.304688 L 40.320312 23.609375 C 40.320312 24.730469 40.855469 25.234375 41.765625 25.234375 C 42.675781 25.234375 43.230469 24.730469 43.230469 23.609375 Z M 43.230469 19.304688 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(29.803922%,29.019608%,25.098039%);fill-opacity:1;\" d=\"M 48.039062 16.230469 C 48.039062 15.824219 48.347656 15.46875 48.820312 15.46875 L 52.183594 15.46875 C 54.410156 15.46875 56.179688 16.296875 56.179688 18.945312 C 56.179688 20.765625 55.351562 21.804688 54.214844 22.246094 L 56.195312 25.835938 C 56.261719 25.933594 56.277344 26.046875 56.277344 26.128906 C 56.277344 26.761719 55.433594 27.445312 54.683594 27.445312 C 54.359375 27.445312 54.050781 27.316406 53.871094 26.972656 L 51.644531 22.652344 L 50.574219 22.652344 L 50.574219 26.566406 C 50.574219 27.085938 49.941406 27.347656 49.304688 27.347656 C 48.671875 27.347656 48.039062 27.085938 48.039062 26.566406 Z M 50.574219 17.679688 L 50.574219 20.699219 L 52.183594 20.699219 C 53.09375 20.699219 53.644531 20.328125 53.644531 19.191406 C 53.644531 18.050781 53.09375 17.679688 52.183594 17.679688 Z M 50.574219 17.679688 \"/>\n" +
                        "<path style=\" stroke:none;fill-rule:nonzero;fill:rgb(85.098039%,19.215686%,10.980392%);fill-opacity:1;\" d=\"M 15.027344 36.511719 L 16.65625 34.882812 C 17.390625 34.152344 17.390625 32.964844 16.65625 32.230469 C 15.925781 31.5 14.738281 31.5 14.003906 32.230469 L 12.375 33.859375 L 10.746094 32.230469 C 10.015625 31.5 8.828125 31.5 8.09375 32.230469 C 7.363281 32.964844 7.363281 34.152344 8.09375 34.882812 L 9.726562 36.511719 L 8.09375 38.140625 C 7.363281 38.875 7.363281 40.0625 8.09375 40.792969 C 8.460938 41.160156 8.941406 41.34375 9.421875 41.34375 C 9.902344 41.34375 10.382812 41.160156 10.746094 40.792969 L 12.375 39.164062 L 14.003906 40.792969 C 14.371094 41.160156 14.851562 41.34375 15.332031 41.34375 C 15.8125 41.34375 16.289062 41.160156 16.65625 40.792969 C 17.390625 40.0625 17.390625 38.875 16.65625 38.140625 Z M 15.027344 36.511719 \"/>\n" +
                        "</g>\n" +
                        "</svg>\n";
                String htmlData = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "\t<style type=\"text/css\">\n" +
                        "\t\tdiv {\n" +
                        "\t\t\tmargin: 50% auto;\n" +
                        "\t\t\tdisplay: block;\n" +
                        "\t\t\ttext-align: center;\n" +
                        "\t\t}\n" +
                        "\t</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "\t<div>" + svgIcon + "<br> \n" +
                        "\n" +
                        "\t\t<p>Pastikan anda terhubung ke internet!</p>\n" +
                        "\t</div>\n" +
                        "\n" +
                        "\t\n" +
                        "</body>\n" +
                        "</html>";
                webView.loadUrl("about:blank");
                webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
                webView.invalidate();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (webView.getProgress() > 40)
                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
