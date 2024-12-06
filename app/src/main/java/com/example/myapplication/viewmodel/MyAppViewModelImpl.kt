package com.example.myapplication.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ItemUIModel
import com.example.myapplication.model.LoginPageState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal abstract class MyAppViewModel : ViewModel() {
    abstract val repoList: List<ItemUIModel>

    abstract val uiState: State<LoginPageState>

    abstract fun getContent()

    abstract fun login(accountName: String, pwd: String)
}


internal class MyAppViewModelImpl(
//    private val useCases: GithubUseCasesImpl,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): MyAppViewModel() {

    private val _uiState: MutableState<LoginPageState> = mutableStateOf(LoginPageState.Init)
    override val uiState: State<LoginPageState>
        get() = _uiState

    override val repoList: List<ItemUIModel>
        get() = getRepos()

    override fun getContent() {
        viewModelScope.launch {
        }
    }

    override fun login(accountName: String, pwd: String) {
        _uiState.value = LoginPageState.Loading
        viewModelScope.launch(ioDispatcher) {
            kotlin.runCatching {
                //need to figure out how to inject usecase
//                useCases.login(accountName, pwd)
            }.onSuccess {
                _uiState.value = LoginPageState.Success
            }.onFailure {
                _uiState.value = LoginPageState.Error
            }
        }
    }

    private fun getRepos() : List<ItemUIModel> {
        return listOf(
            ItemUIModel(
                "1esp-idf",
                "https://www.baidu.com"
            ),
            ItemUIModel(
                "2esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "3esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "4esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "5esp-idf",
                "https://www.baidu.com"
            ),
            ItemUIModel(
                "6esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "7esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "8esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "9esp-idf",
                "https://www.baidu.com"
            ),
            ItemUIModel(
                "10esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "11esp-idf",
                "https://github.com/espressif/esp-idf"
            ),
            ItemUIModel(
                "12esp-idf",
                "https://github.com/espressif/esp-idf"
            )
        )
    }
}