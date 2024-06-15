package com.example.appdocsach.Fragment.typebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appdocsach.Adapter.BooksAdapterHorizontal;
import com.example.appdocsach.Adapter.viewpagerSlide;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

public class AllTypeFragment extends Fragment {

    private ViewPager viewPagerSlide;
    private CircleIndicator circleIndicatorSlide;
    private viewpagerSlide viewpagerSlideAdapter;
    private BooksAdapterHorizontal booksAdapterHorizontalTrending, booksAdapterHorizontalNew;
    private RecyclerView recyclerViewBooktrending, recyclerViewNew;
    List<BooksModel> mListBooksTrend, mListBooksNew;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Mapping view
        viewPagerSlide = view.findViewById(R.id.viewpagerSlide);
        circleIndicatorSlide = view.findViewById(R.id.circleindicatorSlide);
        recyclerViewBooktrending = view.findViewById(R.id.recyclerViewTrending);
        recyclerViewNew = view.findViewById(R.id.recyclerViewNew);
        //

        //declare list book
        mListBooksTrend = new ArrayList<>();
        mListBooksNew = new ArrayList<>();
        //

        //show to screen
        booksAdapterHorizontalTrending = new BooksAdapterHorizontal(mListBooksTrend, new BooksAdapterHorizontal.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewBooktrending.setAdapter(booksAdapterHorizontalTrending);

        booksAdapterHorizontalNew = new BooksAdapterHorizontal(mListBooksNew, new BooksAdapterHorizontal.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewNew.setAdapter(booksAdapterHorizontalNew);
        //

        //show horizontal recycleview
        LinearLayoutManager horizontalLayoutManagerTrend
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBooktrending.setLayoutManager(horizontalLayoutManagerTrend);

        LinearLayoutManager horizontalLayoutManagerNew
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNew.setLayoutManager(horizontalLayoutManagerNew);
        //
        //

        //create list advance
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20001954.png?alt=media&token=a3dd5fc6-1ea2-45a6-b8b0-5a85e9d453aa");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20002148.png?alt=media&token=d7223bba-23b8-4eab-ab28-76ed83798b6b");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20002342.png?alt=media&token=613fec23-ce1e-41fc-bc9c-b19690daec9d");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20002454.png?alt=media&token=d502cbdb-e53b-439e-800f-6dec2fbfd6b9");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20000640.png?alt=media&token=3a540cc7-e93e-4895-9973-d47b7ca75be5");

        viewpagerSlideAdapter = new viewpagerSlide(getContext(), mangquangcao);
        viewPagerSlide.setAdapter(viewpagerSlideAdapter);

        circleIndicatorSlide.setViewPager(viewPagerSlide);

        viewpagerSlideAdapter.registerDataSetObserver(circleIndicatorSlide.getDataSetObserver());
        //

        // call method get dtb
        getListRealtimeDTB();

    }


    private void getListRealtimeDTB() {
        DatabaseReference myRef = database.getReference("books");

        Query query = myRef.orderByChild("view");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel != null){
                    mListBooksTrend.add(0, booksModel); // sort large to small

                    // sort by date
                      mListBooksNew.add(booksModel);
                    Collections.sort(mListBooksNew, new Comparator<BooksModel>() {
                        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

                        @Override
                        public int compare(BooksModel book1, BooksModel book2) {
                            try {
                                Date date1 = originalFormat.parse(book1.getDay());
                                Date date2 = originalFormat.parse(book2.getDay());
                                String formattedDate1 = targetFormat.format(date1);
                                String formattedDate2 = targetFormat.format(date2);
                                return formattedDate2.compareTo(formattedDate1); // Sort new to old
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                    //

                    booksAdapterHorizontalTrending.setBooksList(mListBooksTrend); //reset adapter
                    booksAdapterHorizontalNew.setBooksList(mListBooksNew); // resetadapter

                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel == null || mListBooksTrend == null || mListBooksTrend.isEmpty()){return;}
                for(int i =0; i<mListBooksTrend.size(); i++){
                    if(booksModel.getId() == mListBooksTrend.get(i).getId()){
                        mListBooksTrend.set(i, booksModel);
                        break;
                    }
                }
                booksAdapterHorizontalTrending.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel == null || mListBooksTrend == null || mListBooksTrend.isEmpty()){return;}
                for(int i =0; i<mListBooksTrend.size(); i++){
                    if(booksModel.getId() == mListBooksTrend.get(i).getId()){
                        mListBooksTrend.remove(mListBooksTrend.get(i));
                        break;
                    }
                }
                booksAdapterHorizontalTrending.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}