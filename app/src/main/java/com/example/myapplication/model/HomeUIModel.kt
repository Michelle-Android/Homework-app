package com.example.myapplication.model

internal sealed interface  LoginPageState {
    data object Init: LoginPageState
    data object Loading: LoginPageState
    data object Error: LoginPageState
    data object Success : LoginPageState
}

data class ItemUIModel(
    val header: String,
    val link: String
)

