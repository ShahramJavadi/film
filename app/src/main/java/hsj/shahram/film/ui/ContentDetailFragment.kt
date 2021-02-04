package hsj.shahram.film.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import hsj.shahram.film.AppController
import hsj.shahram.film.R
import hsj.shahram.film.databinding.FragmentContentBinding
import hsj.shahram.film.databinding.FragmentContentDetailBinding
import hsj.shahram.film.util.Const
import hsj.shahram.film.util.Status
import hsj.shahram.film.viewmodel.MainViewModel
import hsj.shahram.film.viewmodel.ViewModelFactory
import javax.inject.Inject

class ContentDetailFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentContentDetailBinding

    @Inject
    lateinit var factory: ViewModelFactory
    val args: ContentDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_content_detail,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {


        (activity?.application as AppController).appComponent
            .contentDetailFragmentComponent().build()
            .inject(this)


        setupViewModel()
        clickListener()
        contentDetailObserver()
        favoriteItemObserver()
        viewModel.getContentDetail(args.contentItem.id)
        viewModel.getFavoriteById(args.contentItem.id)


    }


    private fun favoriteItemObserver() {

        viewModel.favoriteItemLiveData().observe(viewLifecycleOwner) {

            it?.let {


                if (it.serverId == args.contentItem.id) {
                    binding.fab.setImageResource(R.drawable.favorite)
                    binding.fab.setTag(Const.FAVORITE)
                }
            }


        }

    }

    private fun contentDetailObserver() {

        viewModel.contentDetailLiveData().observe(viewLifecycleOwner) {


            it.let {
                when (it.status) {

                    Status.LOADING -> binding.inProgress = true
                    Status.SUCCESS -> {

                        binding.inProgress = false
                        binding.model = it.data

                    }
                    Status.ERROR -> {

                        binding.inProgress = false
                       Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()


                    }


                }


            }

        }

    }

    private fun clickListener() {
        binding.back.setOnClickListener {
            findNavController().navigateUp()

        }

        binding.fab.setOnClickListener {


            if (binding.fab.tag.toString() == Const.FAVORITE) {
                unFavorite()
            } else {

                favorite()
            }
        }

    }


    private fun unFavorite() {

        viewModel.deleteFavorite(args.contentItem.id)
        binding.fab.setImageResource(R.drawable.unfavorite)
        binding.fab.tag = Const.UNFAVORITE

    }

    private fun favorite() {


        viewModel.insertToFavorite(args.contentItem)
        binding.fab.setImageResource(R.drawable.favorite)
        binding.fab.tag = Const.FAVORITE



    }

    private fun setupViewModel() {

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

    }


}