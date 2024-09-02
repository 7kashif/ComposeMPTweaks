package com.kashif.composemptweaks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp


@Composable
fun HighlightedTextSearch() {
    val listState = rememberLazyListState()

    val mobileAppFacts = remember {
        listOf(
            "The Apple App Store, launched in 2008, was the first digital distribution platform for mobile apps, offering 500 apps at launch.",
            "On average, users spend 90% of their mobile time on apps, with social media and communication apps being the most popular.",
            "As of 2024, Android holds about 71% of the global mobile operating system market share, making it the most widely used mobile OS.",
            "The majority of mobile app revenue comes from in-app purchases and ads, with games being the top revenue generators.",
            "It’s estimated that more than 100,000 new apps are released every month across various platforms, especially on Android and iOS.",
            "Mobile games account for nearly 50% of the total gaming industry revenue, surpassing PC and console gaming combined.",
            "The average smartphone user has over 80 apps installed on their device but typically uses only about 9 apps daily.",
            "The first mobile phone call was made on April 3, 1973, by Martin Cooper, a Motorola researcher and executive.",
            "In 2023, there were over 3.5 billion smartphone users worldwide, which is nearly 45% of the global population.",
            "The most downloaded app of all time is Facebook, with over 5 billion downloads on the Google Play Store alone.",
            "The first SMS message was sent in 1992, and it simply read 'Merry Christmas'.",
            "The Google Play Store was originally called the Android Market when it launched in 2008.",
            "As of 2023, there are over 2.7 million apps available on the Google Play Store.",
            "The most popular category of apps on the Apple App Store is games, followed by business and education apps.",
            "In 2022, mobile apps generated over $935 billion in revenue globally.",
            "The average mobile app loses 77% of its daily active users within the first three days after installation.",
            "Mobile apps are expected to generate over $1 trillion in revenue by 2025.",
            "The first mobile app was a simple calculator app, created in 1993 for the IBM Simon Personal Communicator.",
            "In 2021, TikTok became the most downloaded app globally, surpassing Facebook and Instagram.",
            "The average cost to develop a mobile app ranges from $10,000 to $500,000, depending on the complexity and features."
        )
    }

    val filteredList = remember {
        mutableStateListOf("")
    }

    val (query, setQuery) = remember { mutableStateOf("") }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            filteredList.clear()
            filteredList.addAll(
                mobileAppFacts.filter {
                    it.contains(query, ignoreCase = true)
                }
            )
        } else {
            filteredList.clear()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = setQuery,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (filteredList.isNotEmpty() || query.isNotEmpty()) {
                items(filteredList) { item ->
                    HighlightableText(
                        text = item,
                        query = query
                    )
                    Spacer(Modifier.height(8.dp))
                    Divider()
                }
            } else {
                items(mobileAppFacts) { item ->
                    Text(
                        text = item
                    )
                    Spacer(Modifier.height(8.dp))
                    Divider()
                }
            }
        }
    }
}

@Composable
fun HighlightableText(
    text: String,
    query: String
) {
    Text(
        text = buildAnnotatedString {
            text.splitString(query).forEach {
                if (it.equals(query, true)) {
                    withStyle(
                        style = SpanStyle(
                            background = Green
                        )
                    ) {
                        append(it)
                    }
                } else {
                    append(it)
                }
            }
        }
    )
}

fun String.splitString(subString: String): List<String> {
    val index = this.indexOf(subString, ignoreCase = true)
    return if (index != -1) {
        listOf(
            this.substring(0, index), // Part before the substring
            this.substring(index, index + subString.length), // The exact substring as it appears in the original string
            this.substring(index + subString.length) // Part after the substring
        )
    } else {
        listOf(this) // Return the original string if the substring is not found
    }
}
