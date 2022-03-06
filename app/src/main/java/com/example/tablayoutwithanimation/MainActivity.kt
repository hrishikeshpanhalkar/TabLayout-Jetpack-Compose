package com.example.tablayoutwithanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tablayoutwithanimation.ui.theme.Denim
import com.example.tablayoutwithanimation.ui.theme.Malibu
import com.example.tablayoutwithanimation.ui.theme.TabLayoutWithAnimationTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tabItems = listOf("Music", "Movies", "Apps")
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()

            TabLayoutWithAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    Column {
                        TabRow(
                            selectedTabIndex = pagerState.currentPage,
                            backgroundColor = Malibu,
                            modifier = Modifier
                                .padding(all = 20.dp)
                                .background(color = Color.Transparent)
                                .clip(RoundedCornerShape(30.dp)),
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier
                                        .pagerTabIndicatorOffset(
                                            pagerState, tabPositions
                                        )
                                        .width(0.dp)
                                        .height(0.dp)
                                )
                            }
                        ) {
                            tabItems.forEachIndexed { index, title ->
                                val color = remember {
                                    Animatable(Denim)
                                }

                                LaunchedEffect(
                                    pagerState.currentPage == index
                                ) {
                                    color.animateTo(if (pagerState.currentPage == index) Color.White else Malibu)
                                }
                                Tab(
                                    text = {
                                        Text(
                                            title,
                                            style = if (pagerState.currentPage == index) TextStyle(
                                                color = Denim,
                                                fontSize = 18.sp
                                            ) else TextStyle(color = Denim, fontSize = 16.sp)
                                        )
                                    },
                                    selected = pagerState.currentPage == index,
                                    modifier = Modifier.background(
                                        color = color.value,
                                        shape = RoundedCornerShape(30.dp)
                                    ),
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                                )
                            }
                        }
                        HorizontalPager(
                            count = tabItems.size,
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Blue)
                        ) { page ->
                            Text(
                                text = tabItems[page],
                                modifier = Modifier.padding(50.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
