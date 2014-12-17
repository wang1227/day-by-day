package function;

import java.util.List;

import org.w3c.dom.Text;

import com.lhtblog.daybyday.R;

import DatabaseTest.Info;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 这是填充ListView的适配器
 * 重写了SimpleCursorAdapter方法
 */
public class ListViewAdapter extends BaseAdapter {
	Context context;
	List<Info> list;
	public ListViewAdapter(Context context,List<Info> list) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=View.inflate(context.getApplicationContext(), R.layout.listview, null);
		int[] colors = { Color.rgb(253, 253, 234), Color.rgb(254, 241, 252),
				Color.rgb(226, 230, 252), Color.rgb(252, 224, 229),
				Color.rgb(227, 225, 225) };
		view.setBackgroundColor(colors[position % 5]);
		TextView id=(TextView) view.findViewById(R.id.tv_lv_id);
		id.setText(String.valueOf(list.get(position).getid()));
		TextView title=(TextView) view.findViewById(R.id.tv_lv_show);
		title.setText(list.get(position).getTitle().toString());
		return view;
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		// 第一个参数为listview的item序号，每个item的序号是不同的。
//		// 第二个参数为View的缓存，当listview的item过多时，拖动会遮住一部分item，被遮住的item的view就是convertView保存着。
//		// 第三个参数表示是一个viewgroup（View组）
//		View view = null;
//		// if(convertView!=null){
//		// view = convertView;//使用缓存的view，节约内存
//		// 当滚动条回到之前被遮住的item时，直接使用convertView，而不必再去new view（）
//		// }
//		// else{
//		view = super.getView(position, convertView, parent);
//		TextView tv = (TextView) view.findViewById(R.id.tv_lv_show);
//		tv.setTextColor(cursor.getInt(cursor.getColumnIndex("textcolor")));
//		// }
//		int[] colors = { Color.rgb(253, 253, 234), Color.rgb(254, 241, 252),
//				Color.rgb(226, 230, 252), Color.rgb(252, 224, 229),
//				Color.rgb(227, 225, 225) };
//		view.setBackgroundColor(colors[position % 5]);
//		// return super.getView(position, convertView, parent);
////		 if(cursor != null){
////		cursor.close();
////		 }
//		return view;
//	}

}
