package com.mzj.mobiletest

data class Booking(
    val shipReference: String,
    val shipToken: String,
    val canIssueTicketChecking: Boolean,
    val expiryTime: String,
    val duration: Long,
    val segments: List<Segment>
)

data class Segment(
    val id: Long,
    val originAndDestinationPair: OriginAndDestinationPair
)

data class OriginAndDestinationPair(
    val destination: DestinationOrigin,
    val destinationCity: String,
    val origin: DestinationOrigin,
    val originCity: String
)

data class DestinationOrigin(
    val code: String,
    val displayName: String,
    val url: String
)
