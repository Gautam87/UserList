package gautam.blazon.com.userlist.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gautam.blazon.com.userlist.R;
import gautam.blazon.com.userlist.data.model.UserItem;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private List<UserItem> mList;

    private UserListAdapter.OnItemInteractionListener mListener;
    private Context mContext;

    public UserListAdapter() {
        mList = new ArrayList<>();
    }

    public UserListAdapter(Context context, List<UserItem> list, UserListAdapter.OnItemInteractionListener listener) {
        mContext = context;
        this.mList = list;
        mListener = listener;
    }


    public List<UserItem> getItemList() {
        return mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user)
        ImageView ivUser;
        @BindView(R.id.text_name)
        TextView tvName;
        @BindView(R.id.text_skills)
        TextView tvSkills;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_user_item_list, parent, false);
        return new UserListAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(UserListAdapter.MyViewHolder holder, final int position) {
        if(mList.get(position).getImage()!=null && !mList.get(position).getImage().equals("")) {
            Picasso.get()
                    .load(mList.get(position).getImage())
                    .resize(150, 150)
                    .placeholder(R.drawable.ic_user_default)
                    .centerCrop()
                    .into(holder.ivUser);
        }else{
            holder.ivUser.setImageResource(R.drawable.ic_user_default);
        }
        holder.tvName.setText(mList.get(position).getName().trim());
        holder.tvSkills.setText(mList.get(position).getSkills().trim());

//        holder.relativeUserProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onEditClicked(position);
//            }
//        });
//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onDeleteClicked(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void add(List<UserItem> p) {
        int previousDataSize = this.mList.size();
        this.mList.addAll(p);
        notifyItemRangeInserted(previousDataSize, p.size());
    }

    public void setItems(List<UserItem> p) {
        mList = p;
        notifyDataSetChanged();
    }

    public interface OnItemInteractionListener {
//        void onEditClicked(int position);
//
//        void onDeleteClicked(int position);
    }
}
