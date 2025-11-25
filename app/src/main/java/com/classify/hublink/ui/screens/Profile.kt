package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cases
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator // Importar para posible indicador de carga
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(authViewModel: AuthViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val userProfile by authViewModel.currentUserProfile.collectAsState()

    LaunchedEffect(key1 = Unit) {
        authViewModel.loadUserProfile()
    }

    if (userProfile == null) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Surface(
        modifier = Modifier.padding(20.dp)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth().align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp)
            ) {
                // Usamos el nombre del perfil para el título
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = userProfile?.name ?: "Usuario Desconocido",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Información Personal",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                ProfileRow(
                    icon = Icons.Default.Person,
                    label = "Nombre:",
                    value = userProfile?.name
                )

                ProfileRow(
                    icon = Icons.Default.Mail,
                    label = "Correo electrónico:",
                    value = authViewModel.userEmail
                )

                // Número de teléfono
                ProfileRow(
                    icon = Icons.Default.Phone,
                    label = "Numero de telefono:",
                    value = userProfile?.phone
                )
            }

            // --- Formación e Intereses ---
            Column(
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Formación e Intereses",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Universidad
                ProfileRow(
                    icon = Icons.Default.School,
                    label = "Universidad:",
                    value = userProfile?.university
                )

                // Carrera
                ProfileRow(
                    icon = Icons.Default.Work,
                    label = "Carrera:",
                    value = userProfile?.career
                )

                // Intereses
                ProfileRow(
                    icon = Icons.Default.RocketLaunch,
                    label = "Intereses:",
                    value = userProfile?.interests
                )
            }

            // --- Redes Sociales ---
            Column(
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Redes Sociales",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // LinkedIn
                ProfileRow(
                    icon = Icons.Default.Cases, // Cambiado a un icono más representativo
                    label = "Linkedin:",
                    value = userProfile?.linkedin
                )

                // Github
                ProfileRow(
                    icon = Icons.Default.Mail, // Cambiado a un icono más representativo (aunque quizás un ícono de Github sería mejor)
                    label = "Github:",
                    value = userProfile?.github
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp)) // Espacio antes del botón

                Button(
                    onClick = {
                        scope.launch {
                            authViewModel.signOut()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Cerrar Sesión")
                }
            }
        }
    }
}

// 📐 Componente auxiliar para reducir la redundancia y mejorar la lectura
@Composable
fun ProfileRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label.replace(":", "") // Usar la etiqueta como descripción
        )
        Spacer(modifier = Modifier.width(15.dp))

        // Mostrar 'No especificado' o un string vacío si el valor es nulo
        Text(
            text = "$label\n ${value ?: "No especificado"}"
        )
    }
}