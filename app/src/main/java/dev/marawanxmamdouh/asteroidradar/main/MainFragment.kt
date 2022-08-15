package dev.marawanxmamdouh.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.marawanxmamdouh.asteroidradar.R
import dev.marawanxmamdouh.asteroidradar.databinding.FragmentMainBinding
import dev.marawanxmamdouh.asteroidradar.repository.Filter

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(
            this,
            MainViewModel.Factory(activity.application)
        )[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.asteroidRecyclerView.adapter =
            RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
                viewModel.navigateToDetailFragment(it)
            })

        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onNavigateToDetailFragmentComplete()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.nextWeekAsteroids -> {
                        viewModel.refreshAsteroids(Filter.WEEK)
                        true
                    }
                    R.id.todayAsteroids -> {
                        viewModel.refreshAsteroids(Filter.TODAY)
                        true
                    }
                    R.id.savedAsteroids -> {
                        viewModel.refreshAsteroids(Filter.SAVED)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
