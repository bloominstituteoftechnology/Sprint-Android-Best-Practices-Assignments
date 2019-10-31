package com.lambdaschool.readinglist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "MainActivity","This is main activity!")

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1")
        bundle.putString(FirebaseAnalytics.Param.CONTENT, "2")

        FirebaseAnalytics.getInstance(this).logEvent("main_activity_view", bundle)

        context = this

        preferences = getSharedPreferences(Constants.BOOK_PREFERENCES, Context.MODE_PRIVATE)

        add_book_button.setOnClickListener {


            val bundle2 = Bundle()
            bundle2.putString(FirebaseAnalytics.Param.ITEM_ID, "1")
            bundle2.putString(FirebaseAnalytics.Param.CONTENT, "2")

            FirebaseAnalytics.getInstance(this).logEvent("main_activity_view", bundle2)

            val intent = Intent(context, EditBookActivity::class.java)
            val nextId = BookRepo.nextId().toString()
            intent.putExtra(Constants.NEW_BOOK_TAG, nextId)
            startActivityForResult(intent, Constants.NEW_BOOK_REQUEST_CODE)
        }

    }

    override fun onResume() {
        super.onResume()

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1")
        bundle.putString(FirebaseAnalytics.Param.CONTENT, "2")

        FirebaseAnalytics.getInstance(this).logEvent("main_activity_view", bundle)
        book_scroll_view.removeAllViews()
        book_scroll_view.addView(BooksController.getBooksView(context!!))

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.NEW_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                BooksController.handleEditActivityResult(data)
            }
        }
        if (requestCode == Constants.EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                BooksController.handleEditActivityResult(data)
            }
        }
    }

    companion object {
         var preferences: SharedPreferences? = null
    }
}
