package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(authViewModel: AuthViewModel = viewModel()) {
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.padding(20.dp)
    ) {
        Column {

            Button(
                onClick = {
                    scope.launch {
                        authViewModel.signOut()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Out")
            }
        }
    }
}