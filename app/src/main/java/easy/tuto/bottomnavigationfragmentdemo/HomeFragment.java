package easy.tuto.bottomnavigationfragmentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
//10120052_Eddy Rochman_If-2//


public class HomeFragment extends Fragment {

    private ListView listView;
    private DatabaseAcces databaseAccess;
    private List<Memo> memos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);
        Button btnBuat = view.findViewById(R.id.btnBuat);

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseAccess = DatabaseAcces.getInstance(getActivity());
        databaseAccess.open();
        memos = databaseAccess.getAllMemos();
        databaseAccess.close();
        MemoAdapter adapter = new MemoAdapter(getActivity(), memos);
        listView.setAdapter(adapter);
    }

    private class MemoAdapter extends ArrayAdapter<Memo> {

        MemoAdapter(@NonNull Context context, List<Memo> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_item, parent, false);
            }

            Button btnEdit = convertView.findViewById(R.id.btnEdit);
            Button btnDelete = convertView.findViewById(R.id.btnDelete);

            TextView txtDate = convertView.findViewById(R.id.txtDate);
            TextView txtMemo = convertView.findViewById(R.id.txtMemo);

            final Memo memo = getItem(position);
            txtDate.setText(memo.getDate());
            txtMemo.setText(memo.getShortText());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EditActivity.class);
                    intent.putExtra("MEMO", memo);
                    startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseAccess.open();
                    databaseAccess.delete(memo);
                    databaseAccess.close();

                    MemoAdapter adapter = (MemoAdapter) listView.getAdapter();
                    adapter.remove(memo);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}