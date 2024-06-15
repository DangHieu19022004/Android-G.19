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

import com.example.appdocsach.Adapter.BooksAdapter;
import com.example.appdocsach.Adapter.viewpagerSlide;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class AllTypeFragment extends Fragment {

    private ViewPager viewPagerSlide;
    private CircleIndicator circleIndicatorSlide;
    private viewpagerSlide viewpagerSlideAdapter;
    private BooksAdapter booksAdapter;
    private RecyclerView recyclerViewBooktrending;
    List<BooksModel> mListBooks;
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

        //

        //declare list book
        mListBooks = new ArrayList<>();
        //

        //show to screen
        booksAdapter = new BooksAdapter(getContext(), mListBooks, new BooksAdapter.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewBooktrending.setAdapter(booksAdapter);

        //show horizontal recycleview
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBooktrending.setLayoutManager(horizontalLayoutManager);


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

        // Call method to get top viewed books
        getTopViewedBooks();

        //Call method to get top liked books
        getTopLikedBooks();
    }

    private void getTopLikedBooks() {
        DatabaseReference booksRef = database.getReference("books").orderByChild("like").limitToLast(10).getRef(); // Giới hạn số lượng sách lấy về
        booksRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    mListBooks.add(0, booksModel);
                    booksAdapter.notifyItemInserted(mListBooks.size() - 1);
                    recyclerViewBooktrending.scrollToPosition(mListBooks.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    for (int i = 0; i < mListBooks.size(); i++) {
                        if (booksModel.getId() == mListBooks.get(i).getId()) {
                            mListBooks.set(i, booksModel);
                            booksAdapter.notifyItemChanged(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    for (int i = 0; i < mListBooks.size(); i++) {
                        if (booksModel.getId() == mListBooks.get(i).getId()) {
                            mListBooks.remove(i);
                            booksAdapter.notifyItemRemoved(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Xử lý khi có sự di chuyển dữ liệu (nếu cần thiết)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load top liked books: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTopViewedBooks() {
        DatabaseReference myRef = database.getReference("books").orderByChild("view").getRef();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    mListBooks.add(0, booksModel); // Thêm vào đầu danh sách để sắp xếp theo thứ tự lớn đến nhỏ

                    // Cập nhật RecyclerView
                    booksAdapter.notifyItemInserted(0); // Thông báo là có item được chèn vào vị trí đầu tiên
                    recyclerViewBooktrending.scrollToPosition(0); // Di chuyển đến vị trí đầu tiên
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    for (int i = 0; i < mListBooks.size(); i++) {
                        if (booksModel.getId() == mListBooks.get(i).getId()) {
                            mListBooks.set(i, booksModel);
                            booksAdapter.notifyItemChanged(i); // Cập nhật item thay đổi
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    for (int i = 0; i < mListBooks.size(); i++) {
                        if (booksModel.getId() == mListBooks.get(i).getId()) {
                            mListBooks.remove(i);
                            booksAdapter.notifyItemRemoved(i); // Xóa item khỏi danh sách
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Xử lý khi có sự di chuyển dữ liệu
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra trong quá trình truy vấn dữ liệu
            }
        });
    }
}