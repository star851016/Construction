package temp.chiu.com.clinico;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import temp.chiu.com.clinico.model.ConstructionlModel;

/**
 * Created by xin-ling on 2016/5/15.
 */
public class ConstructionListAdapter extends UltimateViewAdapter {

    Context context;
    List<ConstructionlModel> constructionlModelList = new ArrayList<ConstructionlModel>();

    public ConstructionListAdapter(Context context, List<ConstructionlModel> constructionlModelList) {
        this.context = context;
        this.constructionlModelList = constructionlModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_construction_list, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends UltimateRecyclerviewViewHolder {

        @Bind(R.id.item)
        LinearLayout itemLayout;

        @Bind(R.id.title)
        TextView title;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final ConstructionlModel data = constructionlModelList.get(position);

        viewHolder.title.setText("施工單位:"+data.getAPP_NAME() + "\n\n" + data.getC_NAME()+ "區" + data.getADDR());


        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstructionDetailActivity.launch((Activity) context, data);
            }
        });

    }


    @Override
    public int getAdapterItemCount() {
        return constructionlModelList.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return null;
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
