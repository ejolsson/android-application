package com.onetoucheasy.restauranteofertas.data.remote.fakes

import com.onetoucheasy.restauranteofertas.repository.remote.RemoteDataSource
import com.onetoucheasy.restauranteofertas.repository.remote.request.SignUpRequestBody
import com.onetoucheasy.restauranteofertas.repository.remote.response.JWTResponse
import com.onetoucheasy.restauranteofertas.repository.remote.response.Offer
import com.onetoucheasy.restauranteofertas.repository.remote.response.OffersResponse
import com.onetoucheasy.restauranteofertas.utils.generateJWTResponse

class FakeRemoteDataSource: RemoteDataSource {

    override suspend fun performLogin(loginData: String): JWTResponse? {
        return generateJWTResponse()
    }

    override suspend fun performSignUp(signUpRequestBody: SignUpRequestBody): JWTResponse? {
        return generateJWTResponse()
    }

    override suspend fun getOfferById(offerId: String): Offer {
        /*return Offer("1",
            "2x1",
            "2x1 en Toda la carta",
            "",
            "11:00",
            "13:00",
            "10:00")*/
        TODO("Not yet implemented")
    }
    override suspend fun getOffers(): OffersResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getRestaurants(): OffersResponse {
        TODO("Not yet implemented")
    }
}