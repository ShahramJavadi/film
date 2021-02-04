package hsj.shahram.film.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hsj.shahram.film.AppController
import hsj.shahram.film.R
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.databinding.FragmentContentBinding
import hsj.shahram.film.ui.adapter.ContentListAdapter
import hsj.shahram.film.ui.adapter.FavoriteListAdapter
import hsj.shahram.film.viewmodel.MainViewModel
import hsj.shahram.film.viewmodel.ViewModelFactory
import javax.inject.Inject

class FavoriteContentFragment : Fragment(), FavoriteListAdapter.OnFavoriteItemListener {


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentContentBinding

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var adapter: FavoriteListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_content, container, false
        )


        init()

        return binding.root
    }


    private fun setupViewModel() {

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun init() {


        (context?.applicationContext as AppController)
            .appComponent.favoriteFragmentComponent().setFavoriteClickListener(this)
            .build().inject(this)

        setupViewModel()
        setupRecyclerView()
        favoriteListObserver()


    }


    private fun favoriteListObserver() {


        viewModel.favoriteLiveData().observe(viewLifecycleOwner) {

            adapter.submitList(it)

        }

    }

    private fun setupRecyclerView() {

        binding.rvContentList.layoutManager = LinearLayoutManager(context)
        binding.rvContentList.adapter = adapter

    }

    override fun onItemClicked(contentList: ResponseObject.ContentList) {


        if (contentList == null) return
        val bundle = Bundle()
        bundle.putParcelable("contentItem", contentList)

        findNavController().navigate(
            R.id.action_favoriteContentFragment_to_contentDetailFragment,
            bundle
        )


    }

    override fun onFAvoriteIconClicked(contentId: Int, position: Int) {
        viewModel.deleteFavorite(contentId)
        adapter.deleteItem(position)

    }
}