/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mmf.circleprogress.widget.fancycoverflow.samples.shared;

import java.util.List;
import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mmf.circleprogress.R;
import com.mmf.circleprogress.widget.fancycoverflow.FancyCoverFlow;
import com.mmf.circleprogress.widget.fancycoverflow.FancyCoverFlowAdapter;
import com.squareup.picasso.Picasso;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {

    @Override
    public int getCount() {
        return resultList.size();
    }
    private List<String> resultList;
	private Context context;
	 int mSelect = 0;   
	 public FancyCoverFlowSampleAdapter(List<String> resultList, Context context) {
		super();
		this.resultList = resultList;
		this.context = context;		
	}
    @Override
    public String getItem(int i) {
        return resultList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //设置选中的位置
    public void changeSelected(int positon){ 
        if(positon != mSelect){
         mSelect = positon;
        notifyDataSetChanged();
        }
       }

    @Override
    public View getCoverFlowItem(int i, View convertView, ViewGroup viewGroup) {
    	String itemMovie = resultList.get(i);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.image_item_small, null);
			convertView.setLayoutParams(new FancyCoverFlow.LayoutParams(convertDipOrPx(88, context)
               		,convertDipOrPx(114, context)));
			viewHolder.cinemaItemMovie = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}
		Picasso.with(context).load(itemMovie).into(viewHolder.cinemaItemMovie);
		if(mSelect==i){    
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));  
		}else{
	        convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
	     }

		return convertView;
	}
    public static int convertDipOrPx(int dip,Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

    private static class ViewHolder {
		private ImageView cinemaItemMovie;
	}
}
