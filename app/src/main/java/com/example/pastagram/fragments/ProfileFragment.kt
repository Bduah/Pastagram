package com.example.pastagram.fragments

import android.util.Log
import com.example.pastagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : FeedFragment() {

    override fun queryPosts(){
        //Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //find all post objects
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.setLimit(20)
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e!=null){
                    Log.e(TAG, "Error fetching posts")
                }else{
                    if (posts != null){
                        adapter.clear()
                        for(post in posts){
                            Log.i(TAG,"Post: "+ post.getDescription()+" . Username: "+ post.getUser()?.username)
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()

                        swipeContainer.setRefreshing(false)
                    }


                }
            }

        })
    }
}