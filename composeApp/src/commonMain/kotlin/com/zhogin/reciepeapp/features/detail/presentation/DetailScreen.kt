package com.zhogin.reciepeapp.features.detail.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.zhogin.reciepeapp.features.common.data.model.capitalizeFirstWord
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailRoute(
    recipeId: Long,
    onBackClick: () -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    detailViewModel: RecipeDetailViewModel = koinViewModel(),

    ) {
    LaunchedEffect(Unit) {
        detailViewModel.getRecipeDetail(recipeId)
    }

    var showAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val detailUiState = detailViewModel.detailUiState.collectAsStateWithLifecycle()
    val updateIsFavUiState = detailViewModel.updateIsFavUiState.collectAsStateWithLifecycle()
    val urlHandler = LocalUriHandler.current
    val onWatchVideoClick: (String) -> Unit = { link ->
        if (link.isNotEmpty()) {
            urlHandler.openUri(link)
        }
    }

    val onSaveClick: (RecipeItem) -> Unit = {
        if (!isUserLoggedIn()) {
            showAlertDialog = true
        } else {
            detailViewModel.updateIsFavorite(
                recipeId = it.id,
                isAdding = !it.isFavorite
            )
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                showAlertDialog = false
            },
            title = {
                Text(
                    text = "Update Favorites",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "Login to Add/Remove Favorites",
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = {
                        showAlertDialog = false
                        openLoginBottomSheet {
                            detailUiState.value.recipeItem?.let {
                                detailViewModel.updateIsFavorite(
                                    recipeId = it.id,
                                    isAdding = !it.isFavorite
                                )
                            }
                        }
                    }
                ) {
                    Text(
                        text = "Login"
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    onClick = {
                        showAlertDialog = false

                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }
        )
    }



    DetailScreen(
        onBackClick = onBackClick,
        uiState = detailUiState.value,
        onWatchVideoClick = onWatchVideoClick,
        updateIsFavUiState = updateIsFavUiState.value,
        onSaveClick = onSaveClick,
    )
}

@Composable
private fun DetailScreen(
    onBackClick: () -> Unit,
    uiState: RecipeDetailUiState,
    onWatchVideoClick: (String) -> Unit,
    updateIsFavUiState: RecipeDetailUpdateIsFavUiState,
    onSaveClick: (RecipeItem) -> Unit,
) {
    Scaffold(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                uiState.detailIsLoading -> {
                    LoadingScreen()
                }

                uiState.detailError != null -> {
                    ErrorScreen(uiState.detailError, onBackClick)
                }

                uiState.recipeItem != null -> {
                    RecipeDetailContent(
                        recipeItem = uiState.recipeItem,
                        onBackClick = onBackClick,
                        onWatchVideoClick = onWatchVideoClick,
                        onSaveClick = onSaveClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeDetailContent(
    recipeItem: RecipeItem,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    onSaveClick: (RecipeItem) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        RecipeMainContent(
            recipeItem = recipeItem,
            onWatchVideoClick = onWatchVideoClick
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(vertical = 32.dp, horizontal = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = { onSaveClick(recipeItem) },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (recipeItem.isFavorite) Icons.Default.Bookmark
                    else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun RecipeMainContent(
    recipeItem: RecipeItem,
    onWatchVideoClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = recipeItem.imageUrl,
            contentDescription = recipeItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp,
                    )
                )
        )
        RecipeDetails(
            title = recipeItem.title,
            duration = recipeItem.duration,
            rating = recipeItem.rating,
            difficulty = recipeItem.duration
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
        ) {
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = recipeItem.description,
                style = MaterialTheme.typography.bodySmall
            )
        }

        IngredientsList(
            ingredients = recipeItem.ingredients.map {
                val item = it.split(":")
                if (item.isNotEmpty() && item.size == 2) {
                    Pair(item[0].trim().capitalizeFirstWord(), item[1].trim())
                } else {
                    Pair("", "")
                }
            }.filter {
                it.first.isNotBlank() && it.second.isNotBlank()
            }.filterNot {
                it.first.contains("null") || it.second.contains("null")
            }
        )
        InstructionsList(
            instructions = recipeItem.instructions
        )
        WatchVideoButton(
            youtubeLink = recipeItem.youtubeLink,
            onWatchVideoClick = onWatchVideoClick
        )


    }
}

@Composable
private fun WatchVideoButton(
    youtubeLink: String,
    onWatchVideoClick: (String) -> Unit,
) {
    Button(
        onClick = { onWatchVideoClick(youtubeLink) },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = "Watch Video",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
private fun InstructionsList(
    instructions: List<String>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Instructions",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        instructions.forEachIndexed { index, value ->
            Text(
                text = "${index + 1} $value",
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}

@Composable
private fun IngredientsList(
    ingredients: List<Pair<String, String>>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        ingredients.forEach {
            IngredientItem(
                name = it.first,
                quantity = it.second
            )
        }

    }
}

@Composable
private fun IngredientItem(
    name: String,
    quantity: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = quantity,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun RecipeDetails(
    title: String,
    duration: String,
    rating: Long,
    difficulty: String,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Schedule,
                modifier = Modifier.size(16.dp),
                contentDescription = null,
            )
            Text(
                text = duration,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Star,
                modifier = Modifier.size(16.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer.copy(
                    alpha = 0.5f
                )
            )
            Text(
                text = "$rating stars",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = difficulty,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
private fun ErrorScreen(error: String, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBackClick
        ) {
            Text("Go back")
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}