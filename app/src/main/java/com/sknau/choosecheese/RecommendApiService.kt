
import com.sknau.choosecheese.ChartData

import retrofit2.Call
import retrofit2.http.GET

interface RecommendApiService {
    @GET("recommend")
    suspend fun getViewPagerData(): ChartData
}

interface PieChartApiService{
    @GET("recommend")
    fun getChartData(): Call<ChartData>
}


