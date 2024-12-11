import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

// Movie data class to hold movie details
data class Movie(val title: String, val overview: String, val posterPath: String)

// MovieApiService to handle network requests and fetch movies
object MovieApiService {
    private val client = OkHttpClient()

    // TMDb API key
    private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
    private const val BASE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY"

    // Fetch movies from the API
    fun fetchMovies(): List<Movie> {
        val request = Request.Builder()
            .url(BASE_URL)
            .build()

        // Execute the network call
        client.newCall(request).execute().use { response ->
            val jsonResponse = response.body?.string()
            val moviesList = mutableListOf<Movie>()

            if (!jsonResponse.isNullOrEmpty()) {
                val jsonObject = JSONObject(jsonResponse) // Proper use of JSONObject constructor
                val results = jsonObject.getJSONArray("results")

                for (i in 0 until results.length()) {
                    val movieJson = results.getJSONObject(i)
                    val title = movieJson.getString("title")
                    val overview = movieJson.getString("overview")
                    val posterPath = movieJson.getString("poster_path")
                    val movie = Movie(title, overview, posterPath)
                    moviesList.add(movie)
                }
            }
            return moviesList // Return the list of movies
        }
    }
}
