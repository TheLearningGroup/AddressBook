package com.example.muchao.addressbook.slide;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.muchao.addressbook.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by muchao on 16/2/24.
 */
public class CycleViewPager extends FrameLayout {
    private static final String TAG = CycleViewPager.class.getSimpleName();

    //自定义轮播图的资源
    private String[] imageUrls = {
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg",// 虚假的前一页
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg",
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",// 虚假的后一页
    };

    private ImageLoader imageLoader = ImageLoader.getInstance();

    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 3000;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;

    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    //放圆点的View的list
    private List<View> dotViewsList;

    private ViewPager viewPager;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    private static final int MSG_SLIDE = 1;

    private Context context;

    //Handler
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d(TAG, "handleMessage: " + currentItem);
            super.handleMessage(msg);
            handler.removeMessages(MSG_SLIDE);
            currentItem++;
            if (currentItem > viewPager.getAdapter().getCount() - 2) {
                currentItem = 1;
            }
            viewPager.setCurrentItem(currentItem);
            handler.sendEmptyMessageDelayed(MSG_SLIDE, TIME_INTERVAL);
        }
    };

    public CycleViewPager(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public CycleViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        initImageLoader(context);

        initData();
        if (isAutoPlay) {
            startPlay();
        }
    }

    private void startPlay() {
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 0, TIME_INTERVAL, TimeUnit.SECONDS);
        handler.sendEmptyMessageDelayed(MSG_SLIDE, TIME_INTERVAL);
    }

//    private class SlideShowTask implements Runnable {
//        @Override
//        public void run() {
//            synchronized (viewPager) {
//                currentItem = (currentItem + 1);
//                handler.obtainMessage().sendToTarget();
//            }
//        }
//    }

    public void initData() {
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();

        // 异步任务获取图片
        new GetListTask().execute("");
    }

    private void initUI(Context context) {

        if (imageUrls == null || imageUrls.length < 1) {
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.fragment_slide_content, this, true);
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view = new ImageView(context);
            view.setTag(imageUrls[i]);
//            if (i == 0) {
//                view.setBackgroundResource(R.drawable.photo);
//            }
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(view);

            ImageView dotView = new ImageView(context);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotView.setBackgroundResource(R.drawable.dot_focus);
            dotViewsList.add(dotView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build(); // Remove
        ImageLoader.getInstance().init(config);
    }

    class GetListTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                initUI(context);
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            Log.d(TAG, "instantiateItem: " + position);

            if (position < 1) {
                position = imageViewsList.size() - 2;
            } else if (position == imageViewsList.size() - 1) {
                position = 1;
            }

            ImageView imageView = imageViewsList.get(position);
            imageLoader.displayImage(imageView.getTag() + "", imageView);
            ViewParent viewParent = imageView.getParent();
            if (viewParent != null) {
                ViewGroup parent = (ViewGroup) viewParent;
                parent.removeView(imageView);
            }

            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    int position = viewPager.getCurrentItem();
                    int count = viewPager.getAdapter().getCount();
                    Log.d(TAG, "onPageScrollStateChanged: " + position);
                    Log.d(TAG, "onPageScrollStateChanged: " + count);
                    if (position == 0) {
                        viewPager.setCurrentItem(count - 2, false);
                    } else if (position == count - 1) {
                        viewPager.setCurrentItem(1, false);
                    }
                    break;
            }
        }

        @Override
        public void onPageSelected(int position) {
//            if (imageViewsList.size() > 1) {
//                if (position < 1) {
//                    currentItem = imageViewsList.size() - 2;
//                } else if (position > imageViewsList.size() - 1) {
//                    currentItem = 1;
//                }
//                viewPager.setCurrentItem(currentItem, false);
//                for (int i = 0; i < dotViewsList.size(); i++) {
//                    if (i == position) {
//                        ((View) dotViewsList.get(position)).setBackgroundResource(R.drawable.dot_focus);
//                    } else {
//                        ((View) dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_blur);
//                    }
//                }
//        }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }
}
