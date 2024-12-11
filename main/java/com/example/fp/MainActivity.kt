import android.os.Bundle
import com.example.bf2.databinding.ActivityMainBind
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bf2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Correct inflation of binding
        setContentView(binding.root)

        lifecycleScope.launch {
            try {
                val movies = withContext(Dispatchers.IO) {
                    MovieApiService.fetchMovies()
                }
                val adapter = MovieAdapter(movies)
                binding.rvMovies.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvMovies.adapter = adapter
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error loading movies", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
