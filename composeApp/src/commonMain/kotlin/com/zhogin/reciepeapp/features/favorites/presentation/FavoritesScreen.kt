package com.zhogin.reciepeapp.features.favorites.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import com.zhogin.reciepeapp.features.common.presentation.ErrorContent
import com.zhogin.reciepeapp.features.common.presentation.Loader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesRoute(
    favoriteRecipeViewModel: FavoriteRecipeViewModel = koinViewModel(),
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
) {

    val uiState = favoriteRecipeViewModel.favoriteUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        favoriteRecipeViewModel.getFavoriteList()
    }

    FavoritesScreen(
        uiState = uiState.value,
        navigateToDetail = navigateToDetail,
        isUserLoggedIn = isUserLoggedIn,
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    uiState: FavoriteScreenUiState,
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
) {
    val recipeList = uiState.favoriteList
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = "Favorites"
                    )
                }
            )
        },
        modifier = Modifier.systemBarsPadding()
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            HorizontalDivider(
                thickness = 0.3.dp,
                color = MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                )
            )
            when {
                !isUserLoggedIn.invoke() -> {
                    ErrorContent(
                        text = "Login to view favorites!"
                    )
                }

                uiState.favoriteListIsLoading -> {
                    Loader()
                }

                uiState.favoriteError != null -> {
                    ErrorContent()
                }

                recipeList != null -> {
                    FavoritesContent(
                        navigateToDetail = navigateToDetail,
                        padding = innerPadding,
                        recipes = recipeList,
                    )
                }
            }
        }


    }
}

@Composable
private fun FavoritesContent(
    navigateToDetail: (Long) -> Unit,
    padding: PaddingValues,
    recipes: List<RecipeItem>
) {
    val imageModifier =
        Modifier.width(140.dp).height(80.dp).clip(RoundedCornerShape(16.dp))
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = recipes, key = {
            it.id
        }) { recipe ->
            FavoriteRecipeCard(
                imageModifier = imageModifier,
                imageUrl = recipe.imageUrl,
                title = recipe.title,
                duration = recipe.duration,
                rating = recipe.rating,
                navigateToDetail = navigateToDetail,
                recipeId = recipe.id
            )
        }
    }
}


@Composable
private fun FavoriteRecipeCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier,
    imageUrl: String,
    title: String,
    duration: String,
    rating: Long,
    navigateToDetail: (Long) -> Unit,
    recipeId: Long,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = modifier.clickable {
            navigateToDetail(recipeId)
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                onError = {
                    println("onError=${it.result.throwable}")
                },
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )
            Column {

                Text(
                    textAlign = TextAlign.Start,
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = duration,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primaryContainer.copy(
                                alpha = 0.5f
                            )
                        )
                        Text(
                            text = " $rating",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }

    }
}