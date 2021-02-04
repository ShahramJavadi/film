package hsj.shahram.film.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hsj.shahram.film.AppController
import hsj.shahram.film.R
import hsj.shahram.film.data.model.RequestObject
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.databinding.FragmentContentBinding
import hsj.shahram.film.ui.adapter.ContentListAdapter
import hsj.shahram.film.util.Status
import hsj.shahram.film.viewmodel.MainViewModel
import hsj.shahram.film.viewmodel.ViewModelFactory
import javax.inject.Inject

class ContentFragment : Fragment(), ContentListAdapter.MyClickListener {

    private lateinit var binding: FragmentContentBinding
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var adapter: ContentListAdapter

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


    private fun init() {

        (context?.applicationContext as AppController)
            .appComponent.contentFragmentComponent()
            .setClickListener(this).build().inject(this)

        setupViewModel()
        setupRecyclerView()
        contentStatusObserver()
        contentListObserver()


    }


    private fun contentListObserver() {
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            binding.inProgress = false
            adapter.submitList(it)
        }

    }


    private fun setupViewModel() {

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

    }

    private fun setupRecyclerView() {

        binding.rvContentList.layoutManager = LinearLayoutManager(activity)
        binding.rvContentList.adapter = adapter


    }


    private fun contentStatusObserver()
    {

        viewModel.contentStatusLiveData().observe(viewLifecycleOwner){

            it?.let {

               when(it.status){

                   Status.LOADING -> binding.inProgress = true
                   Status.SUCCESS -> binding.inProgress = false
                   Status.ERROR -> {binding.inProgress = false
                   Toast.makeText(context , it.message , Toast.LENGTH_SHORT).show()
                      }


               }



            }

        }

    }
    override fun onClicked(model: ResponseObject.ContentList?) {


        if(model == null) return
        val bundle = Bundle()
        bundle.putParcelable("contentItem" , model)

        findNavController().navigate(R.id.action_contentFragment_to_contentDetailFragment ,
            bundle)


    }


    override fun onFavoriteClicked(model: ResponseObject.ContentList?) {

        if(model != null)
        viewModel.insertToFavorite(model)

    }

    override fun onUnFavoriteClicked(model: ResponseObject.ContentList?) {


        if(model != null)
            viewModel.deleteFavorite(model.id)


    }


}