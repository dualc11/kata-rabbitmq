package com.example.kata.twitter.model

data class Tweet(
        val id: Long,
        val editHistoryTweetIds: List<Long>,
        val text: String
)