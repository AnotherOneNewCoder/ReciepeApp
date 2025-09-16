package com.zhogin.reciepeapp.features.feed.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import com.zhogin.reciepeapp.features.common.presentation.ErrorContent
import com.zhogin.reciepeapp.features.common.presentation.Loader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedRoute(
    navigateToDetail: (Long) -> Unit,
    navigateToSearch: () -> Unit,
    feedViewModel: FeedViewModel = koinViewModel()
) {
    val feedUiState = feedViewModel.feedUiState.collectAsStateWithLifecycle()
    FeedScreen(
        feedUiState = feedUiState.value,
        navigateToSearch = navigateToSearch,
        navigateToDetail = navigateToDetail
    )
}

@Composable
fun FeedScreen(
    navigateToDetail: (Long) -> Unit,
    feedUiState: FeedUiState,
    navigateToSearch: () -> Unit,
) {
    val recipeList = feedUiState.recipes
    Scaffold(
        topBar = {
            TopBar(
                navigateToSearch = navigateToSearch
            )
        }
    ) { innerPadding ->
        when {
            feedUiState.recipeLoading -> {
                Loader()
            }

            feedUiState.recipeError != null -> {
                ErrorContent()
            }

            recipeList != null -> {
                FeedContent(
                    navigateToDetail = navigateToDetail,
                    padding = innerPadding,
                    recipes = recipeList,
                )
            }
        }

    }
}

@Composable
private fun TopBar(
    navigateToSearch: () -> Unit,
) {
    Column(
        modifier = Modifier
            //.windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Hi Ivan!",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Got a tasty dish in mind?",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(45.dp)
                .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(12.dp))
                .border(width = 1.dp, MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.3f
                ),RoundedCornerShape(12.dp),)
                .padding(horizontal = 16.dp)
                .clickable {
                    navigateToSearch()
                },
        )
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Search any recipes",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )
        }
    }
}

@Composable
private fun FeedContent(
    navigateToDetail: (Long) -> Unit,
    padding: PaddingValues,
    recipes: List<RecipeItem>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(
            top = padding.calculateTopPadding()
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            TopRecipeList(
                title = "Top Recommendations",
                recipes = recipes.reversed(),
                navigateToDetail = navigateToDetail
            )
        }

        recipeListOfTheWeek(
            title = "Recipes Of The Week",
            recipes = recipes,
            navigateToDetail = navigateToDetail,
        )
    }
}


private fun LazyGridScope.recipeListOfTheWeek(
    navigateToDetail: (Long) -> Unit,
    title: String,
    recipes: List<RecipeItem>) {

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)

        )

    }
    itemsIndexed(
        items = recipes,
        key = { _, it ->
            it.id
        }
    ) { index, recipe ->
        val cardPaddingStart = if (index % 2 == 0) 16.dp else 0.dp
        val cardPaddingEnd = if (index % 2 == 0) 0.dp else 16.dp
        val imageModifier =
            Modifier.fillMaxWidth().height(130.dp).clip(RoundedCornerShape(16.dp))
        RecipeCard(
            modifier = Modifier.padding(start = cardPaddingStart, end = cardPaddingEnd),
            imageModifier = imageModifier.clickable{
                navigateToDetail(recipe.id)
            },
            imageUrl = recipe.imageUrl,
            title = recipe.title,
            duration = recipe.duration,
            rating = recipe.rating,
        )
    }


}

@Composable
private fun TopRecipeList(
    navigateToDetail: (Long) -> Unit,
    title: String,
    recipes: List<RecipeItem>) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)

        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = recipes,
                key = { it.id }
            ) { recipe ->
                val imageModifier =
                    Modifier.width(120.dp).height(140.dp).clip(RoundedCornerShape(16.dp))
                RecipeCard(
                    modifier = Modifier.width(110.dp),
                    imageModifier = imageModifier.clickable{
                        navigateToDetail(recipe.id)
                    },
                    imageUrl = recipe.imageUrl,
                    title = recipe.title,
                    duration = recipe.duration,
                    rating = recipe.rating,
                )
            }
        }
    }
}

@Composable
private fun RecipeCard(
    modifier: Modifier,
    imageModifier: Modifier,
    imageUrl: String,
    title: String,
    duration: String,
    rating: Long,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
                    imageVector = Icons.Default.Start,
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

