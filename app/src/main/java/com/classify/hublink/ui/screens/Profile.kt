package com.classify.hublink.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.ui.components.Logo
import com.classify.hublink.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(authViewModel: AuthViewModel = viewModel()) {
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.padding(20.dp)
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth().align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Nombre de Usuario",
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .padding(vertical = 20.dp)
            ) {
                Column {
                    Text(
                        text = "Información Personal",
                        fontSize = 25.sp
                    )
                    Text(
                        text = "Nombre: Alessandro"
                    )
                }

            }

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