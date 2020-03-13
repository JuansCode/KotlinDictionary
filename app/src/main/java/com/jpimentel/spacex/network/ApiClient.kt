package com.jpimentel.spacex.network

import com.jpimentel.spacex.model.SpaceXModel
import com.jpimentel.spacex.network.Constants.ORDER_OF_RESULT
import com.jpimentel.spacex.network.Constants.SORT_RESULT_BY
import io.reactivex.Single

class ApiClient {

    private val apiService: RestService =
        ApiEndPoint.retrofitInstance.create(RestService::class.java)

    fun getSpaceXList(limit: Int, offset: Int): Single<List<SpaceXModel>> =
        apiService.getSpaceXData(limit, offset, SORT_RESULT_BY, ORDER_OF_RESULT)
}