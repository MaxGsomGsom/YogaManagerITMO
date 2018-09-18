package ru.gsomgsom.yogaitmo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;

public class MainActivity extends Activity {

    int timeOut = 2000;
    String HideWarnings = " $('div.note-warning, div.note-danger').hide(); ";
    String HideColumns = " $('div#attendance tr').each(function(index) { $('th, td', this).slice(1).hide(); }); ";
    String OnLessonChanged = " setTimeout(function() { $('select#P6_DATE').empty(); $('div#attendance th').slice(1).each(function(index) { $('select#P6_DATE').append(\"<option value='\" + index + \"'>\" + $(this).text() + \"</option>\"); " + HideWarnings + HideColumns + " }); }, " + timeOut + "); ";
    String OnDateChanged = HideColumns + " var colNum = parseInt($('select#P6_DATE').val()) + 1; $('div#attendance tr').each(function(index) { $('th:eq(' + colNum + ')', this).show(); $('td:eq(' + colNum + ')', this).show(); }); ";
    String AppendSelectNode =" $('div#R1294308328580210889 div.panel-body').append(\"<div class='form-group'><label for='P6_DATE' class='control-label optional' tabindex='999'>Дата</label><select id='P6_DATE' class='selectlist form-control' size='1'></select></div>\"); ";
    String OnLoaded = AppendSelectNode + HideWarnings + " $('select#P6_TEACHER_SELECT_CLASS').change(function() { " + OnLessonChanged + " }); $('select#P6_DATE').change(function() { " + OnDateChanged + " }); ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView web = findViewById(R.id.browser);

        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(OnLoaded, null);
            }
        });
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setAppCacheEnabled(true);

        web.loadUrl("https://isu.ifmo.ru/pls/apex/f?p=2153:6::");

    }
}
