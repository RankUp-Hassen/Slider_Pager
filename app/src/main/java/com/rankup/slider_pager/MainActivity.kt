package com.rankup.slider_pager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.rankup.slider_pager.ui.theme.Slider_PagerTheme
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        setContent {
            Slider_PagerTheme {

                SliderP()
            }
        }
    }
}
data class DataList( val image: Int, val text:String, val des:String)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SliderP(){

    val dataList1 = DataList(R.drawable._691234128300, "hello  ", "Description 1")
    val dataList2 = DataList(R.drawable._691234128286, "Text 2", "Description 2")
    val dataList3 = DataList(R.drawable._691234128272, "Text 3", "Description 3")
    val pageState= rememberPagerState(initialPage = 0)
    val slideList= listOf(dataList1,dataList2,dataList3)
    val scope = rememberCoroutineScope()
    val text = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        HorizontalPager(
            count = slideList.size,
            state = pageState,
            contentPadding = PaddingValues(horizontal = 50.dp),
            modifier = Modifier
                .height(500.dp)
                .background(Color.Transparent)
                .padding(top = 70.dp)
        ) {p->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(25.dp),

                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(p).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                            .also { scale ->
                                scaleX = scale
                                scaleY = scale


                            }

                    }
                    .fillMaxSize()
                    .shadow(
                        15.dp,
                        shape = RoundedCornerShape(25.dp),
                        spotColor = colorResource(id = R.color.blue)
                    )

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(

                        alignment = Alignment.TopCenter,
                        painter = painterResource(id = slideList[p].image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)

                    )


                }








            }

        }




        text.value=
            if (  (pageState.pageCount)-pageState.currentPage==1) "Go" else "Next"

        Row(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            repeat(slideList.size){
                val color =
                    if (pageState.currentPage==it) colorResource(id = R.color.yyy) else Color.LightGray
                val w=
                    if (pageState.currentPage==it) 30 else 20

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .width(w.dp)
                        .height(20.dp)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pageState.animateScrollToPage(it)
                            }
                        }
                )
            }

        }

        val context = LocalContext.current

        Button(

            onClick = {
                if (pageState.currentPage < pageState.pageCount-1){
                    scope.launch {
                        pageState.animateScrollToPage(pageState.currentPage+1)
                    }


                }
                if(text.value=="Go"){

                    context.startActivity(Intent(context,MainActivity2::class.java))


                }


            },
            colors = ButtonDefaults.buttonColors(
                containerColor =  colorResource(id =R.color.blueback)
            ),
            modifier = Modifier
                .width(250.dp)
                .padding(top = 15.dp),

            shape = RoundedCornerShape(25.dp)

        )
        {
            Text(
                text = text.value,
                color = Color.White,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic
            )

        }



        Button(


            onClick = {
                      context.startActivity(Intent(context,MainActivity2::class.java))


            },
            colors = ButtonDefaults.buttonColors(
                containerColor =  Color.White
            ),
            modifier = Modifier
                .width(250.dp),

            shape = RoundedCornerShape(25.dp),
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.blueback)
            )


        )
        {
            Text(
                text = "Skip",
                color =  colorResource(id =R.color.blueback),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic
            )

        }





    }
}
