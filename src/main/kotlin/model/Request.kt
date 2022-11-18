package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Request(
    @SerialName("rawPath") val path:String,
    @SerialName("headers") val headers: Map<String,String>,
    @SerialName("requestContext") val requestContext: RequestContext,
    @SerialName("body") val body: String? = null
){
    fun getMethod():String = requestContext.http.method
}

@Serializable
data class RequestContext(
    @SerialName("http") val http: RequestHttp
)

@Serializable
data class RequestHttp(
    @SerialName("method") val method: String
)
