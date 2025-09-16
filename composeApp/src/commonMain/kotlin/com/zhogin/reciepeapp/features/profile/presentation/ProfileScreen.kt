package com.zhogin.reciepeapp.features.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhogin.reciepeapp.features.common.presentation.ErrorContent
import com.zhogin.reciepeapp.features.common.presentation.Loader
import com.zhogin.reciepeapp.features.profile.domain.User
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import reciepeapp.composeapp.generated.resources.Res
import reciepeapp.composeapp.generated.resources.avatar
import reciepeapp.composeapp.generated.resources.profile_dummy

@Composable
fun ProfileRoute(
    profileViewModel: ProfileViewModel = koinViewModel(),
    isUserLoggedIn: () -> Boolean,
    openLoginBottomSheet: (() -> Unit) -> Unit,
    onLogout: () -> Unit,
) {
    val profileUiState = profileViewModel.profileUiState.collectAsStateWithLifecycle()
    ProfileScreen(
        profileScreenUiState = profileUiState.value,
        onEditProfile = {},
        onLogin = {
            openLoginBottomSheet {
                profileViewModel.toRefresh()
            }
        },
        onLogout = onLogout,
        isUserLoggedIn = isUserLoggedIn
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isUserLoggedIn: () -> Boolean,
    profileScreenUiState: ProfileScreenUiState,
    onEditProfile: () -> Unit,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = "Profile"
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
                !isUserLoggedIn() -> {
                    LoggingProfileScreen(
                        onLogin = onLogin,
                        onSignUp = {}
                    )
                }
                profileScreenUiState.isLoading -> {
                    Loader()
                }

                profileScreenUiState.error != null -> {
                    ErrorContent()
                }



                profileScreenUiState.userInfo != null && isUserLoggedIn() -> {
                    ProfileContent(
                        user = profileScreenUiState.userInfo,
                        onEditProfile = onEditProfile,
                        onLogout = onLogout,
                    )
                }
            }
        }


    }
}

@Composable
private fun LoggingProfileScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    Column(
       modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Image(
            painter = painterResource(Res.drawable.profile_dummy),
            contentDescription = "default avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(0.3.dp, MaterialTheme.colorScheme.outline.copy(
                alpha = 0.5f
            ),CircleShape)
                .background(MaterialTheme.colorScheme.outline),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "You are not Logged In",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Login to view your profile and save recipes",
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ){
            Text(
                text = "Log In",
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
            onClick = onSignUp,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.primaryContainer,
                containerColor = MaterialTheme.colorScheme.background
            )
        ){
            Text(
                text = "Sign Up",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun ProfileContent(
    user: User,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Image(
            painter = painterResource(Res.drawable.avatar),
            contentDescription = "default avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(0.3.dp, MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                ),CircleShape)
                .background(MaterialTheme.colorScheme.outline),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = user.email,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(Modifier.height(24.dp))
        OutlinedButton(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
            onClick = onEditProfile,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.primaryContainer,
                containerColor = MaterialTheme.colorScheme.background
            )
        ){
            Text(
                text = "Edit Profile",
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ){
            Text(
                text = "Logout",
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.height(12.dp))

    }
}

