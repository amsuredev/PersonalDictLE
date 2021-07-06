package pl.amsuredev.personaldictionary.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_posts.*
import pl.amsuredev.personaldictionary.R
import pl.amsuredev.personaldictionary.data.Post
import pl.amsuredev.personaldictionary.databinding.FragmentListPostsBinding
import pl.amsuredev.personaldictionary.viewmodels.PostListViewModel
import java.util.*
import android.text.format.DateFormat
import javax.security.auth.callback.Callback

private const val DATE_PATTERN = "MMMM dd yyyy"
private const val TAG = "ListPostFragment"
class ListPostsFragment : Fragment() {
    private var callbacks: CallBacks? = null

    interface CallBacks {
        fun onItemSelected(postId: UUID)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as CallBacks?
    }

    private val postListViewModel: PostListViewModel by lazy{
        ViewModelProviders.of(this).get(PostListViewModel::class.java)
    }
    private var adapter: PostAdapter? = PostAdapter(emptyList())
    var _bind: FragmentListPostsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO REPLACE post_list WITH database elements
        _bind = FragmentListPostsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = _bind?.root

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_posts)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        return view
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            ListPostsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as AppCompatActivity).setSupportActionBar(_bind?.toolbar)
        setHasOptionsMenu(true)
        postListViewModel.postsLiveData.observe(viewLifecycleOwner, Observer {
            posts -> posts?.let {
            Log.i(TAG, "Got crimes ${posts.size}")
            updateUI(posts)
        }
        })
    }

    private fun updateUI(posts: List<Post>) {
        adapter?.posts = posts
        adapter?.notifyDataSetChanged()
    }

    private inner class PostAdapter(var posts: List<Post>) : RecyclerView.Adapter<PostHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
            val view = layoutInflater.inflate(R.layout.post_item, parent, false)
            return PostHolder(view)
        }

        override fun onBindViewHolder(holder: PostHolder, position: Int) {
            if (posts.isEmpty()){
                return
            }
            holder.bind(post = posts[position])
            holder.itemView.setOnClickListener {
                (context as CallBacks).onItemSelected(holder.post.id)
            }
        }

        override fun getItemCount(): Int {
            return posts.size
        }

    }

    private inner class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
        var dateText = view.findViewById<TextView>(R.id.date)
        lateinit var post: Post
        var descrText = view.findViewById<TextView>(R.id.description)
        var imageView = view.findViewById<ImageView>(R.id.image)
        fun bind(post: Post){
            this@PostHolder.post = post
            dateText.text = DateFormat.format(DATE_PATTERN, post?.date)
            // TODO image parse URI date format
            descrText.text = post.title.toString()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu_post_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId)
        {
            R.id.add -> {
                val post = Post()
                postListViewModel.addPost(post)
                callbacks?.onItemSelected(post.id)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}