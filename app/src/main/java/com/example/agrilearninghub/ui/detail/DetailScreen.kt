package com.example.agrilearninghub.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import coil3.compose.rememberAsyncImagePainter
import org.koin.core.parameter.parametersOf

class DetailScreen(private val cropId: Long) : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<DetailScreenModel> { parametersOf(cropId) }
        val state by screenModel.state.collectAsStateWithLifecycle()

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(state.crop.image, filterQuality = FilterQuality.Low),
                    contentDescription = "Crop image",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(.35f)
                            .clip(shape = MaterialTheme.shapes.extraSmall),
                    contentScale = ContentScale.Crop
                )
                LazyColumn(modifier = Modifier.padding(16.dp).weight(.65f)) {
                    items(
                        listOf(
                            buildBoldAnnotatedString("নাম: ", state.crop.nameBn),
                            buildBoldAnnotatedString("বৈজ্ঞানিক নাম: ", state.crop.scientificName),
                            buildBoldAnnotatedString("পরিবার: ", state.crop.family),
                            buildBoldAnnotatedString("বিবরণ: ", state.crop.description)
                        )
                    ) { detail ->
                        DetailCard(detail)
                    }
                    items(
                        listOf(
                            buildBoldAnnotatedString("ঋতু: ", state.crop.season),
                            buildBoldAnnotatedString("জলবায়ু: ", state.crop.climate),
                            buildBoldAnnotatedString("মাটি: ", state.crop.soil),
                            buildBoldAnnotatedString("বীজ: ", state.crop.seed)
                        )
                    ) { detail ->
                        DetailCard(detail)
                    }
                    items(
                        listOf(
                            buildBoldAnnotatedString("বীজ রোপণ: ", state.crop.planting),
                            buildBoldAnnotatedString("সার: ", state.crop.fertilization),
                            buildBoldAnnotatedString("পানি দেওয়া: ", state.crop.irrigation),
                            buildBoldAnnotatedString("যত্ন: ", state.crop.care),
                            buildBoldAnnotatedString("ফসল সংগ্রহের সময়: ", state.crop.harvestTime),
                            buildBoldAnnotatedString("বাজার: ", state.crop.market)
                        )
                    ) { detail ->
                        DetailCard(detail)
                    }
                    items(
                        listOf(
                            buildBoldAnnotatedString("পুষ্টি: ", state.crop.nutrition),
                            buildBoldAnnotatedString("ব্যবহার: ", state.crop.uses)
                        )
                    ) { detail ->
                        DetailCard(detail)
                    }
                    items(
                        listOf(
                            buildBoldAnnotatedString("সাধারণ রোগ: ", state.crop.commonDiseases),
                            buildBoldAnnotatedString("সমাধান: ", state.crop.solutions)
                        )
                    ) { detail ->
                        DetailCard(detail)
                    }
                }
            }
        }
    }

    @Composable
    private fun DetailCard(detail: AnnotatedString) {
        Card(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()) {
            Text(text = detail, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
        }
    }

    private fun buildBoldAnnotatedString(
        prefix: String,
        text: String
    ): AnnotatedString {
        return buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(prefix)
            }
            append(text)
        }
    }
}