package com.rmwl.rcchgwd.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;


/**
 * 加载类
 * @author wangbin
 *
 */
public class LoadingView extends RelativeLayout {
	private LinearLayout mNoContent;
	private TextView mNoContentTxt;
	private TextView mNoContentTxtLeft;
	private TextView mNoContentTxtRight;
	private ImageView mNoContentIco;
	private LinearLayout mLoading;
	private TextView mLoadingTxt;
//	private GifView mLoadingIco;
    private ImageView mLoadingIco;
	private LinearLayout mLoadError;
	private OnRetryListener mOnRetryListener;
	private OnClickGoListener mOnClickGoListener;
	private ImageView imageView1;
	private TextView tv_content,tv_refresh;
//	private TextView tv_ok;//底部新增加按钮（发布车辆，添加员工等）

	public LoadingView(Context context) {
		super(context, null);
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@SuppressLint("NewApi")
	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr,
                       int defStyleRes) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.loading_view, this);

		//暂无内容
		mNoContent = (LinearLayout) findViewById(R.id.nocontent);//暂无类容布局
		mNoContentTxt = (TextView) findViewById(R.id.nocontent_txt);//暂无类容字体
		mNoContentIco = (ImageView) findViewById(R.id.nocontent_ico);//暂无内龙icon

//		mNoContentTxtLeft = (TextView) findViewById(R.id.nocontent_txt_left);//暂无类容字体
//		mNoContentTxtRight = (TextView) findViewById(R.id.nocontent_txt_right);//暂无类容字体

		//加载中...
		mLoading = (LinearLayout) findViewById(R.id.loading);//加载
		mLoadingTxt = (TextView) findViewById(R.id.loading_txt);//加载字体
		mLoadingIco= (ImageView) findViewById(R.id.loading_ico);//加载动图
		mLoadingIco.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.up_loading));

//		mLoadingIco= (GifView) findViewById(R.id.loading_ico);//加载动图
//		mLoadingIco.setGifResource(R.drawable.animt);
//		mLoadingIco.setGifResource(R.drawable.load);


		//加载失败
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_refresh = (TextView) findViewById(R.id.tv_refresh);

		mLoadError = (LinearLayout) findViewById(R.id.loaderror);//失败的

		mLoadError.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnRetryListener != null){
					mOnRetryListener.OnRetry();
				}
				loading();
			}
		});

		loading();
	}


	public void noContent() {
//		mLoadingIco.pause();
		setVisibility(View.VISIBLE);
		mNoContent.setVisibility(View.VISIBLE);
		mLoading.setVisibility(View.GONE);
		mLoadError.setVisibility(View.GONE);
	}


	/**
	 * 设置暂无内容文案默认"暂无内容"
	 * @param s
	 */
	public void setNoContentTxt(String s) {
		mNoContentTxt.setText(s);
	}


	/**设置暂无内容图片
	 * @param r
	 */
	public void setNoContentIco(int r) {
		mNoContentIco.setImageResource(r);
	}

	public void loading() {
		setVisibility(View.VISIBLE);
//		mLoadingIco.play();
		mNoContent.setVisibility(View.GONE);
		mLoading.setVisibility(View.VISIBLE);
		mLoadError.setVisibility(View.GONE);
	}


	/**
	 * 设置加载字体默认加载中..
	 * @param s
	 */
	public void setLoadingTxt(String s) {
		mLoadingTxt.setText(s);
	}

	public void setLoadingTxt(int r) {
		mLoadingTxt.setText(r);
	}


	public void setLoadingIco(int r) {
		mLoadingIco.setImageResource(r);
	}

	public void loadComplete() {
		setVisibility(View.GONE);

	}
	
	public void loadError(){
		setVisibility(View.VISIBLE);
		mNoContent.setVisibility(View.GONE);
		mLoading.setVisibility(View.GONE);
		mLoadError.setVisibility(View.VISIBLE);
	}


	public interface OnRetryListener{
		public void OnRetry();
	}
	public void setOnRetryListener(OnRetryListener l){
		mOnRetryListener = l;
	}

	public interface OnClickGoListener{
		void click();
	}

	public void setOnClickGoListener(OnClickGoListener l){
		this.mOnClickGoListener = l;
	}
}
