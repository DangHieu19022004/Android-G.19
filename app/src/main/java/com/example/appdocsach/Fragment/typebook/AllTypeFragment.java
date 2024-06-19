
package com.example.appdocsach.Fragment.typebook;

import android.content.Intent;
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

import com.example.appdocsach.Activity.BookDetailActivity;

import com.example.appdocsach.Adapter.BooksAdapterHorizontal;

import com.example.appdocsach.Adapter.viewpagerSlide;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class AllTypeFragment extends Fragment {

    private ViewPager viewPagerSlide;
    private CircleIndicator circleIndicatorSlide;
    private viewpagerSlide viewpagerSlideAdapter;
    private BooksAdapterHorizontal booksAdapter;
    private RecyclerView recyclerViewBooktrending, recyclerViewFavorite;
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
        recyclerViewFavorite = view.findViewById(R.id.recyclerViewFavorite);

        //

        //declare list book
        mListBooks = new ArrayList<>();
        List<BooksModel> mListFavoriteBooks = new ArrayList<>();

        //show to screen
        booksAdapter = new BooksAdapterHorizontal( mListBooks, new BooksAdapterHorizontal.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                showDetailBook(books);
            }
        });

        BooksAdapterHorizontal favoriteBooksAdapter = new BooksAdapterHorizontal(mListFavoriteBooks, new BooksAdapterHorizontal.IClickListener() { // New
            @Override
            public void onClickReadItemBook(BooksModel books) {
                showDetailBook(books);
            }
        });

        recyclerViewBooktrending.setAdapter(booksAdapter);
        recyclerViewFavorite.setAdapter(favoriteBooksAdapter);

        //show horizontal recycleview
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBooktrending.setLayoutManager(horizontalLayoutManager);

        LinearLayoutManager favoriteLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); // New
        recyclerViewFavorite.setLayoutManager(favoriteLayoutManager);


        //create list advance
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/slide%2FScreenshot%202024-06-16%20220014.png?alt=media&token=8882a5a7-caad-46f6-9d92-985ca31d62d4");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/slide%2FScreenshot%202024-06-16%20220122.png?alt=media&token=5c18bb1c-0aae-4a9e-b57c-bbdada5eef5d");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/slide%2FScreenshot%202024-06-16%20220254.png?alt=media&token=8c3df2c6-07dc-4f4c-8e41-4c89c71359df");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/slide%2FScreenshot%202024-06-16%20220359.png?alt=media&token=c74186c8-9761-42df-97ad-f6524bd2a26d");
//        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20000640.png?alt=media&token=3a540cc7-e93e-4895-9973-d47b7ca75be5");

        //show to slide + create circleindicator
        viewpagerSlideAdapter = new viewpagerSlide(getContext(), mangquangcao);
        viewPagerSlide.setAdapter(viewpagerSlideAdapter);
        circleIndicatorSlide.setViewPager(viewPagerSlide);
        viewpagerSlideAdapter.registerDataSetObserver(circleIndicatorSlide.getDataSetObserver());

        // Call method to get top viewed books
        getTopViewedBooks();

        //Call method to get top liked books
        getTopLikedBooks(mListFavoriteBooks, favoriteBooksAdapter);
    }

    private void showDetailBook(BooksModel books) {
        Intent it = new Intent(getActivity(), BookDetailActivity.class);
        it.putExtra("book_data", (Serializable) books); ///make serialize
//        it.putExtra("author", books.getAuthor());
//        it.putExtra("content", books.getContent());
//        it.putExtra("day", books.getDay());
//        it.putExtra("dislike", books.getDislikeCount());
//        it.putExtra("id", books.getId());
//        it.putExtra("img", books.getImg());
//        it.putExtra("like", books.getLike());
//        it.putExtra("subtitle", books.getSubtitle());
//        it.putExtra("title", books.getTitle());
//        it.putExtra("view", books.getView());
        startActivity(it);
    }

    private void getTopLikedBooks(List<BooksModel> listFavoriteBooks, BooksAdapterHorizontal favoriteBooksAdapter) {
        DatabaseReference booksRef = database.getReference("books").orderByChild("likeCount").limitToLast(10).getRef(); // Giới hạn số lượng sách lấy về
        booksRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);

                if (booksModel != null) {
                    listFavoriteBooks.add(0, booksModel);
                    favoriteBooksAdapter.notifyItemInserted(listFavoriteBooks.size() - 1);
                    recyclerViewFavorite.scrollToPosition(listFavoriteBooks.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    for (int i = 0; i < listFavoriteBooks.size(); i++) {
                        if (booksModel.getId() == listFavoriteBooks.get(i).getId()) {
                            listFavoriteBooks.set(i, booksModel);
                            favoriteBooksAdapter.notifyItemChanged(i);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    for (int i = 0; i < listFavoriteBooks.size(); i++) {
                        if (booksModel.getId() == listFavoriteBooks.get(i).getId()) {
                            listFavoriteBooks.remove(i);
                            favoriteBooksAdapter.notifyItemRemoved(i);
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
