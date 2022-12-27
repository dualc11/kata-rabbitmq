package com.example.kata.twitter.model

data class SearchTweet(
        val id: Long,
        val editHistoryTweetIds: List<Long>,
        val text: String
)