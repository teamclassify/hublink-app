package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.data.entities.UserProfile
import com.classify.hublink.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileScreen(
    authViewModel: AuthViewModel = viewModel(),
    onProfileSaved: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var career by remember { mutableStateOf("") }
    var interests by remember { mutableStateOf("") }
    var linkedin by remember { mutableStateOf("") }
    var github by remember { mutableStateOf("") }
    var gitlab by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val isButtonEnabled = name.isNotBlank() && phone.isNotBlank() && university.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Completa tu Perfil") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "¡Casi listo! Necesitamos tus datos para el HubLink.",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text("Información Personal", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Número de Teléfono") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Text("Formación e Intereses", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            OutlinedTextField(value = university, onValueChange = { university = it }, label = { Text("Universidad") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = career, onValueChange = { career = it }, label = { Text("Carrera/Programa") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = interests, onValueChange = { interests = it }, label = { Text("Intereses/Hobbies") }, modifier = Modifier.fillMaxWidth(), minLines = 2)

            Spacer(modifier = Modifier.height(16.dp))


            Text("Redes Sociales (Usuario)", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            OutlinedTextField(value = linkedin, onValueChange = { linkedin = it }, label = { Text("LinkedIn Username") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = github, onValueChange = { github = it }, label = { Text("GitHub Username") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = gitlab, onValueChange = { gitlab = it }, label = { Text("GitLab Username") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isLoading = true
                    val userProfile = UserProfile(
                        name = name,
                        phone = phone,
                        university = university,
                        career = career,
                        interests = interests,
                        linkedin = linkedin,
                        github = github,
                    )
                    authViewModel.saveUserProfile(userProfile) {
                        isLoading = false
                        onProfileSaved()
                    }
                },
                enabled = isButtonEnabled && !isLoading,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Perfil y Continuar")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
