package mutl.touch.sample;

import mutl.touch.sample.imageviewer1.ViewArea;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity{
    private LinearLayout ll_viewArea;
    private LinearLayout.LayoutParams parm;
    private ViewArea viewArea;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title    
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
         //去掉Activity上面的状态栏  
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN,  
                       WindowManager.LayoutParams. FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_touchview);
        
        ll_viewArea = (LinearLayout) findViewById(R.id.ll_viewArea);
        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
        										LinearLayout.LayoutParams.MATCH_PARENT);
        viewArea = new ViewArea(MainActivity.this, R.drawable.test);    //自定义布局控件，用来初始化并存放自定义imageView
        ll_viewArea.addView(viewArea, parm);

    }
}
