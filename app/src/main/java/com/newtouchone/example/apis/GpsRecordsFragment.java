package com.newtouchone.example.apis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newtouchone.example.apis.domain.Position;
import com.newtouchone.example.apis.manager.GpsManager;

import java.util.List;


/**
 * 百度定位
 * @author xiangli.ma
 *
 */
public class GpsRecordsFragment extends Fragment {

    private RecyclerView mRecyclerView;

	private List<Position> mSampleList;

	private GpsManager mGpsManager;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_records, container, false);
        init(savedInstanceState);
        return rootView;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fillView();
		setListener();
		loadData();
	}

	
	/**
	 * 初始化
	 * 
	 * @param savedInstanceState
	 */
	protected void init(Bundle savedInstanceState) {
		mGpsManager = new GpsManager(getActivity());
	}
	/**
	 * 获取View对象
	 */
	protected void fillView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}
	/**
	 * 设置监听事件
	 */
	protected void setListener() {
	}
	/**
	 * 加载填充数据
	 */
	protected void loadData() {
		mSampleList = mGpsManager.findAllPositionDatas();
		mRecyclerView.setAdapter(new SampleAdapter());
	}

	private class SampleAdapter extends RecyclerView.Adapter<SampleHolder> {

		@Override
		public SampleHolder onCreateViewHolder(ViewGroup parent, int pos) {
			View view = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.list_item_record, parent, false);
			return new SampleHolder(view);
		}

		@Override
		public void onBindViewHolder(SampleHolder holder, int pos) {
			Position data = mSampleList.get(pos);
			holder.bindData(data, pos);
		}

		@Override
		public int getItemCount() {
			return mSampleList.size();
		}
	}

	private class SampleHolder extends RecyclerView.ViewHolder {

		private View mRootView;
		private TextView mTextView1;
		private TextView mTextView2;
		private TextView mTextView3;

		private Position mData;

		public SampleHolder(View itemView) {
			super(itemView);

			mRootView = itemView;
			mTextView1 = (TextView) itemView.findViewById(R.id.text1);
			mTextView2 = (TextView) itemView.findViewById(R.id.text2);
			mTextView3 = (TextView) itemView.findViewById(R.id.text3);
		}

		public void bindData(Position data, int position) {
			mData = data;

//			mRootView.setBackgroundResource(mData.get(KEY_COLOR));

			mTextView1.setText(mData.getTime());
			mTextView2.setText(mData.getAddrStr());
			mTextView3.setText(mData.getUploaded() ? "已上传" : "未上传");

			int index = position % 3;
			switch (index) {
				case 0:
					mRootView.setBackgroundResource(R.color.saffron);
					break;
				case 1:
					mRootView.setBackgroundResource(R.color.eggplant);
					break;
				case 2:
					mRootView.setBackgroundResource(R.color.sienna);
					break;
			}
		}
	}
}
