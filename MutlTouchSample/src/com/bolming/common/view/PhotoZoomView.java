package com.bolming.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class PhotoZoomView extends ImageView {
	
	private final static String TAG = "PhotoZoomView";

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;	
	
	private GestureDetector mGestureDetector;
	
	private int mode = NONE;
	private float oldDist;
	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	private float xtranlate = 0, ytranslate = 0, mscale = 1;
	private PointF start = new PointF();
	private PointF mid = new PointF();	

	public PhotoZoomView(Context context) {
		this(context, null);
	}

	public PhotoZoomView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PhotoZoomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mGestureDetector = new GestureDetector(
				new GestureDetector.SimpleOnGestureListener() {

					@Override
					public boolean onDoubleTapEvent(MotionEvent e) {

						switch (e.getAction()) {
						case MotionEvent.ACTION_UP:
							restore();
							break;
						default:
							return super.onDoubleTapEvent(e);
						}
						return true;
					}
				});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.e(TAG, "w: " + getWidth() + ", h: " + getHeight());
		moveToCenter();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			mode = DRAG;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:	
			if (mode == DRAG) {
				Log.d(TAG, String.format("xtranslate: %f, ytranslate: %f\n",
								event.getX() - start.x, event.getY() - start.y));
				xtranlate += event.getX() - start.x;
				ytranslate += event.getY() - start.y;
			}else if(mode == ZOOM){
				float newDist = spacing(event);
				if (newDist > 10f) {
					float scale = newDist / oldDist;
					mscale *= scale;
					xtranlate *= scale;
					ytranslate *= scale;
					xtranlate += - mid.x * (scale - 1);
					ytranslate += - mid.y * (scale - 1);
					Log.d(TAG, String.format("scarle: %f\n", scale));					
				}
			}	
			
			mode = NONE;			
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
				
				xtranlate += event.getX() - start.x;
				ytranslate += event.getY() - start.y;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, 
						event.getY() - start.y);	
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);					
				}
			}
			break;

		default:			
			return super.onTouchEvent(event);
		}	

		setImageMatrix(matrix);
		mGestureDetector.onTouchEvent(event);
		return true;
	}
	
	private void moveToCenter(){
		matrix.set(savedMatrix);		
		final float vw = getWidth();
		final float vh = getHeight();
		final float imgw = getDrawable().getIntrinsicWidth();
		final float imgh = getDrawable().getIntrinsicHeight();
		
		float scale = 1;
		if(imgw / imgh > vw / vh){
			scale = vw / imgw;
		}else{
			scale = vh / imgh;
		}
		
		matrix.postScale(scale, scale, 0, 0);		
		matrix.postTranslate((vw - imgw * scale) / 2, (vh - imgh * scale )/ 2);
		
		setImageMatrix(matrix);
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
	
	private void restore(){
		Log.e(TAG, String.format("scarle: %f, xtranlate: %f, ytranlate: %f\n", mscale, xtranlate, ytranslate));
		
		matrix.set(savedMatrix);
		matrix.postTranslate(-xtranlate, -ytranslate);
		matrix.postScale(1 / mscale, 1 / mscale);
		
		xtranlate = 0;
		ytranslate = 0;
		mscale = 1;
		
		setImageMatrix(matrix);
	}
}
