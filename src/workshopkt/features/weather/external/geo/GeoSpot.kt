package workshopkt.features.weather.external.geo

data class GeoSpot (
    val location: GeoLocation
) {
    data class GeoLocation (
        val latitude: Double,
        val longitude: Double
    )
}
