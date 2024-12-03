package com.example.agrilearninghub.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.agrilearninghub.R

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 0u,
                title = "হোম",
                icon =
                    rememberVectorPainter(
                        image = Icons.Default.Home
                    )
            )
        }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Image(
                painter = painterResource(id = R.drawable.asset_3),
                contentDescription = "Home_Image",
                modifier =
                    Modifier
                        .size(220.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.agri_learning_hub_logo),
                    contentDescription = "Home_Image",
                    modifier =
                        Modifier
                            .size(120.dp)
                            .padding(start = 8.dp)
                )
                Text(text = "এগ্রি লার্নিং হাবে স্বাগতম!", fontSize = 24.sp)
                Text(
                    text = "এখানে আপনি ফসল, সবজি, উদ্ভিদ, চাষাবাদ সম্পর্কিত তথ্য এবং আরও অনেক কিছু শিখতে পারবেন!",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(text = "চলুন শুরু করা যাক।", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.asset_2),
                contentDescription = "Home_Image",
                modifier =
                    Modifier
                        .size(150.dp)
                        .padding(end = 8.dp)
                        .align(Alignment.End)
            )
        }
    }
}