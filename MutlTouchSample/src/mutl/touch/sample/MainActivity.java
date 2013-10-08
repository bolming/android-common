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
        //ȥ��title    
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
         //ȥ��Activity�����״̬��  
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN,  
                       WindowManager.LayoutParams. FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_touchview);
        
        ll_viewArea = (LinearLayout) findViewById(R.id.ll_viewArea);
        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
        										LinearLayout.LayoutParams.MATCH_PARENT);
        viewArea = new ViewArea(MainActivity.this, R.drawable.test);    //�Զ��岼�ֿؼ���������ʼ��������Զ���imageView
        ll_viewArea.addView(viewArea, parm);

    }
}
