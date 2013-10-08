package mutl.touch.sample.imageviewer1;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.FrameLayout;

public class ViewArea extends FrameLayout{  
	//ǰ��˵��ViewArea��һ�����֣� �������ﵱȻҪ�̳�һ�������ˡ�LinearLayoutҲ����
	
    private int imgDisplayW;    
    private int imgDisplayH;    
    private int imgW;        
    private int imgH;        
    private TouchView touchView;
    private DisplayMetrics dm;
    //resIdΪͼƬ��Դid
    public ViewArea(Context context,int resId) { //�ڶ���������ͼƬ����ԴID����ȻҲ�����ñ�ķ�ʽ��ȡͼƬ
/*        dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        imgDisplayW = dm.widthPixels;
        imgDisplayH = dm.heightPixels;*/ //���ַ�ʽ��ȡ����Ļ��С������ķ�ʽ�����һ���ģ�����480x800��i9000�ֱ��ʣ�
        super(context);
        imgDisplayW = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();//����Ŀ��Ҫ��xml�е�LinearLayout��Сһ�£����Ҫָ����С��xml�� LinearLayout�Ŀ��һ��Ҫ��px���ص�λ����Ϊ����Ŀ�������أ���dp������
        imgDisplayH = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
        
        touchView = new TouchView(context, imgDisplayW, imgDisplayH);//���������ǵ��Զ���ImageView
        touchView.setImageResource(resId);//�����ǵ��Զ���imageView����Ҫ��ʾ��ͼƬ
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
        imgW = img.getWidth();
        imgH = img.getHeight();
        //ͼƬ��һ�μ��ؽ������ж�ͼƬ��С�Ӷ�ȷ����һ��ͼƬ����ʾ��ʽ��
        int layout_w = imgW>imgDisplayW?imgDisplayW:imgW;  
        int layout_h = imgH>imgDisplayH?imgDisplayH:imgH;
        
        // ����Ĵ������ж�ͼƬ��ʼ��ʾ��ʽ�ģ���Ȼ���Ը�������뷨������ʾ��
        // �������ǽ�����ڸߵ�ͼƬ���տ���С�ı����Ѹ�ѹ����ǰ������ǿ�ȳ�������Ļ��С��
        // �෴������ߴ��ڿ��ҽ�ͼƬ���ո���С�ı����ѿ�ѹ����ǰ������Ǹ߶ȳ�������Ļ��С
        if(imgW>=imgH) {
            if(layout_w==imgDisplayW){
                layout_h = (int) (imgH*((float)imgDisplayW/imgW));
            }
        }else {
            if(layout_h==imgDisplayH){
                layout_w = (int) (imgW*((float)imgDisplayH/imgH));
            }
        }
        //������Ҫע����ǣ�
        //����FreamLayout����LinearLayout�ĺô��ǣ����ѹ�����ͼƬ����һ���ߴ�����Ļ��
        //��ôֻ��ʾ����Ļ�ڵĲ��֣�����ͨ���ƶ��󿴼��ⲿ������ü���ͼƬ����
        //�������RelativeLayout���֣�ͼƬ��ʼ��������ʾ����Ļ�ڲ��������г�����Ļ������
        //���ͼƬ������ȫռ����Ļ����ô����Ļ��û��ͼƬ�ĵط��϶���ͼƬҲ���ƶ���
        //���������鲻̫�ã�������FreamLayout����LinearLayout��
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(layout_w,layout_h);
        params.gravity = Gravity.CENTER;
        touchView.setLayoutParams(params);//�����Զ���imageView�Ĵ�С��Ҳ���Ǵ�����Χ

        this.addView(touchView);
    }
}
