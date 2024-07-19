package com.kashif.composemptweaks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class SampleData(
    val title: String = "",
    val subTitles: List<String> = emptyList()
)

val sampleDataList: List<SampleData> = listOf(
    SampleData(
        "A",
        listOf("Apple", "Ant", "Axe", "Arrow", "Airplane", "Apron", "Anchor", "Almond", "Artist")
    ),
    SampleData(
        "B",
        listOf("Banana", "Book", "Ball", "Box", "Butterfly", "Boat", "Button", "Bottle", "Bridge")
    ),
    SampleData(
        "C",
        listOf("Cat", "Car", "Cake", "Cup", "Camera", "Candle", "Castle", "Chair", "Cloud")
    ),
    SampleData(
        "D",
        listOf("Dog", "Drum", "Door", "Duck", "Doll", "Desk", "Diamond", "Donkey", "Dragon")
    ),
    SampleData(
        "E",
        listOf("Elephant", "Eagle", "Egg", "Engine", "Envelope", "Ear", "Earth", "Echo", "Eel")
    ),
    SampleData(
        "F",
        listOf("Fish", "Frog", "Flag", "Fan", "Feather", "Fence", "Fire", "Flute", "Flower")
    ),
    SampleData(
        "G",
        listOf("Goat", "Guitar", "Glass", "Gift", "Gate", "Giraffe", "Globe", "Glove", "Grass")
    ),
    SampleData(
        "H",
        listOf("Horse", "Hat", "House", "Hammer", "Heart", "Helicopter", "Helmet", "Hill", "Honey")
    ),
    SampleData(
        "I",
        listOf("Ice", "Iron", "Island", "Igloo", "Ink", "Insect", "Ivory", "Iceberg", "Internet")
    ),
    SampleData(
        "J",
        listOf("Jaguar", "Jar", "Jacket", "Jewel", "Jet", "Jam", "Jungle", "Juice", "Joystick")
    ),
    SampleData(
        "K",
        listOf("Kangaroo", "Key", "Kite", "Knife", "King", "Kitten", "Kiwi", "Kettle", "Keyboard")
    ),
    SampleData(
        "L",
        listOf("Lion", "Lamp", "Leaf", "Ladder", "Lake", "Laptop", "Lock", "Lemon", "Lighthouse")
    ),
    SampleData(
        "M",
        listOf(
            "Monkey",
            "Moon",
            "Mountain",
            "Mouse",
            "Mask",
            "Medal",
            "Milk",
            "Mirror",
            "Microscope"
        )
    ),
    SampleData(
        "N",
        listOf(
            "Nose",
            "Nest",
            "Notebook",
            "Nail",
            "Needle",
            "Nut",
            "Necklace",
            "Newspaper",
            "Night"
        )
    ),
    SampleData(
        "O",
        listOf("Owl", "Orange", "Ocean", "Octopus", "Oven", "Onion", "Ostrich", "Oil", "Orbit")
    ),
    SampleData(
        "P",
        listOf("Pig", "Pen", "Pineapple", "Piano", "Plate", "Pumpkin", "Parrot", "Plane", "Pizza")
    ),
    SampleData(
        "Q",
        listOf("Queen", "Quilt", "Quail", "Quartz", "Quiver", "Quince", "Quiz", "Quote", "Quadrant")
    ),
    SampleData(
        "R",
        listOf("Rabbit", "Ring", "Robot", "Rocket", "Rose", "Rainbow", "Radio", "Ruler", "River")
    ),
    SampleData(
        "S",
        listOf("Snake", "Sun", "Star", "Ship", "Shirt", "Sword", "School", "Seal", "Sand")
    ),
    SampleData(
        "T",
        listOf("Tiger", "Tree", "Train", "Table", "Truck", "Tomato", "Turtle", "Telephone", "Tent")
    ),
    SampleData(
        "U",
        listOf(
            "Umbrella",
            "Unicorn",
            "Uniform",
            "Urn",
            "Umpire",
            "Universe",
            "Uranium",
            "U-turn",
            "Ulcer"
        )
    ),
    SampleData(
        "V",
        listOf(
            "Van",
            "Vase",
            "Violin",
            "Volcano",
            "Vampire",
            "Vine",
            "Vulture",
            "Vest",
            "Vegetable"
        )
    ),
    SampleData(
        "W",
        listOf("Wolf", "Whale", "Window", "Water", "Wagon", "Watch", "Wheel", "Worm", "Windmill")
    ),
    SampleData(
        "X",
        listOf(
            "Xylophone",
            "X-ray",
            "Xenon",
            "Xerox",
            "Xylophonist",
            "Xylograph",
            "Xylem",
            "Xanadu",
            "Xenolith"
        )
    ),
    SampleData(
        "Y",
        listOf("Yak", "Yacht", "Yarn", "Yam", "Yogurt", "Yard", "Yellow", "Yawn", "Youth")
    ),
    SampleData(
        "Z",
        listOf("Zebra", "Zipper", "Zoo", "Zeppelin", "Zinc", "Zombie", "Zucchini", "Zone", "Zenith")
    )
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoupledScroller() {
    val titlesColumnsState = rememberLazyListState()
    val subTitlesColumnsState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val firstVisibleSubTitle by remember {
        derivedStateOf {
            subTitlesColumnsState.firstVisibleItemIndex
        }
    }

    var selectedData by remember {
        mutableStateOf(SampleData())
    }

    var titleBoxHeight by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = firstVisibleSubTitle) {
        selectedData = sampleDataList[firstVisibleSubTitle]
        val itemsInfo = titlesColumnsState.layoutInfo.visibleItemsInfo

        if (firstVisibleSubTitle > itemsInfo.last().index)
            titlesColumnsState.animateScrollBy(titleBoxHeight)
        else if (firstVisibleSubTitle < itemsInfo.first().index)
            titlesColumnsState.animateScrollBy(-titleBoxHeight)
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .fillMaxHeight(),
            state = titlesColumnsState
        ) {
            items(sampleDataList) { data ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(if (selectedData.title == data.title) Orange else Color.Transparent)
                        .clickable {
                            selectedData = data
                            coroutineScope.launch {
                                subTitlesColumnsState.animateScrollToItem(
                                    index = sampleDataList.indexOf(
                                        data
                                    )
                                )
                            }
                        }
                        .graphicsLayer {
                            titleBoxHeight = this.size.height
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = data.title, style = MaterialTheme.typography.h1)
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .fillMaxHeight(),
            state = subTitlesColumnsState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleDataList) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = it.title, style = MaterialTheme.typography.h1)
                    Divider()
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        maxItemsInEachRow = 3
                    ) {
                        it.subTitles.forEach {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .height(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = it, style = MaterialTheme.typography.h1)
                            }
                        }
                    }
                }
            }
        }
    }
}