package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.ItemUIModel
import com.example.myapplication.model.LoginPageState
import com.example.myapplication.utils.SearchField
import com.example.myapplication.utils.WebViewPage
import com.example.myapplication.viewmodel.MyAppViewModelImpl

@Composable
internal fun Home() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("login") { LoginPage() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Home Screen") }, actions = { LoginComponent(navController) }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            val viewModel: MyAppViewModelImpl = viewModel()

            Column (modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 20.dp)) {
                var showWebView by remember { mutableStateOf(false) }
                var currentUrl by remember { mutableStateOf("") }
                if(!showWebView) {
                    SearchField(viewModel.repoList.map{ it.header }) { selectedItem ->
                        // Handle selected item here
                    }
                    Text(
                        text = "Popular GitHub Repos",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                    LazyColumn {
                        viewModel.repoList.forEach {
                            item {
                                RepoItemCard(it) { url ->
                                    showWebView = true
                                    currentUrl = url
                                }
                            }
                        }
                    }
                }

                if (showWebView) {
                    WebViewPage(url = currentUrl) { showWebView = false }
                }
            }
        }
    }
}

@Composable
fun LoginPage() {
    val viewModel: MyAppViewModelImpl = viewModel()

    val uiState = viewModel.uiState.value

    when(uiState) {
        is LoginPageState.Init -> {
            LoginPageInit { username, password ->
                viewModel.login(username, password)
            }
        }
        is LoginPageState.Loading -> {
            Box(
                modifier = Modifier
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator(trackColor = Color.Green)
            }

        }
        is LoginPageState.Success -> {
//            need to work
            Box(
                modifier = Modifier
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center) {
                Text("success")
            }

        }
        is LoginPageState.Error -> {
//            need to work
            Text("error")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginPageInit(login: (username: String, password: String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Login") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it; usernameError = false },
                label = { Text("Username") },
                isError = usernameError,
                modifier = Modifier.fillMaxWidth()
            )
            if (usernameError) {
                Text("Username is required", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = false },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError,
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError) {
                Text("Password is required", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                usernameError = username.isBlank()
                passwordError = password.isBlank()
                if (!usernameError && !passwordError) {
                    login(username, password)
                }
            }) {
                Text("Login")
            }
        }
    }
}

@Composable
fun RepoItemCard(item: ItemUIModel, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(item.link) },
    ) {
        Text(
            text = item.header,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
internal fun LoginComponent(navController: NavController) {
    IconButton(
        onClick = { navController.navigate("login") },
        modifier = Modifier.size(24.dp)
    ) {
        Icon(Icons.Default.AccountCircle, "Login")
    }
}
